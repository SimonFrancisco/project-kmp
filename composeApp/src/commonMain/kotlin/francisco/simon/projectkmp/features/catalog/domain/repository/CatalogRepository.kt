package francisco.simon.projectkmp.features.catalog.domain.repository

import francisco.simon.projectkmp.features.catalog.domain.entity.Courses
import kotlinx.coroutines.flow.StateFlow

interface CatalogRepository {
    fun getCourses(): StateFlow<List<Courses>>
    suspend fun loadNextPage()
}
