package francisco.simon.projectkmp.features.search.screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.projectkmp.features.search.domain.usecase.LoadNextSearchPageUseCase
import francisco.simon.projectkmp.features.search.domain.usecase.SearchCoursesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.error_unknown

@OptIn(
    ExperimentalCoroutinesApi::class,
    FlowPreview::class
)
class SearchScreenViewModel(
    private val searchCoursesUseCase: SearchCoursesUseCase,
    private val loadNextSearchPageUseCase: LoadNextSearchPageUseCase
) : ViewModel() {

    private companion object {
        const val DEBOUNCE = 3000L
    }

    private val retryTrigger = MutableSharedFlow<Unit>(replay = 1)
    val showLoading = mutableStateOf<Boolean>(false)

    private val _state: MutableStateFlow<SearchScreenState> =
        MutableStateFlow(SearchScreenState.Idle)
    val state = _state.asStateFlow()

    private val _query = MutableStateFlow(value = "")
    val query: StateFlow<String> = _query.asStateFlow()

    init {
        searchCourses()
    }

    fun searchCourses() {
        viewModelScope.launch {
            combine(
                _query.debounce(DEBOUNCE),
                retryTrigger.onStart { emit(Unit) }
            ) { query, _ -> query }
                .flatMapLatest { query ->
                    if (query.isBlank()) {
                        flowOf(SearchScreenState.Idle)
                    } else {
                        searchCoursesUseCase(query).mapLatest { result ->
                            result.fold(
                                onSuccess = { courses ->
                                    showLoading.value = false
                                    SearchScreenState.Success(courses)
                                },
                                onFailure = {
                                    showLoading.value = false
                                    SearchScreenState.Error(Res.string.error_unknown)
                                }
                            )
                        }
                    }
                }.collect { value ->
                    _state.value = value
                }
        }
    }

    fun retry() {
        retryTrigger.tryEmit(Unit)
        _state.update { SearchScreenState.Loading }
    }

    fun onClear() {
        _query.update { "" }
        _state.update { SearchScreenState.Idle }
    }

    fun onQuery(query: String) {
        _query.update { query }
        _state.update { SearchScreenState.Loading }

    }

    fun loadNextCourses() {
        viewModelScope.launch {
            showLoading.value = true
            loadNextSearchPageUseCase()
        }
    }
}
