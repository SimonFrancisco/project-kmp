package francisco.simon.projectkmp.features.courses.screen

sealed interface UserCourseScreenIntent {
    data object TryAgain : UserCourseScreenIntent
    data class CourseClicked(val courseId: Int) : UserCourseScreenIntent
}
