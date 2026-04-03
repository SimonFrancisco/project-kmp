package francisco.simon.projectkmp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import francisco.simon.projectkmp.features.auth.navigation.AuthRoute
import francisco.simon.projectkmp.features.auth.ui.AuthScreen
import francisco.simon.projectkmp.features.catalog.navigation.CatalogGraph
import francisco.simon.projectkmp.features.catalog.navigation.catalogNavGraph
import francisco.simon.projectkmp.features.courses.navigation.coursesNavGraph
import francisco.simon.projectkmp.features.onboarding.navigation.OnboardingRoute
import francisco.simon.projectkmp.features.onboarding.screen.OnboardingScreen
import francisco.simon.projectkmp.features.profile.navigation.profileNavGraph
import francisco.simon.projectkmp.features.search.navigation.searchNavGraph

@Composable
internal fun AppNavGraph(
    navController: NavHostController,
    startDestination: Any,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable<OnboardingRoute> {
            OnboardingScreen(
                onNavigateToCatalogScreen = {
                    navController.navigate(AuthRoute)
                }
            )
        }
        composable<AuthRoute> {
            AuthScreen(
                onAuthFinished = {
                    navController.navigate(CatalogGraph) {
                        popUpTo(AuthRoute) {
                            inclusive = true
                        }
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        catalogNavGraph(navController)
        searchNavGraph(navController)
        coursesNavGraph()
        profileNavGraph(navController)
    }
}
