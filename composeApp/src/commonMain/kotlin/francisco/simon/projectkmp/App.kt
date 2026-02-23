package francisco.simon.projectkmp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import francisco.simon.projectkmp.features.onboarding.navigation.OnboardingRoute
import francisco.simon.projectkmp.navigation.AppNavGraph
import francisco.simon.projectkmp.ui.theme.ProjectKmp

@Composable
@Preview
fun App() {
    ProjectKmp {
        Box {
            val navController = rememberNavController()
            AppNavGraph(
                navController = navController,
                startDestination = OnboardingRoute,
                modifier = Modifier
                    .safeContentPadding()
                    .fillMaxSize()
            )
        }
    }
}