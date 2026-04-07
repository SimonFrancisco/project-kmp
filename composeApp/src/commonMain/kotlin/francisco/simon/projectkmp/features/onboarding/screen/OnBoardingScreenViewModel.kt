package francisco.simon.projectkmp.features.onboarding.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.projectkmp.features.onboarding.domain.usecase.CompleteOnboardingUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class OnBoardingScreenViewModel(
    private val onCompleteOnboardingUseCase: CompleteOnboardingUseCase
) : ViewModel() {

    private val _effects: MutableSharedFlow<OnBoardingScreenEffect> = MutableSharedFlow()
    val effects = _effects.asSharedFlow()

    private fun completeOnboarding() {
        viewModelScope.launch {
            onCompleteOnboardingUseCase()
            _effects.emit(OnBoardingScreenEffect.NavigateToAuthScreen)
        }
    }

    fun onHandleIntent(intent: OnBoardingScreenIntent) {
        when (intent) {
            is OnBoardingScreenIntent.CompleteOnBoarding -> {
                completeOnboarding()
            }
        }
    }
}
