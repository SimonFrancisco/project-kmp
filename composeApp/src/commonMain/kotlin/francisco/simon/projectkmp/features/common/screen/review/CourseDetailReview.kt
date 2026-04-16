package francisco.simon.projectkmp.features.common.screen.review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import francisco.simon.projectkmp.ui.components.CustomAsyncImage
import francisco.simon.projectkmp.ui.theme.ProjectKmp
import francisco.simon.projectkmp.ui.theme.paddingSmall
import francisco.simon.projectkmp.ui.theme.spaceSmall
import francisco.simon.projectkmp.ui.theme.spaceXXSmall
import francisco.simon.projectkmp.ui.utils.HorizontalSpacerSmall
import francisco.simon.projectkmp.ui.utils.VerticalSpacerMedium
import francisco.simon.projectkmp.ui.utils.VerticalSpacerSmall
import org.jetbrains.compose.resources.painterResource
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.ic_start_rate

private const val TOTAL_NUMBER_OF_STARS = 5
private val avatarSize = 40.dp

@Composable
internal fun CourseDetailReview(
    modifier: Modifier = Modifier,
    reviews: List<ReviewUI>
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(paddingSmall),
        verticalArrangement = Arrangement.spacedBy(spaceSmall)
    ) {
        items(reviews, key = { it.id }) { review ->
            ReviewItem(reviewUI = review)
            VerticalSpacerMedium()
            HorizontalDivider()
        }
    }
}

@Composable
private fun ReviewItem(
    modifier: Modifier = Modifier,
    reviewUI: ReviewUI
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomAsyncImage(
                model = reviewUI.userAvatarUrl,
                contentDescription = reviewUI.userFullName,
                modifier = Modifier
                    .size(avatarSize)
                    .clip(CircleShape)
            )
            HorizontalSpacerSmall()
            Text(
                text = reviewUI.userFullName,
                style = MaterialTheme.typography.titleSmall
            )
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(spaceXXSmall, Alignment.End)
            ) {
                repeat(TOTAL_NUMBER_OF_STARS) { index ->
                    Icon(
                        painter = painterResource(Res.drawable.ic_start_rate),
                        contentDescription = null,
                        tint = if (index < reviewUI.score) Color.Green else Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
        VerticalSpacerSmall()
        val paddingStart = avatarSize + spaceSmall
        Text(
            text = reviewUI.text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = paddingStart)
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ReviewItemPreview() {
    ProjectKmp {
        ReviewItem(
            reviewUI = ReviewUI(
                id = 0,
                userFullName = "Simon Francisco",
                userAvatarUrl = "",
                score = 5,
                text = "The course is very good, keep it up!"
            )

        )
    }
}
