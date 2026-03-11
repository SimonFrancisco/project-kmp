package francisco.simon.projectkmp.features.catalog.domain.usecase

import francisco.simon.projectkmp.features.catalog.domain.entity.Courses
import francisco.simon.projectkmp.features.catalog.domain.repository.CatalogRepository
import kotlinx.coroutines.flow.StateFlow

class GetCatalogCoursesUseCase(
    private val repository: CatalogRepository
) {
    operator fun invoke(): StateFlow<List<Courses>> {
        return repository.getCourses()
    }
}
