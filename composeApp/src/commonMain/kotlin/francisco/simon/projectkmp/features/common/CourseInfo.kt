package francisco.simon.projectkmp.features.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import francisco.simon.projectkmp.core.domain.entity.Course
import francisco.simon.projectkmp.ui.components.CustomAsyncImage
import francisco.simon.projectkmp.ui.utils.VerticalSpacerXMedium

@Composable
internal fun CourseInfo(
    modifier: Modifier = Modifier,
    course: Course
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier.fillMaxSize()
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomAsyncImage(
            model = course.cover
        )
        VerticalSpacerXMedium()
        Text(
            text = course.summary,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}
