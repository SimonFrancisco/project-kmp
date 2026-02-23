package francisco.simon.projectkmp.features.onboarding.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

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
    ) {

    }
}


@Composable
@Preview(showBackground = true)
private fun LoginScreenPreview(){
    LoginScreen()
}