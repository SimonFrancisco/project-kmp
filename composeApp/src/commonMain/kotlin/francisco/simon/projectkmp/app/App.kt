package francisco.simon.projectkmp.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import francisco.simon.projectkmp.navigation.routeClass
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
                LaunchedEffect(state) {
                    when (state) {
                        AppState.Authorized -> navController.navigate(CatalogGraph) {
                            popUpTo(0)
                        }

                        AppState.Unauthorized -> navController.navigate(OnboardingRoute) {
                            popUpTo(0)
                        }
                        else -> Unit
                    }
                }
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
    if (currentBackStackEntry != null && currentBackStackEntry.routeClass() !in hiddenBottomBarRoutes) {
        AppNavigationBar(
            navController = navController,
            tabs = mainTabs
        )
    }
}
