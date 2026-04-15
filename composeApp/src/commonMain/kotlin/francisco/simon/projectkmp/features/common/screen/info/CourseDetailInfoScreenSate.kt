package francisco.simon.projectkmp.features.common.screen.info

import francisco.simon.projectkmp.core.domain.entity.Course
import org.jetbrains.compose.resources.StringResource

sealed interface CourseDetailInfoScreenSate {
    object Loading : CourseDetailInfoScreenSate
    data class Success(
        val course: Course
    ) : CourseDetailInfoScreenSate

    data class Error(val errorMessageRes: StringResource) : CourseDetailInfoScreenSate
}
