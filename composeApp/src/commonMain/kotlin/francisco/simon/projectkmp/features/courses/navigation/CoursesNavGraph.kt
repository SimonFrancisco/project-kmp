package francisco.simon.projectkmp.features.courses.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import francisco.simon.projectkmp.features.catalog.navigation.CatalogGraph
import francisco.simon.projectkmp.features.common.tab.CourseDetailTabMainScreen
import francisco.simon.projectkmp.features.courses.navigation.UserCoursesGraph.CourseDetailRoute
import francisco.simon.projectkmp.features.courses.navigation.UserCoursesGraph.CoursesRoute
import francisco.simon.projectkmp.features.courses.screen.UserCourseScreen

fun NavGraphBuilder.userCoursesNavGraph(
    navController: NavController
) {
    navigation<UserCoursesGraph>(startDestination = CoursesRoute) {
        composable<CoursesRoute> {
            UserCourseScreen(
                onOpenDetailScreen = { courseId ->
                    navController.navigate(CourseDetailRoute(courseId))
                }
            )
        }
        composable<CourseDetailRoute> { entry ->
            val route: CatalogGraph.CourseDetailRoute = entry.toRoute()
            CourseDetailTabMainScreen(
                courseId = route.courseId,
            )
        }
    }
}
