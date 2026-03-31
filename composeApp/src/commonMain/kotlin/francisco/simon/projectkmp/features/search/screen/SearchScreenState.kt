package francisco.simon.projectkmp.features.search.screen

import francisco.simon.projectkmp.features.search.domain.entity.SearchCourse
import org.jetbrains.compose.resources.StringResource

sealed interface SearchScreenState {
    object Idle:SearchScreenState
    object Loading : SearchScreenState
    data class Success(val courses: List<SearchCourse>) : SearchScreenState
    data class Error(val errorMessageRes: StringResource) : SearchScreenState
}
