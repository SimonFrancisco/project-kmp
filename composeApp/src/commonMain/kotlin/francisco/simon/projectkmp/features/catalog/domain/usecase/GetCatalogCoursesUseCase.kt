package francisco.simon.projectkmp.features.catalog.domain.usecase

import francisco.simon.projectkmp.core.utils.asResult
import francisco.simon.projectkmp.features.catalog.domain.entity.Courses
import francisco.simon.projectkmp.features.catalog.domain.repository.CatalogRepository
import kotlinx.coroutines.flow.Flow

typealias GetCatalogCoursesResult = Flow<Result<List<Courses>>>

class GetCatalogCoursesUseCase(
    private val repository: CatalogRepository
) {
    operator fun invoke(): GetCatalogCoursesResult {
        return repository.getCourses().asResult()
    }
}
