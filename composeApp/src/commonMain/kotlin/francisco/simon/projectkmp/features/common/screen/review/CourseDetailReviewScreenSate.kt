package francisco.simon.projectkmp.features.common.screen.review

import org.jetbrains.compose.resources.StringResource

sealed interface CourseDetailReviewScreenSate {
    object Loading : CourseDetailReviewScreenSate
    data class Success(
        val reviews: List<ReviewUI>
    ) : CourseDetailReviewScreenSate

    data class Error(val errorMessageRes: StringResource) : CourseDetailReviewScreenSate
}
