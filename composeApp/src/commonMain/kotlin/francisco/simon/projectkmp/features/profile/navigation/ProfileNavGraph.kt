package francisco.simon.projectkmp.features.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import francisco.simon.projectkmp.features.profile.navigation.ProfileGraph.ProfileRoute
import francisco.simon.projectkmp.features.profile.screen.ProfileScreen

fun NavGraphBuilder.profileNavGraph(
    navController: NavController
) {
    navigation<ProfileGraph>(startDestination = ProfileRoute) {
        composable<ProfileRoute> {
            ProfileScreen(
                onNavigateToLogin = {

                }
            )
        }

    }
}
