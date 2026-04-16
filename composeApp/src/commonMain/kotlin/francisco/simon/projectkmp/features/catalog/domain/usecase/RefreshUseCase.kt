package francisco.simon.projectkmp.features.catalog.domain.usecase

import francisco.simon.projectkmp.core.utils.runCatchingCancellable
import francisco.simon.projectkmp.features.catalog.domain.repository.CatalogRepository

class RefreshUseCase(
    private val repository: CatalogRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return runCatchingCancellable { repository.refresh() }
    }
}
