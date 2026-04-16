package francisco.simon.projectkmp.core.domain.usecase

import francisco.simon.projectkmp.core.domain.entity.UserCore
import francisco.simon.projectkmp.core.domain.repository.CoreRepository
import francisco.simon.projectkmp.core.utils.runCatchingCancellable

class GetUserCoreUseCase(
    private val repository: CoreRepository
) {
    suspend operator fun invoke(userId: Long): Result<UserCore> {
        return runCatchingCancellable {
            repository.getUser(userId)
        }
    }
}
