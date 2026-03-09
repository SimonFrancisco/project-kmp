package francisco.simon.projectkmp.features.login.domain

import francisco.simon.projectkmp.utils.runCatchingCancellable

class LoginUseCase(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(username: String, password: String): Result<Unit> {
        return runCatchingCancellable {
            repository.login(username, password)
        }
    }
}
