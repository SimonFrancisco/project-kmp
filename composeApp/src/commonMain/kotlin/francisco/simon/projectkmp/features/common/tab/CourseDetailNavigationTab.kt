package francisco.simon.projectkmp.features.common.tab

import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import org.jetbrains.compose.resources.stringResource

private const val FIRST_TAB_INDEX = 0
private const val NEW_TAB_INDEX_NOT_FOUND_RESULT = -1

@Composable
internal fun CourseDetailTabNavigation(
    modifier: Modifier = Modifier,
    navController: NavController,
    tabs: List<CourseDetailTab>,

) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination

    var selectedDestination by rememberSaveable { mutableIntStateOf(FIRST_TAB_INDEX) }
    val newIndex = tabs.indexOfFirst { tab ->
        currentDestination?.hasRoute(tab.route::class) == true
    }
    if (newIndex != NEW_TAB_INDEX_NOT_FOUND_RESULT) {
        selectedDestination = newIndex
    }

    PrimaryTabRow(selectedTabIndex = selectedDestination, modifier = modifier) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = selectedDestination == index,
                onClick = {
                    navController.navigate(tab.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                text = {
                    Text(stringResource(tab.labelRes))
                }
            )
        }
    }
}
