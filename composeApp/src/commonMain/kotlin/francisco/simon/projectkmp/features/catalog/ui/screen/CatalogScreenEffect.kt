package francisco.simon.projectkmp.features.catalog.ui.screen

sealed interface CatalogScreenEffect {
    data class NavigateToCourseDetail(val courseId: Int) : CatalogScreenEffect
}
