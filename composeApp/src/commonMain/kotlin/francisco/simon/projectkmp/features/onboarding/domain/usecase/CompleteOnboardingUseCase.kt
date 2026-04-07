package francisco.simon.projectkmp.features.onboarding.domain.usecase

import francisco.simon.projectkmp.core.utils.runCatchingCancellable
import francisco.simon.projectkmp.features.onboarding.domain.repository.OnboardingRepository

class CompleteOnboardingUseCase(
    private val repository: OnboardingRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return runCatchingCancellable {
            repository.completeOnboarding()
        }
    }
}
