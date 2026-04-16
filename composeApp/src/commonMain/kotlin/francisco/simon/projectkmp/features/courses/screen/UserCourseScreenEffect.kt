package francisco.simon.projectkmp.features.courses.screen

sealed interface UserCourseScreenEffect {
    data class NavigateToCourseDetail(val courseId: Int) : UserCourseScreenEffect
}
