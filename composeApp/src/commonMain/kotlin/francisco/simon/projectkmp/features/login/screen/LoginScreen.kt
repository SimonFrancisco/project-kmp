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
import francisco.simon.projectkmp.ui.theme.paddingMedium
import francisco.simon.projectkmp.ui.utils.VerticalSpacerXMedium

@Composable
fun LoginScreen() {
    Scaffold { innerPaddings ->
        LoginScreenContent(
            modifier = Modifier.padding(innerPaddings)
        )
    }
}

@Composable
private fun LoginScreenContent(
    modifier: Modifier = Modifier
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
        EmailTextField()
        VerticalSpacerXMedium()
        PasswordTextField()
        VerticalSpacerXMedium()
        LoginButton()
    }
}

@Composable
@Preview(showBackground = true)
private fun LoginScreenPreview() {
    LoginScreen()
}
