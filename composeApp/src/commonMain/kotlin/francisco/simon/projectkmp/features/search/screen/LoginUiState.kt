package francisco.simon.projectkmp.features.search.screen

import org.jetbrains.compose.resources.StringResource

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val error: StringResource? = null
){
    val isLoginButtonActive: Boolean
        get() = username.isNotBlank() && password.isNotBlank()
}
