package francisco.simon.projectkmp.features.onboarding.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import francisco.simon.projectkmp.ui.utils.VerticalSpacerMedium
import org.jetbrains.compose.resources.stringResource
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.onboarding_button_text
import projectkmp.composeapp.generated.resources.onboarding_description
import projectkmp.composeapp.generated.resources.onboarding_label

@Composable
internal fun LabelAndDescription(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(Res.string.onboarding_label).uppercase(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.ExtraBold
        )
        VerticalSpacerMedium()
        Text(
            text = stringResource(Res.string.onboarding_description).trim(),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
internal fun ProceedButton(onNavigateToLoginScreen: () -> Unit) {
    Button(
        onClick = onNavigateToLoginScreen,
        shape = RoundedCornerShape(CornerSize(30)),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
            contentColor = MaterialTheme.colorScheme.surfaceContainerLowest
        )
    ) {
        Text(
            text = stringResource(Res.string.onboarding_button_text),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.ExtraBold

        )
    }
}
