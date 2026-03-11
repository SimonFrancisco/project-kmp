package francisco.simon.projectkmp.features.catalog.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import francisco.simon.projectkmp.features.catalog.ui.model.CoursesUI
import francisco.simon.projectkmp.ui.components.FullScreenLoading
import francisco.simon.projectkmp.ui.components.RetryCall
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CatalogScreen(
    onOpenDetailScreen: (Int) -> Unit
) {
    val viewModel: CatalogScreenViewModel = koinViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()

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
        //Napier.d(tag = "CatalogScreenContent", message =  state.toString())
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
                //Napier.d(tag = "CatalogScreenContentSuccess", message =  state.toString())
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
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        itemsIndexed(courses, key = { _, it -> it.id }) { index, courseUi ->
            CatalogCard(
                courseUi = courseUi,
                onCardClicked = onGoToDetailedInfo
            )
            if (index == courses.lastIndex && !nextDataIsLoading) {
                LaunchedEffect(Unit) {
                    loadNextCourses()
                }
            }
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
            }
        }
    }
}
