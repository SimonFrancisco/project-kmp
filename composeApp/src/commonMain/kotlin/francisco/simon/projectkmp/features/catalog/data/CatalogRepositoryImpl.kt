package francisco.simon.projectkmp.features.catalog.data

import francisco.simon.projectkmp.features.catalog.data.dto.CourseListResponseDto
import francisco.simon.projectkmp.features.catalog.data.dto.toDomain
import francisco.simon.projectkmp.features.catalog.domain.entity.Courses
import francisco.simon.projectkmp.features.catalog.domain.repository.CatalogRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart

class CatalogRepositoryImpl(
    private val httpClient: HttpClient
) : CatalogRepository {

    private companion object{
        const val FIRST_PAGE = 1
    }

    private val loadNextEvents = MutableSharedFlow<Unit>(replay = 1)
    private var nextPage: Int = FIRST_PAGE
    private var hasNext: Boolean = true
    private val _coursesTemp = mutableListOf<Courses>()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val loadedListFlow =
        loadNextEvents
            .onStart { emit(Unit) }
            .mapLatest {
                val startFrom = nextPage
                if (!hasNext) return@mapLatest _coursesTemp.toList()
                val response = httpClient.get(
                    urlString = "https://stepik.org/api/course-lists"
                ) {
                    parameter(key = "page", value = startFrom)
                }.body<CourseListResponseDto>()
                nextPage += 1
                hasNext = response.meta.next

                _coursesTemp += response.toDomain()

                _coursesTemp.toList()
            }


    override fun getCourses(): Flow<List<Courses>> {
        return loadedListFlow
    }

    override suspend fun loadNextPage() {
        loadNextEvents.emit(Unit)
    }

}
