package francisco.simon.projectkmp.features.catalog.domain.repository

import francisco.simon.projectkmp.features.catalog.domain.entity.Courses
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {
    fun getCourses(): Flow<List<Courses>>
    suspend fun loadNextPage()
}
