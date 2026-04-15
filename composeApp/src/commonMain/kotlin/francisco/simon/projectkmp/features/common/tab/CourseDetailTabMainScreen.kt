package francisco.simon.projectkmp.features.common.tab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@Composable
fun CourseDetailTabMainScreen(
    courseId: Int
) {
    val navController = rememberNavController()
    Scaffold { innerPadding ->
        Column {
            CourseDetailTabNavigation(
                modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
                navController = navController,
                tabs = courseDetailTabs
            )
            CourseDetailNavGraph(
                courseId = courseId,
                navController = navController,
                startDestination = CourseDetailTabGraph.Information,
            )
        }
    }
}
