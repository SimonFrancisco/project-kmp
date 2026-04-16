package francisco.simon.projectkmp.features.auth.ui.webView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.projectkmp.features.auth.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.error_unknown

class WebViewAuthScreenViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<WebViewAuthScreenState> =
        MutableStateFlow(WebViewAuthScreenState.WebViewAuth)
    val state: StateFlow<WebViewAuthScreenState> = _state.asStateFlow()

    fun onCodeReceived(code: String) {
        _state.value = WebViewAuthScreenState.Loading
        viewModelScope.launch {
            loginUseCase(code)
                .onSuccess {
                    _state.value = WebViewAuthScreenState.Success
                }
                .onFailure {
                    _state.value = WebViewAuthScreenState.Error(Res.string.error_unknown)
                }
        }
    }
}
