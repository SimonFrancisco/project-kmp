package francisco.simon.projectkmp.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.projectkmp.auth.data.AuthRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.WebViewLoading)
    val state: StateFlow<AuthState> = _state.asStateFlow()

    fun onCodeReceived(code: String) {
        _state.value = AuthState.ExchangingToken
        viewModelScope.launch {
            authRepository.exchangeCodeForToken(code)
                .onSuccess { token ->
                    Napier.d(tag = "AuthViewModel", message = "Token received: ${token.accessToken}")
                    _state.value = AuthState.Success(token.accessToken)
                }
                .onFailure { error ->
                    Napier.e(tag = "AuthViewModel", message = "Token exchange failed", throwable = error)
                    _state.value = AuthState.Error(error.message ?: "Unknown error")
                }
        }
    }
}
