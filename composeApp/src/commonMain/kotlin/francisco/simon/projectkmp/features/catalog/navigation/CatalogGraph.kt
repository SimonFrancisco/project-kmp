package francisco.simon.projectkmp.features.catalog.navigation

import kotlinx.serialization.Serializable

@Serializable
data object CatalogGraph {

    @Serializable
    data object CatalogRoute

    @Serializable
    data class CourseDetailRoute(
        val courseId: Int
    )
}
