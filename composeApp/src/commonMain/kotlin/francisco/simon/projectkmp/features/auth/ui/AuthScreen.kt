package francisco.simon.projectkmp.features.auth.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import francisco.simon.projectkmp.features.auth.data.StepikAuthConfig
import francisco.simon.projectkmp.features.auth.StepikAuthWebView
import francisco.simon.projectkmp.ui.components.FullScreenLoading
import francisco.simon.projectkmp.ui.components.RetryCall
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuthScreen(
    onAuthFinished: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: AuthScreenViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    when (val current = state) {
        is AuthScreenState.WebViewAuth -> {
            StepikAuthWebView(
                authorizeUrl = StepikAuthConfig.buildAuthorizeUrl(),
                redirectUri = StepikAuthConfig.REDIRECT_URI,
                onCodeReceived = viewModel::onCodeReceived,
                modifier = Modifier.fillMaxSize()
            )
        }
        is AuthScreenState.Loading -> {
            FullScreenLoading()
        }
        is AuthScreenState.Success -> { onAuthFinished() }
        is AuthScreenState.Error -> RetryCall(
            errorRes = current.errorMessageRes,
            onClick = onNavigateBack
        )
    }
}
