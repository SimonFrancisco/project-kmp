package francisco.simon.projectkmp.features.search.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import francisco.simon.projectkmp.core.domain.entity.Course
import francisco.simon.projectkmp.ui.components.CourseCard
import francisco.simon.projectkmp.ui.components.FullScreenLoading
import francisco.simon.projectkmp.ui.components.RetryCall
import francisco.simon.projectkmp.ui.utils.EffectsConsumer
import francisco.simon.projectkmp.ui.utils.LoadMorePages
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onOpenDetailScreen: (courseId: Int) -> Unit,
    viewModel: SearchScreenViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val query = viewModel.query.collectAsStateWithLifecycle()
    val nextDataIsLoading = viewModel.showLoading.collectAsStateWithLifecycle()

    val scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier
            .nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            SearchAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = query.value,
                onIntent = viewModel::onHandleIntent
            )
        }
    ) { innerPadding ->
        SearchScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding()),
            state = state.value,
            nextDataIsLoading = nextDataIsLoading.value,
            onIntent = viewModel::onHandleIntent
        )
    }

    EffectsConsumer(viewModel.effects) { effect ->
        when (effect) {
            is SearchScreenEffect.NavigateToCourseDetail -> onOpenDetailScreen(effect.courseId)
        }
    }
}

@Composable
private fun SearchScreenContent(
    modifier: Modifier = Modifier,
    state: SearchScreenState,
    nextDataIsLoading: Boolean,
    onIntent: (SearchScreenIntent) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        when (state) {
            is SearchScreenState.Error -> {
                RetryCall(
                    errorRes = state.errorMessageRes,
                    onClick = {
                        onIntent(SearchScreenIntent.TryAgain)
                    }
                )
            }

            is SearchScreenState.Idle -> {}

            is SearchScreenState.Loading -> {
                FullScreenLoading()
            }

            is SearchScreenState.Success -> {
                SearchCourseList(
                    onGoToDetailedInfo = {
                        onIntent(SearchScreenIntent.CourseClicked(it))
                    },
                    courses = state.courses,
                    nextDataIsLoading = nextDataIsLoading,
                    loadNextCourses = {
                        onIntent(SearchScreenIntent.LoadMoreCourses)
                    }
                )
            }
        }
    }
}

@Composable
private fun SearchCourseList(
    onGoToDetailedInfo: (Int) -> Unit,
    courses: List<Course>,
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
        state = listState,
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
