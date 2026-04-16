package francisco.simon.projectkmp.features.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import francisco.simon.projectkmp.features.auth.navigation.AuthGraph.AuthRoute
import francisco.simon.projectkmp.features.auth.navigation.AuthGraph.WebViewAuthRoute
import francisco.simon.projectkmp.features.auth.ui.auth.AuthScreen
import francisco.simon.projectkmp.features.auth.ui.webView.WebViewAuthScreen
import francisco.simon.projectkmp.features.catalog.navigation.CatalogGraph

fun NavGraphBuilder.authNavGraph(
    navController: NavController
) {
    navigation<AuthGraph>(startDestination = AuthRoute) {
        composable<AuthRoute> {
            AuthScreen(
                onOpenWebViewAuthScreen = {
                    navController.navigate(WebViewAuthRoute)
                }
            )
        }
        composable<WebViewAuthRoute> {
            WebViewAuthScreen(
                onAuthFinished = {
                    navController.navigate(CatalogGraph) {
                        popUpTo(AuthGraph) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
