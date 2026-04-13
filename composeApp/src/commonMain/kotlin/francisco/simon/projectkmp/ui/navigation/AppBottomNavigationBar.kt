package francisco.simon.projectkmp.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun AppNavigationBar(
    navController: NavController,
    tabs: List<AppTab>
) {
    NavigationBar {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStackEntry?.destination
        tabs.forEach { tab ->
            NavigationItem(currentDestination, tab, navController)
        }
    }
}

@Composable
private fun RowScope.NavigationItem(
    currentDestination: NavDestination?,
    tab: AppTab,
    navController: NavController
) {
    NavigationBarItem(
        selected = currentDestination?.hierarchy?.any { it.hasRoute(tab.graph::class) } == true,
        onClick = {
            navController.navigate(tab.graph) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        icon = {
            NavigationBarIcon(tab)
        },
        label = {
            NavigationBarLabel(tab)
        }
    )
}

@Composable
private fun NavigationBarLabel(tab: AppTab) {
    Text(stringResource(tab.labelRes))
}

@Composable
private fun NavigationBarIcon(tab: AppTab) {
    Icon(
        painter = painterResource(tab.iconRes),
        contentDescription = null
    )
}
