package francisco.simon.projectkmp.core.domain.usecase

import francisco.simon.projectkmp.core.domain.entity.Course
import francisco.simon.projectkmp.core.domain.repository.CoreRepository
import francisco.simon.projectkmp.core.utils.runCatchingCancellable

class GetCoursesUseCase(
    private val repository: CoreRepository
) {
    suspend operator fun invoke(listInt: List<Int>): Result<List<Course>> {
        return runCatchingCancellable {
            repository.getCourses(listInt)
        }
    }
}
