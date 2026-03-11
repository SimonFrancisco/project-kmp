package francisco.simon.projectkmp.features.catalog.ui.screen

import francisco.simon.projectkmp.features.catalog.ui.model.CoursesUI
import org.jetbrains.compose.resources.StringResource

sealed interface CatalogScreenState {
    object Loading : CatalogScreenState
    data class Success(
        val courses: List<CoursesUI>
    ) : CatalogScreenState

    data class Error(val errorMessageRes: StringResource) : CatalogScreenState
}
