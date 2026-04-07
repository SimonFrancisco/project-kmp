package francisco.simon.projectkmp.features.onboarding.data

import francisco.simon.projectkmp.features.onboarding.domain.repository.OnboardingRepository
import francisco.simon.projectkmp.features.onboarding.prefs.OnboardingManager

class OnboardingRepositoryImpl(
    private val onboardingManager: OnboardingManager
) : OnboardingRepository {

    override suspend fun completeOnboarding() {
        onboardingManager.completeOnBoarding()
    }
}
