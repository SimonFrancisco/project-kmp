package francisco.simon.projectkmp.features.catalog.data

import francisco.simon.projectkmp.features.catalog.data.dto.CourseListResponseDto
import francisco.simon.projectkmp.features.catalog.data.dto.toDomain
import francisco.simon.projectkmp.features.catalog.domain.entity.Courses
import francisco.simon.projectkmp.features.catalog.domain.repository.CatalogRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

class CatalogRepositoryImpl(
    private val httpClient: HttpClient
) : CatalogRepository {

    private val scope = CoroutineScope(Dispatchers.IO)
    private val nextDataNeedEvents = MutableSharedFlow<Unit>(replay = 1)
    private var nextFrom: Int = 1
    private var hasNext: Boolean? = null
    private val _coursesTemp = mutableListOf<Courses>()
    private val coursesTemp: List<Courses>
        get() = _coursesTemp.toList()

    private val loadedListFlow = flow {
        nextDataNeedEvents.emit(Unit)
        nextDataNeedEvents.collect {
            val startFrom = nextFrom
            if (hasNext != null && hasNext == false) {
                emit(_coursesTemp)
                return@collect
            }
            val httpResponse = httpClient.get(
                urlString = "https://stepik.org/api/course-lists"
            ) {
                parameter(key = "page", value = startFrom)
            }
            val response = if (hasNext == null) {
                httpResponse.body<CourseListResponseDto>().also {
                    nextFrom += it.meta.page
                    hasNext = it.meta.next
                }
            } else {
                httpResponse.body<CourseListResponseDto>().also {
                    nextFrom += it.meta.page
                    hasNext = it.meta.next
                }
            }

            val courses = response.toDomain()
            _coursesTemp.addAll(courses)
            emit(_coursesTemp)
        }
    }.flowOn(Dispatchers.IO)

    private val coursesList: StateFlow<List<Courses>> = loadedListFlow
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = coursesTemp
        )


    override fun getCourses(): StateFlow<List<Courses>> {
        return coursesList
    }

    override suspend fun loadNextPage() {
        nextDataNeedEvents.emit(Unit)
    }

}
