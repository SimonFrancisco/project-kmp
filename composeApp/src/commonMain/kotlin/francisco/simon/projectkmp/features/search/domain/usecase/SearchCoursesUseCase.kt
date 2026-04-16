package francisco.simon.projectkmp.features.search.domain.usecase

import francisco.simon.projectkmp.core.domain.entity.Course
import francisco.simon.projectkmp.core.domain.repository.CoreRepository
import francisco.simon.projectkmp.core.utils.asResult
import francisco.simon.projectkmp.features.search.domain.repository.SearchRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

typealias SearchCourseAnswer = Flow<Result<List<Course>>>

@OptIn(ExperimentalCoroutinesApi::class)
class SearchCoursesUseCase(
    private val searchRepository: SearchRepository,
    private val coreRepository: CoreRepository
) {
    suspend operator fun invoke(query: String): SearchCourseAnswer {
        return searchRepository.searchCourses(query).mapLatest { listIds ->
            coreRepository.getCourses(listIds)
        }.asResult()
    }
}
