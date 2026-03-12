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
import francisco.simon.projectkmp.features.onboarding.navigation.OnboardingRoute
import francisco.simon.projectkmp.navigation.AppNavGraph
import francisco.simon.projectkmp.navigation.routeClass
import francisco.simon.projectkmp.ui.navigation.AppNavigationBar
import francisco.simon.projectkmp.ui.navigation.mainTabs
import francisco.simon.projectkmp.ui.theme.ProjectKmp
import org.publicvalue.multiplatform.oidc.flows.CodeAuthFlowFactory

@Composable
@Preview
fun App(
    authFlowFactory: CodeAuthFlowFactory? = null
) {
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
                startDestination = OnboardingRoute,
                authFlowFactory = authFlowFactory,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    }
}

@Composable
private fun BottomBarSettings(
    currentBackStackEntry: NavBackStackEntry?,
    navController: NavHostController
) {
    if (currentBackStackEntry.routeClass() != OnboardingRoute::class) {
        AppNavigationBar(
            navController = navController,
            tabs = mainTabs
        )
    }
}
