package francisco.simon.projectkmp.features.auth.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import francisco.simon.projectkmp.ui.components.ProceedButton
import francisco.simon.projectkmp.ui.theme.paddingMedium
import francisco.simon.projectkmp.ui.utils.EffectsConsumer
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.auth_login_button_text
import projectkmp.composeapp.generated.resources.ic_stepik

@Composable
fun AuthScreen(
    onOpenWebViewAuthScreen: () -> Unit,
    viewModel: AuthScreenViewModel = koinViewModel()
) {
    Scaffold { innerPaddings ->
        AuthScreenContent(
            onIntent = viewModel::onHandleIntent,
            modifier = Modifier.padding(innerPaddings)
        )
    }
    EffectsConsumer(viewModel.effects) { effect ->
        when (effect) {
            is AuthScreenEffect.NavigateToWebViewAuthScreen -> {
                onOpenWebViewAuthScreen()
            }
        }
    }
}

@Composable
private fun AuthScreenContent(
    onIntent: (AuthScreenIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingMedium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(16.dp)),
            painter = painterResource(Res.drawable.ic_stepik),
            contentDescription = null
        )
        ProceedButton(
            onClick = {
                onIntent(AuthScreenIntent.LoginButtonClicked)
            },
            buttonText = Res.string.auth_login_button_text
        )
    }
}
