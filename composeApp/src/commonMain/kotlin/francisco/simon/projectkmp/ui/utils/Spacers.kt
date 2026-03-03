package francisco.simon.projectkmp.ui.utils

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import francisco.simon.projectkmp.ui.theme.spaceMedium
import francisco.simon.projectkmp.ui.theme.spaceXMedium

@Composable
internal fun VerticalSpacerXMedium() {
    Spacer(Modifier.height(spaceXMedium))
}

@Composable
internal fun VerticalSpacerMedium() {
    Spacer(Modifier.height(spaceMedium))
}
