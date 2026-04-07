package francisco.simon.projectkmp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import francisco.simon.projectkmp.app.DummyRoute
import francisco.simon.projectkmp.features.auth.navigation.AuthGraph
import francisco.simon.projectkmp.features.auth.navigation.authNavGraph
import francisco.simon.projectkmp.features.catalog.navigation.catalogNavGraph
import francisco.simon.projectkmp.features.courses.navigation.userCoursesNavGraph
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
        composable<DummyRoute> {}
        composable<OnboardingRoute> {
            OnboardingScreen(
                onOpenAuthScreen = {
                    navController.navigate(AuthGraph) {
                        popUpTo(OnboardingRoute) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
        authNavGraph(navController)
        catalogNavGraph(navController)
        searchNavGraph(navController)
        userCoursesNavGraph(navController)
        profileNavGraph(navController)
    }
}
