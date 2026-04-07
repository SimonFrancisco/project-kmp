package francisco.simon.projectkmp.features.profile.domain.usecase

import francisco.simon.projectkmp.core.utils.runCatchingCancellable
import francisco.simon.projectkmp.features.profile.domain.repository.ProfileRepository

class LogoutUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return runCatchingCancellable {
            repository.logout()
        }
    }
}
