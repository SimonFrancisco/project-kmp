package francisco.simon.projectkmp.features.friends.screen

import francisco.simon.projectkmp.features.friends.domain.entity.Friend
import org.jetbrains.compose.resources.StringResource

sealed interface FriendsScreenState {
    object Loading : FriendsScreenState
    data class Success(val friends: List<Friend>) : FriendsScreenState
    data class Error(val errorMessageRes: StringResource) : FriendsScreenState
}
