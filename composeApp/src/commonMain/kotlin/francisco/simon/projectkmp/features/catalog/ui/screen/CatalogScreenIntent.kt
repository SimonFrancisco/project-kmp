package francisco.simon.projectkmp.features.catalog.ui.screen

sealed interface CatalogScreenIntent {
    data object Refresh : CatalogScreenIntent
    data object TryAgain : CatalogScreenIntent
    data object LoadMoreCourses : CatalogScreenIntent
    data class CourseClicked(val courseId: Int) : CatalogScreenIntent
}
