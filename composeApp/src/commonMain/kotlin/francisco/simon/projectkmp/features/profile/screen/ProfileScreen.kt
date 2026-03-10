package francisco.simon.projectkmp.features.profile.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import francisco.simon.projectkmp.ui.components.CustomAsyncImage
import francisco.simon.projectkmp.ui.components.ProceedButton
import francisco.simon.projectkmp.ui.theme.paddingMedium
import francisco.simon.projectkmp.ui.utils.VerticalSpacerMedium
import org.jetbrains.compose.resources.stringResource
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.login_button_text
import projectkmp.composeapp.generated.resources.non_authorized_user

private const val STEPIK_IMAGE_LINK =
    "https://yt3.googleusercontent.com/WETAq-GLqrVFUTjm_Oq-GUCjr4EIp9p7JsAZQoslCAcpi2VO-xyQOKjHrXhivUv-DkMzZ9UiPQ=s900-c-k-c0x00ffffff-no-rj"

@Composable
fun ProfileScreen(
    onNavigateToLogin: () -> Unit
) {
    Scaffold { innerPaddings ->
        ProfileScreenContent(
            onNavigateToLogin = onNavigateToLogin,
            modifier = Modifier.padding(innerPaddings)
        )
    }
}

@Composable
private fun ProfileScreenContent(
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingMedium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CustomAsyncImage(
            model = STEPIK_IMAGE_LINK
        )
        VerticalSpacerMedium()
        Text(
            text = stringResource(Res.string.non_authorized_user),
            style = MaterialTheme.typography.titleLarge
        )
        VerticalSpacerMedium()
        ProceedButton(
            onClick = {},
            buttonText = Res.string.login_button_text
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ProfileScreenScreenPreview() {
    ProfileScreen(
        onNavigateToLogin = {}
    )
}
