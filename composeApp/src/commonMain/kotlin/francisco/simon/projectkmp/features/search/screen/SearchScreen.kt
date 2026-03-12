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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import francisco.simon.projectkmp.features.search.domain.entity.SearchCourse
import francisco.simon.projectkmp.ui.components.FullScreenLoading
import francisco.simon.projectkmp.ui.components.RetryCall
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onOpenDetailScreen: (courseId: Int) -> Unit
) {
    val viewModel: SearchScreenViewModel = koinViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()
    val query = viewModel.query.collectAsStateWithLifecycle()

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
                onValueChange = viewModel::onQuery,
                onClear = viewModel::onClear
            )
        }
    ) { innerPadding ->
        SearchScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding()),
            state = state.value,
            nextDataIsLoading = viewModel.showLoading.value,
            onTryAgain = viewModel::retry,
            onCourseClick = onOpenDetailScreen,
            loadNextCourses = viewModel::loadNextCourses
        )
    }
}

@Composable
private fun SearchScreenContent(
    modifier: Modifier = Modifier,
    state: SearchScreenState,
    nextDataIsLoading: Boolean,
    onTryAgain: () -> Unit,
    onCourseClick: (Int) -> Unit,
    loadNextCourses: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        when (state) {
            is SearchScreenState.Error -> {
                RetryCall(
                    errorRes = state.errorMessageRes,
                    onClick = onTryAgain
                )
            }

            is SearchScreenState.Idle -> {}

            is SearchScreenState.Loading -> {
                FullScreenLoading()
            }

            is SearchScreenState.Success -> {
                SearchCourseList(
                    onGoToDetailedInfo = onCourseClick,
                    courses = state.courses,
                    nextDataIsLoading = nextDataIsLoading,
                    loadNextCourses = loadNextCourses
                )
            }
        }
    }
}

@Composable
private fun SearchCourseList(
    onGoToDetailedInfo: (Int) -> Unit,
    courses: List<SearchCourse>,
    nextDataIsLoading: Boolean,
    loadNextCourses: () -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        itemsIndexed(courses, key = { _, it -> it.id }) { index, searchCourse ->
            SearchCourseCard(
                course = searchCourse,
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
                        .wrapContentHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SearchScreenPreview() {
    SearchScreen {}
}
