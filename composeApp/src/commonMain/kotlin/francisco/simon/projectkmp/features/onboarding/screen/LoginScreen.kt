package francisco.simon.projectkmp.features.onboarding.screen

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
import androidx.compose.ui.unit.dp
import francisco.simon.projectkmp.ui.utils.VerticalSpacerMedium

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
            .padding(16.dp)
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginLabel()
        VerticalSpacerMedium()
        EmailTextField()
        VerticalSpacerMedium()
        PasswordTextField()
        VerticalSpacerMedium()
        LoginButton()
    }
}

@Composable
@Preview(showBackground = true)
private fun LoginScreenPreview() {
    LoginScreen()
}