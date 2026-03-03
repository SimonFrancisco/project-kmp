package francisco.simon.projectkmp.features.onboarding.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import francisco.simon.projectkmp.ui.components.CustomAsyncImage
import francisco.simon.projectkmp.ui.theme.paddingMedium
import francisco.simon.projectkmp.ui.utils.VerticalSpacerXMedium

private const val ONBOARDING_IMAGE_LINK =
    "https://fsd.multiurok.ru/html/2019/05/07/s_5cd1a91d8c411/1153487_2.png"

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
            .padding(paddingMedium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomAsyncImage(
            model = ONBOARDING_IMAGE_LINK,
            modifier = Modifier.weight(1f)
        )
        VerticalSpacerXMedium()
        LabelAndDescription(
            modifier = Modifier.weight(1f)
        )
        ProceedButton(onNavigateToLoginScreen)
    }
}

@Composable
@Preview(showBackground = true)
private fun OnboardingScreenPreview(){
    OnboardingScreen(
        onNavigateToLoginScreen = {}
    )
}
