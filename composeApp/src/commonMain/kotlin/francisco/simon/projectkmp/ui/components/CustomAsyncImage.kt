package francisco.simon.projectkmp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.compose_multiplatform
import projectkmp.composeapp.generated.resources.ic_loading_coil

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
        placeholder = painterResource(Res.drawable.ic_loading_coil),
        error = painterResource(Res.drawable.compose_multiplatform)
    )
}
