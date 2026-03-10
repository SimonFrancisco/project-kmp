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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class CatalogRepositoryImpl(
    private val httpClient: HttpClient
) : CatalogRepository {

    private val scope = CoroutineScope(Dispatchers.IO)
    private val nextDataNeedEvents = MutableSharedFlow<Unit>(replay = 1)
    private var nextFrom: Int = 1
    private var hasNext: Boolean? = null
    private val coursesTemp = mutableListOf<Courses>()

    private val loadedListFlow = flow {
        nextDataNeedEvents.emit(Unit)
        nextDataNeedEvents.collect {
            val startFrom = nextFrom
            if (hasNext != null && hasNext == false) {
                emit(coursesTemp)
                return@collect
            }
            val httpResponse = httpClient.get(
                urlString = "https://stepik.org/api/course-lists"
            ) {
                parameter(key = "page", value = startFrom)
            }
            val response = if (hasNext == null) {
                httpResponse.body<CourseListResponseDto>()
            } else {
                httpResponse.body<CourseListResponseDto>().also {
                    nextFrom += it.meta.page
                    hasNext = it.meta.next
                }
            }

            val courses = response.toDomain()
            coursesTemp.addAll(courses)
            emit(coursesTemp)
        }
    }

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
