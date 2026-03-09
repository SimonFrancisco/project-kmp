package francisco.simon.projectkmp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import francisco.simon.projectkmp.features.friends.navigation.FriendsRoute
import francisco.simon.projectkmp.features.friends.screen.FriendsScreen
import francisco.simon.projectkmp.features.login.navigation.LoginRoute
import francisco.simon.projectkmp.features.login.screen.LoginScreen
import francisco.simon.projectkmp.features.onboarding.navigation.OnboardingRoute
import francisco.simon.projectkmp.features.onboarding.screen.OnboardingScreen

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
                onNavigateToLoginScreen = {
                    navController.navigate(LoginRoute)
                }
            )
        }
        composable<LoginRoute> {
            LoginScreen(
                onNavigateToFriendsScreen = {
                    navController.navigate(FriendsRoute) {
                        popUpTo(OnboardingRoute) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<FriendsRoute> {
            FriendsScreen()
        }
    }
}
