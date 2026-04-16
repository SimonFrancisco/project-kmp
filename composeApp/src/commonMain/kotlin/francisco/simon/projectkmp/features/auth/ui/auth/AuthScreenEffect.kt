package francisco.simon.projectkmp.features.auth.ui.auth

sealed interface AuthScreenEffect {
    data object NavigateToWebViewAuthScreen : AuthScreenEffect
}
