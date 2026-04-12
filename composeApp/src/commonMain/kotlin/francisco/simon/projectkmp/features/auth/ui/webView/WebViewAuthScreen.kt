package francisco.simon.projectkmp.features.auth.ui.webView

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import francisco.simon.projectkmp.features.auth.StepikAuthWebView
import francisco.simon.projectkmp.features.auth.data.StepikAuthConfig
import francisco.simon.projectkmp.ui.components.FullScreenLoading
import francisco.simon.projectkmp.ui.components.RetryCall
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WebViewAuthScreen(
    onAuthFinished: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: WebViewAuthScreenViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    when (val current = state) {
        is WebViewAuthScreenState.WebViewAuth -> {
            StepikAuthWebView(
                authorizeUrl = StepikAuthConfig.buildAuthorizeUrl(),
                redirectUri = StepikAuthConfig.REDIRECT_URI,
                onCodeReceived = viewModel::onCodeReceived,
                modifier = Modifier.fillMaxSize()
            )
        }
        is WebViewAuthScreenState.Loading -> {
            FullScreenLoading()
        }
        is WebViewAuthScreenState.Success -> { onAuthFinished() }
        is WebViewAuthScreenState.Error -> RetryCall(
            modifier = Modifier.fillMaxSize(),
            errorRes = current.errorMessageRes,
            onClick = onNavigateBack
        )
    }
}
