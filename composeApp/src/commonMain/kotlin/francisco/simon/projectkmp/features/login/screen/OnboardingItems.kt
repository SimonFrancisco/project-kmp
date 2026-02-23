package francisco.simon.projectkmp.features.login.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.compose_multiplatform
import projectkmp.composeapp.generated.resources.onboarding_button_text
import projectkmp.composeapp.generated.resources.onboarding_description
import projectkmp.composeapp.generated.resources.onboarding_label

private const val ONBOARDING_IMAGE_LINK =
    "https://i.pinimg.com/736x/64/e3/95/64e395eaaf8718be83ca264bbe082ea7.jpg"

@Composable
internal fun ColumnScope.CompanyImage() {
    AsyncImage(
        model = ONBOARDING_IMAGE_LINK,
        contentDescription = null,
        modifier = Modifier.size(240.dp)
            .weight(1f),
        placeholder = painterResource(Res.drawable.compose_multiplatform),
        error = painterResource(Res.drawable.compose_multiplatform)
    )
}

@Composable
internal fun ColumnScope.LabelAndDescription() {
    Column(
        modifier = Modifier.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(Res.string.onboarding_label).uppercase(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(Modifier.height(16.dp))
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