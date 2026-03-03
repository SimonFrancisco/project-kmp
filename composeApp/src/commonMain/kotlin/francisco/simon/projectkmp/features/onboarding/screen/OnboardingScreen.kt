package francisco.simon.projectkmp.features.onboarding.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import francisco.simon.projectkmp.ui.theme.paddingMedium

private const val ONBOARDING_IMAGE_LINK =
    "https://static.vecteezy.com/system/resources/previews/068/842/002/non_2x/vk-logo-icon-vk-app-transparent-background-free-png.png"

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
        CarouselWithIndicators(
            vkRandomPictures,
            modifier.weight(1f)
        )
        ProceedButton(onNavigateToLoginScreen)
    }
}

private val vkRandomPictures = listOf<String>(
    ONBOARDING_IMAGE_LINK,
    ONBOARDING_IMAGE_LINK,
    ONBOARDING_IMAGE_LINK
)

@Composable
@Preview(showBackground = true)
private fun OnboardingScreenPreview() {
    OnboardingScreen(
        onNavigateToLoginScreen = {}
    )
}
