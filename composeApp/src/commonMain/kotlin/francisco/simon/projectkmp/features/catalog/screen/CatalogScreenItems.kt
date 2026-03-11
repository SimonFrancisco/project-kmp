package francisco.simon.projectkmp.features.catalog.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import francisco.simon.projectkmp.ui.components.CustomAsyncImage
import francisco.simon.projectkmp.ui.theme.ProjectKmp
import francisco.simon.projectkmp.ui.theme.paddingSmall
import francisco.simon.projectkmp.ui.utils.HorizontalSpacerSmall

@Composable
internal fun CatalogCard(
    courseUi: CoursesUI,
    onCardClicked: (friend: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        items(courseUi.courses, key = { it.id }) {course->
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        onCardClicked(courseUi.id)
                    },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingSmall),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CustomAsyncImage(
                        model = course.cover,
                        contentDescription = null,
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(corner = CornerSize(10)))
                    )
                    HorizontalSpacerSmall()
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
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
    }

}


@Preview(showBackground = true)
@Composable
private fun CatalogCardPreview() {
    ProjectKmp {
//        CatalogCard(
//            Friend(
//                id = 0,
//                name = "Simon",
//                surname = "Francisco",
//                imageUrl = "https://randomuser.me/api/portraits/men/20.jpg",
//            ), onCardClicked = {}
//        )
    }

}
