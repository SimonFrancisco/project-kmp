package francisco.simon.projectkmp.features.onboarding.prefs

import kotlinx.coroutines.flow.Flow

interface OnboardingManager {
    val onBoardingCompleted: Flow<Boolean>
    suspend fun completeOnBoarding()
    suspend fun clear()
}
