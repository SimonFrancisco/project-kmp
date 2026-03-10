package francisco.simon.projectkmp.features.onboarding.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import francisco.simon.projectkmp.ui.components.ProceedButton
import francisco.simon.projectkmp.ui.theme.paddingMedium
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.onboarding_button_text

private const val ONBOARDING_IMAGE_LINK =
    "https://yt3.googleusercontent.com/WETAq-GLqrVFUTjm_Oq-GUCjr4EIp9p7JsAZQoslCAcpi2VO-xyQOKjHrXhivUv-DkMzZ9UiPQ=s900-c-k-c0x00ffffff-no-rj"

@Composable
fun OnboardingScreen(
    onNavigateToCatalogScreen: () -> Unit
) {
    Scaffold { innerPaddings ->
        OnboardingScreenContent(
            onNavigateToCatalogScreen = onNavigateToCatalogScreen,
            modifier = Modifier.padding(innerPaddings)
        )
    }
}

@Composable
private fun OnboardingScreenContent(
    onNavigateToCatalogScreen: () -> Unit,
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
            Modifier.weight(1f)
        )
        ProceedButton(
            onNavigateToCatalogScreen,
            buttonText = Res.string.onboarding_button_text
        )
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
        onNavigateToCatalogScreen = {}
    )
}
