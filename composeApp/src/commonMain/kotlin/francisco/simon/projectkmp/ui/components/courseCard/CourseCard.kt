package francisco.simon.projectkmp.ui.components.courseCard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import francisco.simon.projectkmp.core.domain.entity.Course
import francisco.simon.projectkmp.ui.components.CustomAsyncImage
import francisco.simon.projectkmp.ui.theme.ProjectKmp
import francisco.simon.projectkmp.ui.theme.paddingSmall
import francisco.simon.projectkmp.ui.utils.CORNER_SIZE_PERCENTAGE10
import francisco.simon.projectkmp.ui.utils.HorizontalSpacerExtraSmall
import francisco.simon.projectkmp.ui.utils.HorizontalSpacerSmall
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.course_certificate_label
import projectkmp.composeapp.generated.resources.free_course_label
import projectkmp.composeapp.generated.resources.ic_cerficate
import projectkmp.composeapp.generated.resources.ic_groups

@Suppress("LongMethod")
@Composable
fun CourseCard(
    course: Course,
    onCardClicked: (courseId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(CourseCardDefaults.Height)
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
                .padding(paddingSmall)
        ) {
            Column(
                modifier = Modifier.fillMaxHeight()
            ) {
                CustomAsyncImage(
                    model = course.cover,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(corner = CornerSize(CORNER_SIZE_PERCENTAGE10)))
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = if (course.isPaid) {
                        course.priceDisplayed ?: ""
                    } else {
                        stringResource(Res.string.free_course_label)
                    },
                    style = MaterialTheme.typography.labelLarge
                )
            }
            HorizontalSpacerSmall()
            Column(
                modifier = Modifier.weight(1f)
                    .fillMaxHeight()
            ) {
                Text(
                    text = course.title.trim(),
                    style = MaterialTheme.typography.titleSmall
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_groups),
                        contentDescription = null
                    )
                    HorizontalSpacerExtraSmall()
                    Text(
                        text = "${course.participants}",
                        style = MaterialTheme.typography.labelMedium
                    )
                    if (course.hasCertificate) {
                        HorizontalSpacerSmall()
                        Icon(
                            painter = painterResource(Res.drawable.ic_cerficate),
                            contentDescription = null
                        )
                        HorizontalSpacerExtraSmall()
                        Text(
                            text = stringResource(Res.string.course_certificate_label),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = course.summary.trim(),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Suppress("MaxLineLength")
@Composable
@Preview(showBackground = true)
private fun CourseCard() {
    ProjectKmp {
        CourseCard(
            course = Course(
                id = 232639,
                participants = 779,
                title = "Современная Android-разработка: базовый курс (2025)",
                summary = "Научитесь создавать быстрые, стабильные и удобные приложения на современном стеке, рекомендованном Google в 2025 году. Курс подойдёт новичкам и тем, кто хочет перейти на актуальные инструменты и подходы.",
                description = "Это современный курс по Android-разработке с нуля, построенный на технологиях и подходах, которые используются в коммерческой разработке. Вы будете работать с инструментами и библиотеками, рекомендованными Google в 2025 году",
                cover = "https://cdn.stepik.net/media/cache/images/courses/232639/cover_1PhLP3m/83e6c9315bb1ceb87a760a8388e26a76.png",
                isPaid = true,
                priceDisplayed = "8990 ₽",
                workload = "",
                hasCertificate = true
            ),
            onCardClicked = {}
        )
    }
}
