package francisco.simon.projectkmp.features.courses.screen

import francisco.simon.projectkmp.core.domain.entity.Course
import org.jetbrains.compose.resources.StringResource

sealed interface UserCourseScreenState {
    object Loading : UserCourseScreenState
    data class Success(val courses: List<Course>) : UserCourseScreenState
    data class Error(val errorMessageRes: StringResource) : UserCourseScreenState
}
