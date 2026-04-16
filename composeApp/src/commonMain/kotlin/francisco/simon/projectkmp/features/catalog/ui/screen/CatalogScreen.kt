package francisco.simon.projectkmp.features.catalog.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import francisco.simon.projectkmp.ui.components.courseCard.CourseCard
import francisco.simon.projectkmp.ui.components.courseCard.CourseCardDefaults
import francisco.simon.projectkmp.ui.utils.EffectsConsumer
import francisco.simon.projectkmp.ui.utils.LoadMorePages
import francisco.simon.projectkmp.ui.utils.VerticalSpacerSmall
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
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding()),
            state = state.value,
            nextDataIsLoading = nextDataIsLoading.value,
            refreshLoading = refreshLoading.value,
            onIntent = viewModel::onHandleIntents
        )
    }
    EffectsConsumer(viewModel.effects) { effect ->
        when (effect) {
            is CatalogScreenEffect.NavigateToCourseDetail -> {
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
        modifier = modifier
    ) {
        when (state) {
            is CatalogScreenState.Error -> {
                RetryCall(
                    modifier = Modifier.fillMaxSize(),
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
        modifier = modifier.fillMaxSize(),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(courses, key = { coursersUi -> coursersUi.id }) { courseUi ->
            Column {
                HorizontalDivider()
                VerticalSpacerSmall()
                Text(
                    text = courseUi.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                val numberOfRow = if (courseUi.courses.size > 1) 2 else 1
                val spacing = 12.dp
                val gridHeight = CourseCardDefaults.Height * numberOfRow + spacing * (numberOfRow - 1)
                LazyHorizontalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(gridHeight),
                    rows = GridCells.Fixed(numberOfRow),
                    horizontalArrangement = Arrangement.spacedBy(spacing),
                    verticalArrangement = Arrangement.spacedBy(spacing),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(courseUi.courses, key = { it.id }) { course ->
                        CourseCard(
                            modifier = Modifier
                                .width(300.dp),
                            course = course,
                            onCardClicked = {
                                onGoToDetailedInfo(it)
                            }
                        )
                    }
                }
            }
        }
        if (nextDataIsLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
