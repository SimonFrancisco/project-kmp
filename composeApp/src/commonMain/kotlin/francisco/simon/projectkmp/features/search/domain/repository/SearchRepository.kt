package francisco.simon.projectkmp.features.search.domain.repository

import francisco.simon.projectkmp.features.search.domain.entity.SearchCourse
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun searchCourses(query: String): Flow<List<SearchCourse>>

    suspend fun loadNextPage()
}
