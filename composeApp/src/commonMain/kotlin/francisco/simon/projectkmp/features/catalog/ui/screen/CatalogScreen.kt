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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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

    Scaffold { _ ->
        CatalogScreenContent(
            modifier = Modifier.fillMaxSize(),
            state = state.value,
            onGoToDetailedInfo = {},
            onTryAgain = viewModel::retry,
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
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        itemsIndexed(courses, key = { _, it -> it.id }) { index, courseUi ->
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
                    items(courseUi.courses, key = {it.id}) { course ->
                        CatalogCard(course, onCardClicked = {})
                    }
                }
            }

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
