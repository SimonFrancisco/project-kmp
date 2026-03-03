package francisco.simon.projectkmp.features.friends.domain.usecase

import francisco.simon.projectkmp.features.friends.domain.entity.Friend
import francisco.simon.projectkmp.features.friends.domain.repository.FriendsRepository
import francisco.simon.projectkmp.utils.runCatchingCancellable

class GetFriendsUseCase(
    private val repository: FriendsRepository
) {
    suspend operator fun invoke(): Result<List<Friend>> {
        return runCatchingCancellable {
            repository.getFriends()
        }
    }
}
