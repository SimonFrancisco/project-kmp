package francisco.simon.projectkmp.features.courses.domain.usecase

import francisco.simon.projectkmp.core.domain.entity.Course
import francisco.simon.projectkmp.core.domain.repository.CoreRepository
import francisco.simon.projectkmp.core.utils.asResult
import francisco.simon.projectkmp.features.courses.domain.repository.UserCoursesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

@OptIn(ExperimentalCoroutinesApi::class)
class GetUserCoursesUseCase(
    private val userCoursesRepository: UserCoursesRepository,
    private val coreRepository: CoreRepository
) {
    operator fun invoke(): Flow<Result<List<Course>>> {
        return userCoursesRepository.getCoursesId().mapLatest { listIds ->
            coreRepository.getCourses(listIds)
        }.asResult()
    }
}
