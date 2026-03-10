package francisco.simon.projectkmp.features.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import francisco.simon.projectkmp.features.search.navigation.SearchGraph.CourseDetailRoute
import francisco.simon.projectkmp.features.search.navigation.SearchGraph.SearchRoute

fun NavGraphBuilder.searchNavGraph(
    navController: NavController
) {
    navigation<SearchGraph>(startDestination = SearchRoute) {
        composable<SearchRoute> {
//            SearchScreen(
//                onOpenDetailScreen = { courseId ->
//                    navController.navigate(CourseDetailRoute(courseId))
//                }
//            )
        }
        composable<CourseDetailRoute> { entry ->
            val route: CourseDetailRoute = entry.toRoute()
//            CourseDetailScreen(
//                accountId = route.courseId,
//                onGoBack = {
//                    navController.navigateUp()
//                }
//            )
        }
    }
}
