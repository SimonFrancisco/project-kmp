package francisco.simon.projectkmp.features.profile.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import francisco.simon.projectkmp.features.profile.domain.entity.User
import francisco.simon.projectkmp.ui.components.CustomAsyncImage
import francisco.simon.projectkmp.ui.utils.VerticalSpacerMedium
import francisco.simon.projectkmp.ui.utils.VerticalSpacerSmall
import francisco.simon.projectkmp.ui.utils.formatTime

@Suppress("LongMethod")
@Composable
fun UserProfileItem(
    user: User,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomAsyncImage(
                    model = user.image,
                    contentDescription = user.fullName,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally),
                )

                VerticalSpacerMedium()
                Text(
                    text = user.fullName,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            VerticalSpacerSmall()
            Text(
                text = "Registered: ${user.registrationDate.formatTime()}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            if (user.bio.isNotBlank()) {
                VerticalSpacerMedium()
                Text(
                    text = "About",
                    style = MaterialTheme.typography.titleMedium
                )
                VerticalSpacerSmall()
                Text(
                    text = user.bio,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
