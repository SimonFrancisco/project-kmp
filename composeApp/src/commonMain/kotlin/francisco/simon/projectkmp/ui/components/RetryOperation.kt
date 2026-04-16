package francisco.simon.projectkmp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import francisco.simon.projectkmp.ui.theme.ProjectKmp
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.try_again

@Composable
fun RetryCall(
    modifier: Modifier = Modifier,
    errorRes: StringResource,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ErrorText(errorRes)
            Spacer(modifier = Modifier.height(4.dp))
            RetryButton(onClick)
        }
    }
}

@Composable
private fun RetryButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors()
            .copy(containerColor = MaterialTheme.colorScheme.onErrorContainer)
    ) {
        Text(
            text = stringResource(Res.string.try_again),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun ErrorText(errorRes: StringResource) {
    Text(
        text = stringResource(errorRes),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center,
    )
}

@Preview(showBackground = true)
@Composable
fun RetryCallPreview() {
    ProjectKmp {
        RetryCall(
            errorRes = Res.string.try_again,
            onClick = {}
        )
    }
}
