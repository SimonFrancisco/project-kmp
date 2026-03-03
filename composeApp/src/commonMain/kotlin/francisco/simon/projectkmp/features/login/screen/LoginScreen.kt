package francisco.simon.projectkmp.features.login.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import francisco.simon.projectkmp.ui.theme.paddingMedium
import francisco.simon.projectkmp.ui.utils.EventConsumer
import francisco.simon.projectkmp.ui.utils.VerticalSpacerXMedium

@Composable
fun LoginScreen(
    onNavigateToFriendsScreen: () -> Unit
) {
    val viewModel: LoginScreenViewModel = viewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()

    Scaffold { innerPaddings ->
        LoginScreenContent(
            modifier = Modifier.padding(innerPaddings),
            state = state.value,
            onUsernameChange = viewModel::onUsernameChanged,
            onPasswordChange = viewModel::onPasswordChanged,
            onButtonClick = viewModel::onLogin
        )
    }

    EventConsumer(viewModel.events) {
        when (it) {
            is LoginUiEvent.LoginSuccessEvent -> {
                onNavigateToFriendsScreen()
            }
        }
    }
}

@Composable
private fun LoginScreenContent(
    modifier: Modifier = Modifier,
    state: LoginUiState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingMedium)
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginLabel()
        VerticalSpacerXMedium()
        UsernameTextField(state.username, onUsernameChange)
        VerticalSpacerXMedium()
        PasswordTextField(state.password, onPasswordChange)
        VerticalSpacerXMedium()
        LoginButton(isActive = state.isLoginButtonActive, onClick = onButtonClick)
    }
}

@Composable
@Preview(showBackground = true)
private fun LoginScreenPreview() {
    LoginScreen {}
}
