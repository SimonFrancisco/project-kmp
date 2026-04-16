package francisco.simon.projectkmp.features.search.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.ic_clear
import projectkmp.composeapp.generated.resources.search_place_holder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchAppBar(
    modifier: Modifier = Modifier,
    value: String,
    onIntent: (SearchScreenIntent) -> Unit
) {
    SearchBar(
        modifier = modifier,
        query = value,
        onQueryChange = {
            onIntent(SearchScreenIntent.Search(it))
        },
        active = false,
        onActiveChange = { },
        onSearch = { },
        placeholder = { Text(text = stringResource(resource = Res.string.search_place_holder)) },
        trailingIcon = {
            AnimatedVisibility(
                visible = value.isNotBlank(),
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut(),
            ) {
                IconButton(
                    onClick = {
                        onIntent(SearchScreenIntent.ClearSearch)
                    },
                ) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_clear),
                        contentDescription = null
                    )
                }
            }
        },
        content = {}
    )
}
