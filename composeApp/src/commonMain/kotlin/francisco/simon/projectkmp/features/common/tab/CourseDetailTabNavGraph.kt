package francisco.simon.projectkmp.features.common.tab

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import francisco.simon.projectkmp.features.common.CourseDetailScreen

@Composable
fun CourseDetailNavGraph(
    courseId: Int,
    navController: NavHostController,
    startDestination: Any,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable<CourseDetailTabGraph.Information> {
            CourseDetailScreen(courseId = courseId)
        }
        composable<CourseDetailTabGraph.Review> {}
        composable<CourseDetailTabGraph.Units> {}
    }
}
