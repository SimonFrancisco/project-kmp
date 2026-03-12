package francisco.simon.projectkmp.features.catalog.domain.usecase

import francisco.simon.projectkmp.features.catalog.domain.repository.CatalogRepository

class LoadNextPageUseCase(
    private val repository: CatalogRepository
) {
    suspend operator fun invoke() {
        repository.loadNextPage()
    }
}
