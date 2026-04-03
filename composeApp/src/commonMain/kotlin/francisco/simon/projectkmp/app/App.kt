package francisco.simon.projectkmp.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import francisco.simon.projectkmp.features.auth.navigation.AuthRoute
import francisco.simon.projectkmp.features.catalog.navigation.CatalogGraph
import francisco.simon.projectkmp.features.onboarding.navigation.OnboardingRoute
import francisco.simon.projectkmp.navigation.AppNavGraph
import francisco.simon.projectkmp.navigation.matches
import francisco.simon.projectkmp.ui.navigation.AppNavigationBar
import francisco.simon.projectkmp.ui.navigation.mainTabs
import francisco.simon.projectkmp.ui.theme.ProjectKmp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    ProjectKmp {
        val navController = rememberNavController()
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val appViewModel: AppViewModel = koinViewModel()
        val state by appViewModel.authorized.collectAsStateWithLifecycle()

        if (state != AppState.Loading) {
            Scaffold(
                bottomBar = {
                    BottomBarSettings(currentBackStackEntry, navController)
                }
            ) { innerPadding ->
                AppNavGraph(
                    navController = navController,
                    startDestination = when (state) {
                        AppState.Authorized -> CatalogGraph
                        AppState.Unauthorized -> OnboardingRoute
                        else -> OnboardingRoute
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }
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
    val shouldShow = currentBackStackEntry != null &&
        hiddenBottomBarRoutes.none { currentBackStackEntry.destination.matches(it) }

    if (shouldShow) {
        AppNavigationBar(
            navController = navController,
            tabs = mainTabs
        )
    }
}
