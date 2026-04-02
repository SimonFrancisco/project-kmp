package francisco.simon.projectkmp.ui.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun LoadMorePages(
    listState: LazyListState,
    buffer: Int = 2,
    nextDataIsLoading: Boolean,
    loadMoreData: () -> Unit
) {
    val paginationLoadMore = paginationLoadMore(
        listState = listState,
        buffer = buffer,
        isLoading = nextDataIsLoading
    )
    LaunchedEffect(listState) {
        snapshotFlow { paginationLoadMore.value }
            .distinctUntilChanged()
            .filter { it }
            .collect {
                loadMoreData()
            }
    }
}

@Composable
private fun paginationLoadMore(
    listState: LazyListState,
    buffer: Int,
    isLoading: Boolean
): State<Boolean> {
    val isAtBottom = remember {
        derivedStateOf {
            val lastVisibleItem =
                listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val totalItems =
                listState.layoutInfo.totalItemsCount
            lastVisibleItem > 0 && lastVisibleItem >= (totalItems - buffer) && !isLoading
        }
    }
    return isAtBottom
}

