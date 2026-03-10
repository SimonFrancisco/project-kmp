package francisco.simon.projectkmp.features.search.navigation

import kotlinx.serialization.Serializable

@Serializable
data object SearchGraph {

    @Serializable
    data object SearchRoute

    @Serializable
    data class CourseDetailRoute(
        val courseId: Int
    )
    
}
