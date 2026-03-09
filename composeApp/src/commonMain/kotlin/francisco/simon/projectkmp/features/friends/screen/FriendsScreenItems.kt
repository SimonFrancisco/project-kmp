package francisco.simon.projectkmp.features.friends.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import francisco.simon.projectkmp.features.friends.domain.entity.Friend
import francisco.simon.projectkmp.ui.components.CustomAsyncImage
import francisco.simon.projectkmp.ui.theme.ProjectKmp
import francisco.simon.projectkmp.ui.theme.paddingSmall
import francisco.simon.projectkmp.ui.utils.HorizontalSpacerSmall

@Composable
internal fun FriendCard(
    friend: Friend,
    onCardClicked: (friend: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onCardClicked(friend.id)
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
                model = friend.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
            )
            HorizontalSpacerSmall()
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${friend.name} ${friend.surname}",
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun FriendCardPreview() {
    ProjectKmp {
        FriendCard(
            Friend(
                id = 0,
                name = "Simon",
                surname = "Francisco",
                imageUrl = "https://randomuser.me/api/portraits/men/20.jpg",
            ), onCardClicked = {}
        )
    }

}
