package francisco.simon.projectkmp.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.projectkmp.features.auth.prefs.TokenStorage
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AppViewModel(
    tokenStorage: TokenStorage
) : ViewModel() {

    val authorized = tokenStorage.tokensFlow
        .map { token ->
            if (token != null) {
                AppState.Authorized
            } else {
                AppState.Unauthorized
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = AppState.Loading
        )

    }
