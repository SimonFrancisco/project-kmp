package francisco.simon.projectkmp.features.common.screen.info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import francisco.simon.projectkmp.ui.components.FullScreenLoading
import francisco.simon.projectkmp.ui.components.RetryCall
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CourseDetailScreen(
    courseId: Int,
    viewModel: CourseDetailInfoScreenViewModel = koinViewModel(
        parameters = { parametersOf(courseId) }
    )
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    CourseDetailScreenContent(
        state = state.value,
        onTryAgain = viewModel::retry
    )
}

@Composable
private fun CourseDetailScreenContent(
    modifier: Modifier = Modifier,
    state: CourseDetailInfoScreenSate,
    onTryAgain: () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        when (state) {
            is CourseDetailInfoScreenSate.Error -> {
                RetryCall(
                    modifier = Modifier.fillMaxSize(),
                    errorRes = state.errorMessageRes,
                    onClick = onTryAgain
                )
            }

            is CourseDetailInfoScreenSate.Loading -> {
                FullScreenLoading()
            }

            is CourseDetailInfoScreenSate.Success -> {
                CourseInfo(course = state.course)
            }
        }
    }
}
