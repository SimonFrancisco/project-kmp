package francisco.simon.projectkmp.app

sealed interface AppState {
    object Loading : AppState
    object Authorized : AppState
    object Unauthorized : AppState
}
