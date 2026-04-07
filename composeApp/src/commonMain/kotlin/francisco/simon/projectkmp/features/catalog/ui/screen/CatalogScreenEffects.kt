package francisco.simon.projectkmp.features.catalog.ui.screen

sealed interface CatalogScreenEffects {
    data class NavigateToCourseDetail(val courseId: Int) : CatalogScreenEffects
}
