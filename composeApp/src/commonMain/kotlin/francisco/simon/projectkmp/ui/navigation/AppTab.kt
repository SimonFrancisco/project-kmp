package francisco.simon.projectkmp.ui.navigation

import francisco.simon.projectkmp.features.catalog.navigation.CatalogGraph
import francisco.simon.projectkmp.features.courses.navigation.CoursesGraph
import francisco.simon.projectkmp.features.profile.navigation.ProfileGraph
import francisco.simon.projectkmp.features.search.navigation.SearchGraph
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.catalog_bottom_bar_label
import projectkmp.composeapp.generated.resources.courses_bottom_bar_label
import projectkmp.composeapp.generated.resources.ic_catalog
import projectkmp.composeapp.generated.resources.ic_courses
import projectkmp.composeapp.generated.resources.ic_profile
import projectkmp.composeapp.generated.resources.ic_search
import projectkmp.composeapp.generated.resources.profile_bottom_bar_label
import projectkmp.composeapp.generated.resources.search_bottom_bar_label

internal data class AppTab(
    val iconRes: DrawableResource,
    val labelRes: StringResource,
    val graph: Any
)

internal val mainTabs = listOf<AppTab>(
    AppTab(
        iconRes = Res.drawable.ic_catalog,
        labelRes = Res.string.catalog_bottom_bar_label,
        graph = CatalogGraph
    ),
    AppTab(
        iconRes = Res.drawable.ic_search,
        labelRes = Res.string.search_bottom_bar_label,
        graph = SearchGraph
    ),
    AppTab(
        iconRes = Res.drawable.ic_courses,
        labelRes = Res.string.courses_bottom_bar_label,
        graph = CoursesGraph
    ),
    AppTab(
        iconRes = Res.drawable.ic_profile,
        labelRes = Res.string.profile_bottom_bar_label,
        graph = ProfileGraph
    )
)
