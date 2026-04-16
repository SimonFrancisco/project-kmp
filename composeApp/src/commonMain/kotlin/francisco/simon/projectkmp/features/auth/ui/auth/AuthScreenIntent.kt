package francisco.simon.projectkmp.features.auth.ui.auth

sealed interface AuthScreenIntent {
    data object LoginButtonClicked : AuthScreenIntent
}
