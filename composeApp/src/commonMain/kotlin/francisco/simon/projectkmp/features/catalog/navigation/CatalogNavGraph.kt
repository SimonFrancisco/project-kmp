package francisco.simon.projectkmp.features.catalog.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import francisco.simon.projectkmp.features.catalog.navigation.CatalogGraph.CatalogRoute
import francisco.simon.projectkmp.features.catalog.navigation.CatalogGraph.CourseDetailRoute
import francisco.simon.projectkmp.features.catalog.ui.screen.CatalogScreen
import francisco.simon.projectkmp.features.common.tab.CourseDetailTabMainScreen

fun NavGraphBuilder.catalogNavGraph(
    navController: NavController
) {
    navigation<CatalogGraph>(startDestination = CatalogRoute) {
        composable<CatalogRoute> {
            CatalogScreen(
                onOpenDetailScreen = { courseId ->
                    navController.navigate(CourseDetailRoute(courseId))
                }
            )
        }
        composable<CourseDetailRoute> { entry ->
            val route: CourseDetailRoute = entry.toRoute()
            CourseDetailTabMainScreen(
                courseId = route.courseId,
            )
        }
    }
}
