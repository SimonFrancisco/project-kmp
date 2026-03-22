package francisco.simon.projectkmp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import francisco.simon.projectkmp.auth.navigation.AuthRoute
import francisco.simon.projectkmp.features.onboarding.navigation.OnboardingRoute
import francisco.simon.projectkmp.navigation.AppNavGraph
import francisco.simon.projectkmp.navigation.routeClass
import francisco.simon.projectkmp.ui.navigation.AppNavigationBar
import francisco.simon.projectkmp.ui.navigation.mainTabs
import francisco.simon.projectkmp.ui.theme.ProjectKmp

@Composable
@Preview
fun App() {
    ProjectKmp {
        val navController = rememberNavController()
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        Scaffold(
            bottomBar = {
                BottomBarSettings(currentBackStackEntry, navController)
            }
        ) { innerPadding ->
            AppNavGraph(
                navController = navController,
                startDestination = AuthRoute,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    }
}

private val hiddenBottomBarRoutes = setOf(
    OnboardingRoute::class,
    AuthRoute::class
)

@Composable
private fun BottomBarSettings(
    currentBackStackEntry: NavBackStackEntry?,
    navController: NavHostController
) {
    if (currentBackStackEntry.routeClass() !in hiddenBottomBarRoutes) {
        AppNavigationBar(
            navController = navController,
            tabs = mainTabs
        )
    }
}
