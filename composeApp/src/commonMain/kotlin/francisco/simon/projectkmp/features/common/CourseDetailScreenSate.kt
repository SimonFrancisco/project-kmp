package francisco.simon.projectkmp.features.common

import francisco.simon.projectkmp.core.domain.entity.Course
import org.jetbrains.compose.resources.StringResource

sealed interface CourseDetailScreenSate {
    object Loading : CourseDetailScreenSate
    data class Success(
        val course: Course
    ) : CourseDetailScreenSate

    data class Error(val errorMessageRes: StringResource) : CourseDetailScreenSate
}
