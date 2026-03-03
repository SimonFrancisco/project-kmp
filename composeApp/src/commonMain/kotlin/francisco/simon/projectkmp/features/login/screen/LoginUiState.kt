package francisco.simon.projectkmp.features.login.screen

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoginButtonActive: Boolean = false,
    val error: String = ""
)
