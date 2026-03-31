package francisco.simon.projectkmp.features.courses.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import francisco.simon.projectkmp.features.courses.navigation.CoursesGraph.CourseDetailRoute
import francisco.simon.projectkmp.features.courses.navigation.CoursesGraph.CoursesRoute
import francisco.simon.projectkmp.features.courses.screen.CoursesScreen

fun NavGraphBuilder.coursesNavGraph() {
    navigation<CoursesGraph>(startDestination = CoursesRoute) {
        composable<CoursesRoute> {
            CoursesScreen()
        }
        composable<CourseDetailRoute>{}
    }
}
