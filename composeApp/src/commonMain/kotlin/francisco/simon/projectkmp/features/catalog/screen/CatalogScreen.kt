package francisco.simon.projectkmp.features.catalog.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import francisco.simon.projectkmp.ui.components.FullScreenLoading
import francisco.simon.projectkmp.ui.components.RetryCall
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CatalogScreen(
    onOpenDetailScreen: (Int) -> Unit
) {
    val viewModel: CatalogScreenViewModel = koinViewModel()
    val state =
        viewModel.state.collectAsStateWithLifecycle(initialValue = CatalogScreenState.Loading)

    Scaffold { innerPaddings ->
        CatalogScreenContent(
            modifier = Modifier.padding(innerPaddings),
            state = state.value,
            onGoToDetailedInfo = {},
            onTryAgain = {

            },
            nextDataIsLoading = viewModel.showLoading.value,
            loadNextCourses = viewModel::loadNextCourses
        )
    }
}


@Composable
private fun CatalogScreenContent(
    modifier: Modifier = Modifier,
    state: CatalogScreenState,
    onGoToDetailedInfo: (Int) -> Unit,
    onTryAgain: () -> Unit,
    nextDataIsLoading: Boolean,
    loadNextCourses: () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        when (state) {
            is CatalogScreenState.Error -> {
                RetryCall(
                    errorRes = state.errorMessageRes,
                    onClick = onTryAgain
                )
            }

            is CatalogScreenState.Loading -> {
                FullScreenLoading()
            }

            is CatalogScreenState.Success -> {
                CatalogList(
                    onGoToDetailedInfo = onGoToDetailedInfo,
                    courses = state.courses,
                    nextDataIsLoading = nextDataIsLoading,
                    loadNextCourses = loadNextCourses
                )
            }
        }
    }
}

@Composable
private fun CatalogList(
    onGoToDetailedInfo: (Int) -> Unit,
    courses: List<CoursesUI>,
    nextDataIsLoading: Boolean,
    loadNextCourses: () -> Unit
) {
    LazyColumn {
        items(courses, key = { it.id }) { courseUi ->
            CatalogCard(
                courseUi = courseUi,
                onCardClicked = onGoToDetailedInfo
            )
        }

        item {
            if (nextDataIsLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                SideEffect {
                    loadNextCourses()
                }

            }
        }
    }
}
