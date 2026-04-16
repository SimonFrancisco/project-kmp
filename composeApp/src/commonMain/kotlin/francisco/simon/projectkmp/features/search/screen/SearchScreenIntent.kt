package francisco.simon.projectkmp.features.search.screen

sealed interface SearchScreenIntent {
    data object TryAgain : SearchScreenIntent
    data object LoadMoreCourses : SearchScreenIntent
    data object ClearSearch : SearchScreenIntent
    data class Search(val query: String) : SearchScreenIntent
    data class CourseClicked(val courseId: Int) : SearchScreenIntent
}
