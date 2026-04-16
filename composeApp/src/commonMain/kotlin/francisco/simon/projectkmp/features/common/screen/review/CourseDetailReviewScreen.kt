package francisco.simon.projectkmp.features.common.screen.review

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
fun CourseDetailReviewScreen(
    courseId: Int,
    viewModel: CourseDetailReviewScreenViewModel = koinViewModel(
        parameters = { parametersOf(courseId) }
    )
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    CourseDetailReviewScreenContent(
        state = state.value,
        onTryAgain = viewModel::retry
    )
}

@Composable
private fun CourseDetailReviewScreenContent(
    modifier: Modifier = Modifier,
    state: CourseDetailReviewScreenSate,
    onTryAgain: () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        when (state) {
            is CourseDetailReviewScreenSate.Error -> {
                RetryCall(
                    modifier = Modifier.fillMaxSize(),
                    errorRes = state.errorMessageRes,
                    onClick = onTryAgain
                )
            }

            is CourseDetailReviewScreenSate.Loading -> {
                FullScreenLoading()
            }

            is CourseDetailReviewScreenSate.Success -> {
                CourseDetailReview(reviews = state.reviews)
            }
        }
    }
}
