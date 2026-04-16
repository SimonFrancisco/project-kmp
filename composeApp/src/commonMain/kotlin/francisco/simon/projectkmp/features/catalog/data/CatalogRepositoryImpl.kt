package francisco.simon.projectkmp.features.catalog.data

import francisco.simon.projectkmp.features.catalog.data.dto.CourseListResponseDto
import francisco.simon.projectkmp.features.catalog.data.dto.toDomain
import francisco.simon.projectkmp.features.catalog.domain.entity.Courses
import francisco.simon.projectkmp.features.catalog.domain.repository.CatalogRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry

@OptIn(ExperimentalCoroutinesApi::class)
class CatalogRepositoryImpl(
    private val httpClient: HttpClient
) : CatalogRepository {

    private companion object {
        const val FIRST_PAGE = 1
        const val RETRY_TIMES = 3L
        const val PAGE_SIZE = 10
    }

    private val loadTrigger = MutableSharedFlow<Unit>(replay = 1)
    private var nextPage: Int = FIRST_PAGE
    private var hasNext: Boolean = true
    private val _coursesTemp = mutableListOf<Courses>()

    private val loadedListFlow = loadTrigger
        .onStart { emit(Unit) }
        .mapLatest {
            val startFrom = nextPage
            if (!hasNext) return@mapLatest _coursesTemp.toList()
            val response = httpClient.get(
                urlString = "https://stepik.org/api/course-lists"
            ) {
                parameter(key = "page", value = startFrom)
                parameter(key = "page_size", value = PAGE_SIZE)
            }.body<CourseListResponseDto>()

            nextPage += 1
            hasNext = response.meta.next

            _coursesTemp += response.toDomain()

            _coursesTemp.toList()
        }.retry(RETRY_TIMES)
        .flowOn(Dispatchers.IO)

    override fun getCourses(): Flow<List<Courses>> {
        return loadedListFlow
    }

    override suspend fun loadNextPage() {
        loadTrigger.emit(Unit)
    }

    override suspend fun refresh() {
        nextPage = FIRST_PAGE
        hasNext = true
        _coursesTemp.clear()
        loadTrigger.emit(Unit)
    }
}
