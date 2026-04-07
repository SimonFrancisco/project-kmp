package francisco.simon.projectkmp.features.profile.screen

import francisco.simon.projectkmp.features.profile.domain.entity.User
import org.jetbrains.compose.resources.StringResource

sealed interface ProfileScreenState {
    object Loading : ProfileScreenState
    data class Success(val user: User) : ProfileScreenState
    data class Error(val errorMessageRes: StringResource) : ProfileScreenState
}
