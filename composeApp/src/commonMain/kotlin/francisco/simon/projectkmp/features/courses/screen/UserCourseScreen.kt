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
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import francisco.simon.projectkmp.core.domain.entity.Course
import francisco.simon.projectkmp.ui.components.FullScreenLoading
import francisco.simon.projectkmp.ui.components.RetryCall
import francisco.simon.projectkmp.ui.components.courseCard.CourseCard
import francisco.simon.projectkmp.ui.utils.EffectsConsumer
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCourseScreen(
    onOpenDetailScreen: (courseId: Int) -> Unit,
    viewModel: UserCourseScreenViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val refreshLoading = viewModel.showRefreshLoading.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        UserCourseScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = state.value,
            refreshLoading = refreshLoading.value,
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
    refreshLoading: Boolean,
    onIntent: (UserCourseScreenIntent) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        when (state) {
            is UserCourseScreenState.Error -> {
                RetryCall(
                    modifier = Modifier.fillMaxSize(),
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
                PullToRefreshBox(
                    isRefreshing = refreshLoading,
                    onRefresh = { onIntent(UserCourseScreenIntent.Refresh) }
                ) {
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
}

@Composable
private fun UserCourseList(
    modifier: Modifier = Modifier,
    onGoToDetailedInfo: (Int) -> Unit,
    courses: List<Course>,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
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
