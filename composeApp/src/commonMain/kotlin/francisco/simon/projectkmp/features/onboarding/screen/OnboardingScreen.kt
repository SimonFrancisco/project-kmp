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
import francisco.simon.projectkmp.ui.utils.EffectsConsumer
import org.jetbrains.compose.resources.DrawableResource
import org.koin.compose.viewmodel.koinViewModel
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.ic_stepik
import projectkmp.composeapp.generated.resources.onboarding_button_text

@Composable
fun OnboardingScreen(
    onOpenAuthScreen: () -> Unit,
    viewModel: OnBoardingScreenViewModel = koinViewModel()
) {
    Scaffold { innerPaddings ->
        OnboardingScreenContent(
            modifier = Modifier.padding(innerPaddings),
            onIntent = viewModel::onHandleIntent
        )
    }
    EffectsConsumer(viewModel.effects) { effect ->
        when (effect) {
            is OnBoardingScreenEffect.NavigateToAuthScreen -> {
                onOpenAuthScreen()
            }
        }
    }
}

@Composable
private fun OnboardingScreenContent(
    modifier: Modifier = Modifier,
    onIntent: (OnBoardingScreenIntent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingMedium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CarouselWithIndicators(
            stepikRandomPictures,
            Modifier.weight(1f)
        )
        ProceedButton(
            onClick = {
                onIntent(OnBoardingScreenIntent.CompleteOnBoarding)
            },
            buttonText = Res.string.onboarding_button_text
        )
    }
}

private val stepikRandomPictures = listOf<DrawableResource>(
    Res.drawable.ic_stepik,
    Res.drawable.ic_stepik,
    Res.drawable.ic_stepik
)

@Composable
@Preview(showBackground = true)
private fun OnboardingScreenPreview() {
    OnboardingScreenContent(
        onIntent = {},
    )
}
