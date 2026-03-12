package francisco.simon.projectkmp.features.search.domain.usecase

import francisco.simon.projectkmp.features.search.domain.repository.SearchRepository

class LoadNextSearchPageUseCase(
    private val repository: SearchRepository
) {
    suspend operator fun invoke() {
        repository.loadNextPage()
    }
}
