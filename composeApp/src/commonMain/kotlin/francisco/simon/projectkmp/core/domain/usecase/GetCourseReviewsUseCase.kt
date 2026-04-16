package francisco.simon.projectkmp.core.domain.usecase

import francisco.simon.projectkmp.core.domain.entity.Review
import francisco.simon.projectkmp.core.domain.repository.CoreRepository
import francisco.simon.projectkmp.core.utils.runCatchingCancellable

class GetCourseReviewsUseCase(
    private val repository: CoreRepository
) {
    suspend operator fun invoke(courseId: Int): Result<List<Review>> {
        return runCatchingCancellable {
            repository.getReviews(courseId)
        }
    }
}
