package francisco.simon.projectkmp.features.search.screen

import francisco.simon.projectkmp.core.domain.entity.Course
import org.jetbrains.compose.resources.StringResource

sealed interface SearchScreenState {
    object Idle : SearchScreenState
    object Loading : SearchScreenState
    data class Success(val courses: List<Course>) : SearchScreenState
    data class Error(val errorMessageRes: StringResource) : SearchScreenState
}
