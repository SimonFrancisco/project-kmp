package francisco.simon.projectkmp.features.profile.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.projectkmp.features.profile.domain.usecase.GetUserUseCase
import francisco.simon.projectkmp.features.profile.domain.usecase.LogoutUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.error_unknown

class ProfileScreenViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<ProfileScreenState> =
        MutableStateFlow(ProfileScreenState.Loading)
    val state = _state.asStateFlow()

    private val _effects: MutableSharedFlow<ProfileScreenEffect> = MutableSharedFlow()
    val effects = _effects.asSharedFlow()

    private val _showRefreshLoading = MutableStateFlow(false)
    val showRefreshLoading = _showRefreshLoading.asStateFlow()

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            getUserUseCase().fold(
                onSuccess = { user ->
                    _state.update {
                        ProfileScreenState.Success(user)
                    }
                    _showRefreshLoading.update { false }
                },
                onFailure = {
                    _state.update {
                        ProfileScreenState.Error(Res.string.error_unknown)
                    }
                    _showRefreshLoading.update { false }
                }
            )
        }
    }

    private fun retry() {
        viewModelScope.launch {
            _state.update {
                ProfileScreenState.Loading
            }
            getUser()
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _showRefreshLoading.update { true }
            getUser()
        }
    }

    private fun onLogout() {
        viewModelScope.launch {
            logoutUseCase()
            _effects.emit(ProfileScreenEffect.NavigateToAuthScreen)
        }
    }

    fun onHandleIntent(intent: ProfileScreenIntent) {
        when (intent) {
            is ProfileScreenIntent.Logout -> onLogout()
            ProfileScreenIntent.TryAgain -> retry()
            ProfileScreenIntent.Refresh -> refresh()
        }
    }
}
