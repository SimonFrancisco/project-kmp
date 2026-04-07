package francisco.simon.projectkmp.features.search.domain.repository

import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun searchCourses(query: String): Flow<List<Int>>

    suspend fun loadNextPage()
}
