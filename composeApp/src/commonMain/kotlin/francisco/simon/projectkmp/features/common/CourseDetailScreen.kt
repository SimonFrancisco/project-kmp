package francisco.simon.projectkmp.features.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
    viewModel: CourseDetailScreenViewModel = koinViewModel(
        parameters = { parametersOf(courseId) }
    ),
    onGoBack: () -> Unit
) {

    val state = viewModel.state.collectAsStateWithLifecycle()

    Scaffold { innerPaddings ->
        CourseDetailScreenContent(
            modifier = Modifier.padding(innerPaddings),
            state = state.value,
            onTryAgain = viewModel::retry
        )
    }

}

@Composable
private fun CourseDetailScreenContent(
    modifier: Modifier = Modifier,
    state: CourseDetailScreenSate,
    onTryAgain: () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        when (state) {
            is CourseDetailScreenSate.Error -> {
                RetryCall(
                    errorRes = state.errorMessageRes,
                    onClick = onTryAgain
                )
            }

            is CourseDetailScreenSate.Loading -> {
                FullScreenLoading()
            }

            is CourseDetailScreenSate.Success -> {
                CourseInfo(course = state.course)
            }
        }
    }
}
