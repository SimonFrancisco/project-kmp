package francisco.simon.projectkmp.features.friends.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import francisco.simon.projectkmp.di.FakeServiceLocator
import francisco.simon.projectkmp.features.friends.domain.entity.Friend
import francisco.simon.projectkmp.features.friends.domain.usecase.GetFriendsUseCase
import francisco.simon.projectkmp.ui.components.FullScreenLoading
import francisco.simon.projectkmp.ui.components.RetryCall

@Composable
fun FriendsScreen() {
    val viewModel: FriendsScreenViewModel = viewModel(
        factory = FriendsScreenViewModelFactory(
            GetFriendsUseCase(FakeServiceLocator.friendsRepository)
        )
    )
    val state = viewModel.state.collectAsStateWithLifecycle()

    Scaffold { innerPaddings ->
        FriendsScreenScreenContent(
            modifier = Modifier.padding(innerPaddings),
            state = state.value,
            onGoToDetailedInfo = {},
            onTryAgain = {
                viewModel.fetchFriends()
            }
        )
    }
}


@Composable
private fun FriendsScreenScreenContent(
    modifier: Modifier = Modifier,
    state: FriendsScreenState,
    onGoToDetailedInfo: (Int) -> Unit,
    onTryAgain: () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        when (state) {
            is FriendsScreenState.Error -> {
                RetryCall(
                    errorRes = state.errorMessageRes,
                    onClick = onTryAgain
                )
            }
            is FriendsScreenState.Loading -> {
                FullScreenLoading()
            }
            is FriendsScreenState.Success -> {
                FriendsList(
                    onGoToDetailedInfo = onGoToDetailedInfo,
                    friends = state.friends
                )
            }
        }
    }
}

@Composable
private fun FriendsList(
    onGoToDetailedInfo: (Int) -> Unit,
    friends: List<Friend>
) {
    LazyColumn {
        items(friends, key = { it.id }) { friend ->
            FriendCard(
                friend = friend,
                onCardClicked = onGoToDetailedInfo
            )
        }
    }
}
