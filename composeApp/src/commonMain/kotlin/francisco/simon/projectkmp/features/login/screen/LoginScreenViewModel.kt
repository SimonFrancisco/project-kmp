package francisco.simon.projectkmp.features.login.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import francisco.simon.projectkmp.features.login.domain.LoginUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.error_unknown
import kotlin.reflect.KClass

class LoginScreenViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val state = _state.asStateFlow()

    private val _events: MutableSharedFlow<LoginUiEvent> = MutableSharedFlow()
    val events = _events.asSharedFlow()
    var loginJob: Job? = null

    fun onUsernameChanged(username: String) {
        _state.update {
            it.copy(username = username)
        }
    }

    fun onPasswordChanged(password: String) {
        _state.update {
            it.copy(password = password)
        }
    }

    fun onLogin() {
        loginJob?.cancel()
        loginJob = viewModelScope.launch {
            loginUseCase(
                username = _state.value.username,
                password = _state.value.password
            ).onSuccess {
                _events.emit(LoginUiEvent.LoginSuccessEvent)
            }.onFailure {
                _state.update {
                    it.copy(error = Res.string.error_unknown)
                }
            }
        }
    }
}


internal class LoginScreenViewModelFactory(
    private val loginUseCase: LoginUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: KClass<T>,
        extras: CreationExtras
    ): T {
        if (modelClass == LoginScreenViewModel::class) {
            return LoginScreenViewModel(loginUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
