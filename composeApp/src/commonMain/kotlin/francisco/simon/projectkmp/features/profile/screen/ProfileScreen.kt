package francisco.simon.projectkmp.features.profile.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import francisco.simon.projectkmp.features.profile.domain.entity.User
import francisco.simon.projectkmp.ui.components.FullScreenLoading
import francisco.simon.projectkmp.ui.components.RetryCall
import francisco.simon.projectkmp.ui.utils.EffectsConsumer
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.logout_button_label

@Composable
fun ProfileScreen(
    onOpenAuthScreen: () -> Unit,
    viewModel: ProfileScreenViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val refreshLoading = viewModel.showRefreshLoading.collectAsStateWithLifecycle()
    Scaffold { innerPaddings ->
        ProfileScreenContent(
            modifier = Modifier.padding(innerPaddings),
            state = state.value,
            refreshLoading = refreshLoading.value,
            onIntent = viewModel::onHandleIntent
        )
    }
    EffectsConsumer(viewModel.effects) { effect ->
        when (effect) {
            is ProfileScreenEffect.NavigateToAuthScreen -> {
                onOpenAuthScreen()
            }
        }
    }
}

@Composable
private fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    state: ProfileScreenState,
    refreshLoading: Boolean,
    onIntent: (ProfileScreenIntent) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        when (state) {
            is ProfileScreenState.Error -> {
                RetryCall(
                    errorRes = state.errorMessageRes,
                    onClick = {
                        onIntent(ProfileScreenIntent.TryAgain)
                    }
                )
            }
            is ProfileScreenState.Loading -> {
                FullScreenLoading()
            }
            is ProfileScreenState.Success -> {
                PullToRefreshBox(
                    isRefreshing = refreshLoading,
                    onRefresh = { onIntent(ProfileScreenIntent.Refresh) }
                ) {
                    UserProfileItem(
                        user = state.user,
                    )
                }
            }
        }
        Button(
            onClick = { onIntent(ProfileScreenIntent.Logout) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text(stringResource(Res.string.logout_button_label))
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ProfileScreenPreview() {
    ProfileScreenContent(
        state = ProfileScreenState.Success(
            User(
                id = 1,
                fullName = "Simon Francisco",
                image = "https://stepik.org/users/1052079324/4355117f4b25480787b8622fea76eb057ff250c1/avatar.svg",
                bio = "Android developer",
                registrationDate = "2025-05-14T15:43:14Z"
            )
        ),
        refreshLoading = false,
        onIntent = {},
    )
}
