package francisco.simon.projectkmp.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import francisco.simon.projectkmp.ui.theme.paddingMedium
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuthScreen(
    onAuthFinished: (String) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: AuthViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    when (val current = state) {
        is AuthState.WebViewLoading -> StepikAuthWebView(
            authorizeUrl = StepikAuthConfig.buildAuthorizeUrl(),
            redirectUri = StepikAuthConfig.REDIRECT_URI,
            onCodeReceived = viewModel::onCodeReceived,
            modifier = Modifier.fillMaxSize()
        )
        is AuthState.ExchangingToken -> LoadingContent()
        is AuthState.Success -> LaunchedEffect(Unit) { onAuthFinished(current.accessToken) }
        is AuthState.Error -> ErrorContent(
            message = current.message,
            onRetry = onNavigateBack
        )
    }
}

@Composable
private fun LoadingContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingMedium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )
        TextButton(onClick = onRetry) {
            Text(text = "Back")
        }
    }
}
