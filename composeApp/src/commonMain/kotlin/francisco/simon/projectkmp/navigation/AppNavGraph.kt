package francisco.simon.projectkmp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import francisco.simon.projectkmp.auth.AuthScreen
import francisco.simon.projectkmp.auth.navigation.AuthRoute
import francisco.simon.projectkmp.features.catalog.navigation.CatalogGraph
import francisco.simon.projectkmp.features.catalog.navigation.catalogNavGraph
import francisco.simon.projectkmp.features.courses.navigation.coursesNavGraph
import francisco.simon.projectkmp.features.onboarding.navigation.OnboardingRoute
import francisco.simon.projectkmp.features.onboarding.screen.OnboardingScreen
import francisco.simon.projectkmp.features.profile.navigation.profileNavGraph
import francisco.simon.projectkmp.features.search.navigation.searchNavGraph
import io.github.aakira.napier.Napier

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
                    navController.navigate(CatalogGraph) {
                        popUpTo(OnboardingRoute) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<AuthRoute> {
            AuthScreen(
                onAuthFinished = { accessToken ->
                    Napier.d(tag = "Auth", message = "Authorized, token: $accessToken")
                    navController.navigate(OnboardingRoute) {
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
        coursesNavGraph(navController)
        profileNavGraph(navController)
    }
}
