package francisco.simon.projectkmp.ui.utils

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import francisco.simon.projectkmp.ui.theme.spaceExtraSmall
import francisco.simon.projectkmp.ui.theme.spaceMedium
import francisco.simon.projectkmp.ui.theme.spaceSmall
import francisco.simon.projectkmp.ui.theme.spaceXMedium

@Composable
internal fun VerticalSpacerXMedium() {
    Spacer(Modifier.height(spaceXMedium))
}

@Composable
internal fun VerticalSpacerSmall() {
    Spacer(Modifier.height(spaceSmall))
}

@Composable
internal fun VerticalSpacerMedium() {
    Spacer(Modifier.height(spaceMedium))
}

@Composable
internal fun HorizontalSpacerExtraSmall() {
    Spacer(Modifier.width(spaceExtraSmall))
}

@Composable
internal fun HorizontalSpacerSmall() {
    Spacer(Modifier.width(spaceSmall))
}

@Composable
internal fun HorizontalSpacerMedium() {
    Spacer(Modifier.width(spaceMedium))
}
