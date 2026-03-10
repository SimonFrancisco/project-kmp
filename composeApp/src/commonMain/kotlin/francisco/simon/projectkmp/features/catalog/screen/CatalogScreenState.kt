package francisco.simon.projectkmp.features.catalog.screen

import org.jetbrains.compose.resources.StringResource

sealed interface CatalogScreenState {
    object Loading : CatalogScreenState
    data class Success(
        val courses: List<CoursesUI>,
        val nextDataLoading: Boolean = false
    ) : CatalogScreenState

    data class Error(val errorMessageRes: StringResource) : CatalogScreenState
}
