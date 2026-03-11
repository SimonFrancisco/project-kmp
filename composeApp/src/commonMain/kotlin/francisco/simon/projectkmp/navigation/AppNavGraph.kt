package francisco.simon.projectkmp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import francisco.simon.projectkmp.features.catalog.navigation.CatalogGraph
import francisco.simon.projectkmp.features.catalog.navigation.catalogNavGraph
import francisco.simon.projectkmp.features.courses.navigation.coursesNavGraph
import francisco.simon.projectkmp.features.onboarding.navigation.OnboardingRoute
import francisco.simon.projectkmp.features.onboarding.screen.OnboardingScreen
import francisco.simon.projectkmp.features.profile.navigation.profileNavGraph
import francisco.simon.projectkmp.features.search.navigation.searchNavGraph
import org.publicvalue.multiplatform.oidc.flows.CodeAuthFlowFactory

@Composable
internal fun AppNavGraph(
    navController: NavHostController,
    startDestination: Any,
    authFlowFactory: CodeAuthFlowFactory?,
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
                    navController.navigate(CatalogGraph){
                        popUpTo(OnboardingRoute) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        catalogNavGraph(navController)
        searchNavGraph(navController)
        coursesNavGraph(navController)
        profileNavGraph(navController)
    }
}
