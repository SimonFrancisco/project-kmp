package francisco.simon.projectkmp.features.auth.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AuthScreenViewModel : ViewModel() {
    private val _effects: MutableSharedFlow<AuthScreenEffect> = MutableSharedFlow()
    val effects = _effects.asSharedFlow()

    fun onHandleIntent(intent: AuthScreenIntent) {
        viewModelScope.launch {
            when (intent) {
                AuthScreenIntent.LoginButtonClicked -> {
                    _effects.emit(AuthScreenEffect.NavigateToWebViewAuthScreen)
                }
            }
        }
    }
}
