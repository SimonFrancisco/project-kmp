package francisco.simon.projectkmp.features.courses.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import francisco.simon.projectkmp.core.domain.entity.Course
import francisco.simon.projectkmp.ui.components.CourseCard
import francisco.simon.projectkmp.ui.components.FullScreenLoading
import francisco.simon.projectkmp.ui.components.RetryCall
import francisco.simon.projectkmp.ui.utils.EffectsConsumer
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCourseScreen(
    onOpenDetailScreen: (courseId: Int) -> Unit,
    viewModel: UserCourseScreenViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        UserCourseScreenContent(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding()),
            state = state.value,
            onIntent = viewModel::onHandleIntent
        )
    }
    EffectsConsumer(viewModel.effects) { effect ->
        when (effect) {
            is UserCourseScreenEffect.NavigateToCourseDetail -> onOpenDetailScreen(effect.courseId)
        }
    }
}

@Composable
private fun UserCourseScreenContent(
    modifier: Modifier = Modifier,
    state: UserCourseScreenState,
    onIntent: (UserCourseScreenIntent) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        when (state) {
            is UserCourseScreenState.Error -> {
                RetryCall(
                    errorRes = state.errorMessageRes,
                    onClick = {
                        onIntent(UserCourseScreenIntent.TryAgain)
                    }
                )
            }
            is UserCourseScreenState.Loading -> {
                FullScreenLoading()
            }
            is UserCourseScreenState.Success -> {
                UserCourseList(
                    onGoToDetailedInfo = {
                        onIntent(UserCourseScreenIntent.CourseClicked(it))
                    },
                    courses = state.courses,
                )
            }
        }
    }
}

@Composable
private fun UserCourseList(
    onGoToDetailedInfo: (Int) -> Unit,
    courses: List<Course>,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(courses, key = { course -> course.id }) { course ->
            CourseCard(
                modifier = Modifier.fillMaxWidth(),
                course = course,
                onCardClicked = onGoToDetailedInfo
            )
        }
    }
}
