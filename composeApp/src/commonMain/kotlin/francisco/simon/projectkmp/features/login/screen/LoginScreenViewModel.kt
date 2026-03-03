package francisco.simon.projectkmp.features.login.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {

    private val _state: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val state = _state.asStateFlow()

    private val _events: MutableSharedFlow<LoginUiEvent> = MutableSharedFlow()
    val events = _events.asSharedFlow()

    fun onUsernameChanged(username: String) {
        _state.update { old->
            val new = old.copy(username = username)
            changeLoginButtonState(new)
        }
    }

    fun onPasswordChanged(password: String) {
        _state.update {old->
            val new = old.copy(password = password)
            changeLoginButtonState(new)
        }
    }

    private fun changeLoginButtonState(loginUiState: LoginUiState): LoginUiState {
        return if (validate(loginUiState.username, loginUiState.password)) {
            loginUiState.copy(isLoginButtonActive = true)
        } else {
            loginUiState.copy(isLoginButtonActive = false)
        }
    }

    fun onLogin() {
        viewModelScope.launch {
            _events.emit(LoginUiEvent.LoginSuccessEvent)
        }
    }

    private fun validate(username: String, password: String): Boolean {
        return username.isNotBlank() && password.isNotBlank()
    }
}
