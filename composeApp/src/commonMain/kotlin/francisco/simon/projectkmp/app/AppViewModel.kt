package francisco.simon.projectkmp.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.projectkmp.features.auth.prefs.TokenStorage
import francisco.simon.projectkmp.features.onboarding.prefs.OnboardingManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class AppViewModel(
    tokenStorage: TokenStorage,
    onboardingManager: OnboardingManager
) : ViewModel() {

    val state = combine(
        tokenStorage.tokensFlow,
        onboardingManager.onBoardingCompleted
    ) { token, onboardingCompleted ->
        when {
            !onboardingCompleted -> AppState.Onboarding
            token != null -> AppState.Authorized
            else -> AppState.Unauthorized
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = AppState.Loading
    )
}
