package francisco.simon.projectkmp.auth

sealed interface AuthState {
    data object WebViewLoading : AuthState
    data object ExchangingToken : AuthState
    data class Success(val accessToken: String) : AuthState
    data class Error(val message: String) : AuthState
}
