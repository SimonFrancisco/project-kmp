package francisco.simon.projectkmp.features.search.domain.usecase

import francisco.simon.projectkmp.core.utils.asResult
import francisco.simon.projectkmp.features.search.domain.entity.SearchCourse
import francisco.simon.projectkmp.features.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

typealias SearchCourseAnswer = Flow<Result<List<SearchCourse>>>

class SearchCoursesUseCase(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(query: String): SearchCourseAnswer {
        return repository.searchCourses(query).asResult()
    }
}
