package francisco.simon.projectkmp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.ic_stepik

@Composable
internal fun CustomAsyncImage(
    model: String,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        modifier = modifier,
        model = model,
        contentDescription = contentDescription,
        contentScale = contentScale,
        error = painterResource(Res.drawable.ic_stepik)
    )
}
