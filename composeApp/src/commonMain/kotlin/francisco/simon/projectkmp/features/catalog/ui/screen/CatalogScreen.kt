package francisco.simon.projectkmp.features.catalog.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import francisco.simon.projectkmp.features.catalog.ui.model.CoursesUI
import francisco.simon.projectkmp.ui.components.FullScreenLoading
import francisco.simon.projectkmp.ui.components.RetryCall
import francisco.simon.projectkmp.ui.utils.EffectsConsumer
import francisco.simon.projectkmp.ui.utils.LoadMorePages
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CatalogScreen(
    onOpenDetailScreen: (Int) -> Unit,
    viewModel: CatalogScreenViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val nextDataIsLoading = viewModel.showPaginationLoading.collectAsStateWithLifecycle()
    val refreshLoading = viewModel.showRefreshLoading.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        CatalogScreenContent(
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
            state = state.value,
            nextDataIsLoading = nextDataIsLoading.value,
            refreshLoading = refreshLoading.value,
            onIntent = viewModel::onHandleIntents
        )
    }
    EffectsConsumer(viewModel.effects) { effect ->
        when (effect) {
            is CatalogScreenEffects.NavigateToCourseDetail -> {
                onOpenDetailScreen(effect.courseId)
            }
        }
    }
}

@Composable
private fun CatalogScreenContent(
    modifier: Modifier = Modifier,
    nextDataIsLoading: Boolean,
    refreshLoading: Boolean,
    state: CatalogScreenState,
    onIntent: (CatalogScreenIntent) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        when (state) {
            is CatalogScreenState.Error -> {
                RetryCall(
                    errorRes = state.errorMessageRes,
                    onClick = { onIntent(CatalogScreenIntent.TryAgain) }
                )
            }
            is CatalogScreenState.Loading -> {
                FullScreenLoading()
            }
            is CatalogScreenState.Success -> {
                PullToRefreshBox(
                    isRefreshing = refreshLoading,
                    onRefresh = { onIntent(CatalogScreenIntent.Refresh) },
                ) {
                    CatalogList(
                        onGoToDetailedInfo = { onIntent(CatalogScreenIntent.CourseClicked(it)) },
                        courses = state.courses,
                        nextDataIsLoading = nextDataIsLoading,
                        loadNextCourses = { onIntent(CatalogScreenIntent.LoadMoreCourses) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CatalogList(
    modifier: Modifier = Modifier,
    onGoToDetailedInfo: (Int) -> Unit,
    courses: List<CoursesUI>,
    nextDataIsLoading: Boolean,
    loadNextCourses: () -> Unit
) {
    val listState = rememberLazyListState()
    LoadMorePages(
        listState = listState,
        nextDataIsLoading = nextDataIsLoading,
        loadMoreData = loadNextCourses
    )
    LazyColumn(
        modifier = modifier,
        state = listState,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(courses, key = { coursersUi -> coursersUi.id }) { courseUi ->
            Column {
                Text(
                    text = courseUi.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(12.dp))
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(courseUi.courses, key = { it.id }) { course ->
                        CatalogCard(course, onCardClicked = {
                            onGoToDetailedInfo(it)
                        })
                    }
                }
            }
        }
        item {
            if (nextDataIsLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
