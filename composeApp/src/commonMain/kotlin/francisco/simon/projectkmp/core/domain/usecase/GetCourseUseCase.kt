package francisco.simon.projectkmp.core.domain.usecase

import francisco.simon.projectkmp.core.domain.entity.Course
import francisco.simon.projectkmp.core.domain.repository.CoreRepository
import francisco.simon.projectkmp.core.utils.runCatchingCancellable

class GetCourseUseCase(
    private val repository: CoreRepository
) {
    suspend operator fun invoke(courseId: Int): Result<Course> {
        return runCatchingCancellable {
            repository.getCourse(courseId)
        }
    }
}
