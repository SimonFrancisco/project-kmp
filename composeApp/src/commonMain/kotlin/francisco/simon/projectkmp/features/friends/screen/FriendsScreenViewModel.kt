package francisco.simon.projectkmp.features.friends.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import francisco.simon.projectkmp.features.friends.domain.usecase.GetFriendsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.error_unknown
import kotlin.reflect.KClass

internal class FriendsScreenViewModel(
    private val getFriendsUseCase: GetFriendsUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<FriendsScreenState> =
        MutableStateFlow(FriendsScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        fetchFriends()
    }

    fun fetchFriends() {
        viewModelScope.launch {
            getFriendsUseCase()
                .onSuccess { listFriends ->
                    // Check if empty later
                    _state.update { FriendsScreenState.Success(listFriends) }
                }.onFailure {
                    _state.update { FriendsScreenState.Error(Res.string.error_unknown) }
                }
        }
    }

}

internal class FriendsScreenViewModelFactory(
    private val getFriendsUseCase: GetFriendsUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: KClass<T>,
        extras: CreationExtras
    ): T {
        if (modelClass == FriendsScreenViewModel::class) {
            return FriendsScreenViewModel(getFriendsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
