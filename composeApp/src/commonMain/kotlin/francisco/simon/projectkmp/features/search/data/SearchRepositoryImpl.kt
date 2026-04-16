package francisco.simon.projectkmp.features.search.data

import francisco.simon.projectkmp.features.search.data.dto.SearchResponseDto
import francisco.simon.projectkmp.features.search.data.dto.toListId
import francisco.simon.projectkmp.features.search.domain.repository.SearchRepository
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
import kotlinx.coroutines.flow.retry

@OptIn(ExperimentalCoroutinesApi::class)
class SearchRepositoryImpl(
    private val httpClient: HttpClient
) : SearchRepository {

    private companion object {
        const val FIRST_PAGE = 1
        const val RETRY_TIMES = 3L
    }

    private var currentQuery: String = " "
    private val loadTrigger = MutableSharedFlow<Unit>(replay = 1)
    private var nextPage: Int = FIRST_PAGE
    private var hasNext: Boolean = true
    private val _coursesTemp = mutableListOf<Int>()

    private val loadedListFlow = loadTrigger
        .mapLatest {
            val startFrom = nextPage
            if (!hasNext) return@mapLatest _coursesTemp.toList()
            val response = httpClient.get(
                urlString = "https://stepik.org/api/search-results"
            ) {
                parameter(key = "query", value = currentQuery)
                parameter(key = "page", value = startFrom)
            }.body<SearchResponseDto>()

            hasNext = response.metaDto.next

            _coursesTemp += response.toListId()

            _coursesTemp.toList()
        }.retry(RETRY_TIMES)
        .flowOn(Dispatchers.IO)

    override suspend fun searchCourses(query: String): Flow<List<Int>> {
        currentQuery = query
        nextPage = FIRST_PAGE
        hasNext = true
        _coursesTemp.clear()
        loadTrigger.emit(Unit)
        return loadedListFlow
    }

    override suspend fun loadNextPage() {
        nextPage++
        loadTrigger.emit(Unit)
    }
}
