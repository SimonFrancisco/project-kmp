package francisco.simon.projectkmp.features.auth.domain.usecase

import francisco.simon.projectkmp.features.auth.domain.repository.AuthRepository
import francisco.simon.projectkmp.features.auth.prefs.TokenStorage

class LoginUseCase(
    private val authRepository: AuthRepository,
    private val tokenStorage: TokenStorage
) {
    suspend operator fun invoke(code: String): Result<Unit> {
        return authRepository.exchangeCodeForToken(code).onSuccess {
            tokenStorage.saveTokens(it)
        }.onFailure {
            tokenStorage.clear()
        }.map {}
    }
}
