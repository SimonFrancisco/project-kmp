package francisco.simon.projectkmp.features.catalog.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import francisco.simon.projectkmp.core.domain.entity.Course
import francisco.simon.projectkmp.ui.components.CustomAsyncImage
import francisco.simon.projectkmp.ui.theme.paddingSmall
import francisco.simon.projectkmp.ui.utils.HorizontalSpacerSmall

@Composable
internal fun CatalogCard(
    course: Course,
    onCardClicked: (courseId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.width(300.dp)
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
                .fillMaxSize()
                .padding(paddingSmall),
        ) {
            CustomAsyncImage(
                model = course.cover,
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(corner = CornerSize(10)))
            )
            HorizontalSpacerSmall()
            Column {
                Text(
                    text = course.summary,
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = course.workload,
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
