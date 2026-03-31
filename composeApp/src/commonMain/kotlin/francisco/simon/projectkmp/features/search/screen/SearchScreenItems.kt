package francisco.simon.projectkmp.features.search.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import francisco.simon.projectkmp.features.search.domain.entity.SearchCourse
import francisco.simon.projectkmp.ui.components.CustomAsyncImage
import francisco.simon.projectkmp.ui.theme.paddingSmall
import francisco.simon.projectkmp.ui.utils.CORNER_SIZE_PERCENTAGE10
import francisco.simon.projectkmp.ui.utils.HorizontalSpacerSmall
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
    onValueChange: (value: String) -> Unit,
    onClear: () -> Unit,
) {
    SearchBar(
        modifier = modifier,
        query = value,
        onQueryChange = onValueChange,
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
                    onClick = onClear,
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

@Composable
internal fun SearchCourseCard(
    course: SearchCourse,
    onCardClicked: (courseId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
            .clickable {
                onCardClicked(course.id)
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        elevation = CardDefaults.cardElevation(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomAsyncImage(
                model = course.cover,
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(corner = CornerSize(CORNER_SIZE_PERCENTAGE10)))
            )
            HorizontalSpacerSmall()

            Text(
                text = course.title,
                style = MaterialTheme.typography.labelLarge,
                overflow = TextOverflow.Ellipsis
            )

        }
    }
}
