package francisco.simon.projectkmp.features.catalog.domain.usecase

import francisco.simon.projectkmp.core.utils.runCatchingCancellable
import francisco.simon.projectkmp.features.catalog.domain.repository.CatalogRepository

class LoadNextPageUseCase(
    private val repository: CatalogRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return runCatchingCancellable { repository.loadNextPage() }
    }
}
