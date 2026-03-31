package francisco.simon.projectkmp.features.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.projectkmp.features.auth.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.error_unknown

class AuthScreenViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<AuthScreenState> =
        MutableStateFlow(AuthScreenState.WebViewAuth)
    val state: StateFlow<AuthScreenState> = _state.asStateFlow()

    fun onCodeReceived(code: String) {
        _state.value = AuthScreenState.Loading
        viewModelScope.launch {
            loginUseCase(code)
                .onSuccess {
                    _state.value = AuthScreenState.Success
                }
                .onFailure {
                    _state.value = AuthScreenState.Error(Res.string.error_unknown)
                }
        }
    }
}
