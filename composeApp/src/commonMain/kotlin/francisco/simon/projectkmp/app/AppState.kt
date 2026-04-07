package francisco.simon.projectkmp.app

sealed interface AppState {
    object Loading : AppState
    object Onboarding : AppState
    object Authorized : AppState
    object Unauthorized : AppState
}
