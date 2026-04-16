package francisco.simon.projectkmp.features.auth.ui.webView

import org.jetbrains.compose.resources.StringResource

sealed interface WebViewAuthScreenState {
    data object WebViewAuth : WebViewAuthScreenState
    data object Loading : WebViewAuthScreenState
    data object Success : WebViewAuthScreenState
    data class Error(val errorMessageRes: StringResource) : WebViewAuthScreenState
}
