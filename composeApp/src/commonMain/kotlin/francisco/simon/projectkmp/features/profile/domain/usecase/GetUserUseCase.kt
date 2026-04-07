package francisco.simon.projectkmp.features.profile.domain.usecase

import francisco.simon.projectkmp.core.utils.runCatchingCancellable
import francisco.simon.projectkmp.features.profile.domain.entity.User
import francisco.simon.projectkmp.features.profile.domain.repository.ProfileRepository

class GetUserUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): Result<User> {
        return runCatchingCancellable {
            repository.getUser()
        }
    }
}
