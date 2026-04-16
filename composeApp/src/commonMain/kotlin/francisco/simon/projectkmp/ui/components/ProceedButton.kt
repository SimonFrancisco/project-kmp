package francisco.simon.projectkmp.ui.components

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import francisco.simon.projectkmp.ui.utils.CORNER_SIZE_PERCENTAGE30
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ProceedButton(
    onClick: () -> Unit,
    buttonText: StringResource
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(CornerSize(CORNER_SIZE_PERCENTAGE30)),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
            contentColor = MaterialTheme.colorScheme.surfaceContainerLowest
        )
    ) {
        Text(
            text = stringResource(buttonText),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.ExtraBold

        )
    }
}
