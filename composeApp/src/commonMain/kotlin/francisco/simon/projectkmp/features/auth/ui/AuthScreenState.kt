package francisco.simon.projectkmp.features.auth.ui

import org.jetbrains.compose.resources.StringResource

sealed interface AuthScreenState {
    data object WebViewAuth : AuthScreenState
    data object Loading : AuthScreenState
    data object Success : AuthScreenState
    data class Error(val errorMessageRes: StringResource) : AuthScreenState
}
