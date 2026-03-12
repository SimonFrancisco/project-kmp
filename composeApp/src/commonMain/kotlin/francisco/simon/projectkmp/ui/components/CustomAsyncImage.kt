package francisco.simon.projectkmp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.compose_multiplatform

@Composable
internal fun CustomAsyncImage(
    model: String,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier,
        error = painterResource(Res.drawable.compose_multiplatform)
    )
}
