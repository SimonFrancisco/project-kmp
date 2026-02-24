package francisco.simon.projectkmp.features.login.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import francisco.simon.projectkmp.ui.utils.VerticalSpacerMedium

@Composable
fun OnboardingScreen(
    onNavigateToLoginScreen: () -> Unit
) {
    Scaffold { innerPaddings ->
        OnboardingScreenContent(
            onNavigateToLoginScreen = onNavigateToLoginScreen,
            modifier = Modifier.padding(innerPaddings)
        )
    }
}

@Composable
private fun OnboardingScreenContent(
    onNavigateToLoginScreen: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CompanyImage()
        VerticalSpacerMedium()
        LabelAndDescription()
        VerticalSpacerMedium()
        ProceedButton(onNavigateToLoginScreen)
    }
}

@Composable
@Preview(showBackground = true)
private fun OnboardingScreenPreview(){
    OnboardingScreen(
        onNavigateToLoginScreen = {

        }
    )
}