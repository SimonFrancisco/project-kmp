package francisco.simon.projectkmp.features.courses.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import francisco.simon.projectkmp.features.courses.navigation.CoursesGraph.CourseDetailRoute
import francisco.simon.projectkmp.features.courses.navigation.CoursesGraph.CoursesRoute
import francisco.simon.projectkmp.features.courses.screen.CoursesScreen

fun NavGraphBuilder.coursesNavGraph(
    navController: NavController
) {
    navigation<CoursesGraph>(startDestination = CoursesRoute) {
        composable<CoursesRoute> {
            CoursesScreen(
                onNavigateToCourseDetailScreen = { courseId ->
                    navController.navigate(CourseDetailRoute(courseId))
                }
            )
        }
        composable<CourseDetailRoute> { entry ->
            val route: CourseDetailRoute = entry.toRoute()

        }
    }
}
