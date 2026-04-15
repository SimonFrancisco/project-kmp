package francisco.simon.projectkmp.features.common.screen.info

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import francisco.simon.projectkmp.core.domain.entity.Course
import francisco.simon.projectkmp.ui.components.CustomAsyncImage
import francisco.simon.projectkmp.ui.theme.paddingMedium
import francisco.simon.projectkmp.ui.theme.paddingSmall
import francisco.simon.projectkmp.ui.utils.HorizontalSpacerSmall
import francisco.simon.projectkmp.ui.utils.VerticalSpacerMedium
import francisco.simon.projectkmp.ui.utils.VerticalSpacerSmall
import francisco.simon.projectkmp.ui.utils.htmlToAnnotatedString
import francisco.simon.projectkmp.ui.utils.toHoursFromSeconds
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.course_audience
import projectkmp.composeapp.generated.resources.course_requirements
import projectkmp.composeapp.generated.resources.course_time
import projectkmp.composeapp.generated.resources.ic_check
import projectkmp.composeapp.generated.resources.ic_person
import projectkmp.composeapp.generated.resources.ic_requirements
import projectkmp.composeapp.generated.resources.ic_time
import projectkmp.composeapp.generated.resources.topics_to_be_learned

@Suppress("detekt:all")
@Composable
internal fun CourseInfo(
    modifier: Modifier = Modifier,
    course: Course
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = paddingSmall)
    ) {
        item {
            Text(
                text = course.summary,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = paddingSmall)
            )
            VerticalSpacerSmall()
        }

        item {
            if (course.introVideo != null) {
                CustomAsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = course.introVideo.thumbnail,
                    contentScale = ContentScale.FillWidth
                )
            }
            VerticalSpacerSmall()
        }

        item {
            Text(
                text = stringResource(Res.string.topics_to_be_learned),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = paddingSmall)
            )
            VerticalSpacerMedium()
        }

        items(course.acquiredSkills) { skill ->
            Row(modifier = Modifier.padding(horizontal = paddingSmall)) {
                Icon(
                    painter = painterResource(Res.drawable.ic_check),
                    contentDescription = null,
                    tint = Color.Green
                )
                HorizontalSpacerSmall()
                Text(
                    text = skill,
                    style = MaterialTheme.typography.bodyMedium,
                )
                VerticalSpacerSmall()
            }
        }
        item {
            VerticalSpacerSmall()
            HorizontalDivider()
            VerticalSpacerSmall()
        }

        item {
            TitleWithIcon(
                iconRes = Res.drawable.ic_requirements,
                titleRes = Res.string.course_requirements
            )
            VerticalSpacerMedium()
            TextWithHtml(course.requirements)
            VerticalSpacerMedium()
        }

        item {
            TitleWithIcon(
                iconRes = Res.drawable.ic_person,
                titleRes = Res.string.course_audience
            )
            VerticalSpacerMedium()
            TextWithHtml(course.audience)
            VerticalSpacerMedium()
        }
        item {
            TitleWithIcon(
                iconRes = Res.drawable.ic_time,
                titleRes = Res.string.course_time
            )
            VerticalSpacerMedium()
            Text(
                text = course.timeToComplete.toHoursFromSeconds(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = paddingMedium)
            )
        }
    }
}

@Composable
private fun TextWithHtml(text: String) {
    Text(
        text = text.htmlToAnnotatedString(),
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(horizontal = paddingSmall)
    )
}

@Composable
private fun TitleWithIcon(
    iconRes: DrawableResource,
    titleRes: StringResource
) {
    Row(
        modifier = Modifier.padding(horizontal = paddingSmall)
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null
        )
        HorizontalSpacerSmall()
        Text(
            text = stringResource(titleRes),
            style = MaterialTheme.typography.titleMedium,
        )
    }
}
