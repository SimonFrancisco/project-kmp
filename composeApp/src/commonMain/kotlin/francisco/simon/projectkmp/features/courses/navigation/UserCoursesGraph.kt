package francisco.simon.projectkmp.features.courses.navigation

import kotlinx.serialization.Serializable

@Serializable
data object UserCoursesGraph {

    @Serializable
    data object CoursesRoute

    @Serializable
    data class CourseDetailRoute(
        val courseId: Int
    )
}
