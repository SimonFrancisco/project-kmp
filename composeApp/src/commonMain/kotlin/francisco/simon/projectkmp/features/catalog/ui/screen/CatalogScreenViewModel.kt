package francisco.simon.projectkmp.features.catalog.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.projectkmp.core.domain.usecase.GetCoursesUseCase
import francisco.simon.projectkmp.features.catalog.domain.usecase.GetCatalogCoursesUseCase
import francisco.simon.projectkmp.features.catalog.domain.usecase.LoadNextPageUseCase
import francisco.simon.projectkmp.features.catalog.domain.usecase.RefreshUseCase
import francisco.simon.projectkmp.features.catalog.ui.model.CoursesUI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.error_unknown

@OptIn(ExperimentalCoroutinesApi::class)
class CatalogScreenViewModel(
    private val getCatalogCoursesUseCase: GetCatalogCoursesUseCase,
    private val loadNextPageUseCase: LoadNextPageUseCase,
    private val getCoursesUseCase: GetCoursesUseCase,
    private val refreshUseCase: RefreshUseCase
) : ViewModel() {

    private val retryTrigger = MutableSharedFlow<Unit>()
    private val _showPaginationLoading = MutableStateFlow(false)
    val showPaginationLoading = _showPaginationLoading.asStateFlow()

    private val _showRefreshLoading = MutableStateFlow(false)
    val showRefreshLoading = _showRefreshLoading.asStateFlow()

    private val _state = MutableStateFlow<CatalogScreenState>(CatalogScreenState.Loading)
    val state: StateFlow<CatalogScreenState> = _state.asStateFlow()

    private val _effects: MutableSharedFlow<CatalogScreenEffects> = MutableSharedFlow()
    val effects = _effects.asSharedFlow()

    init {
        loadCatalogs()
    }

    private fun loadCatalogs() {
        viewModelScope.launch {
            retryTrigger
                .onStart { emit(Unit) }
                .flatMapLatest {
                    getCatalogCoursesUseCase()
                        .mapLatest { result ->
                            result.fold(
                                onSuccess = { catalogs ->
                                    val sections =
                                        catalogs.map { catalog ->
                                            async {
                                                getCoursesUseCase(catalog.courses).getOrNull()
                                                    ?.let { courses ->
                                                        CoursesUI(
                                                            id = catalog.id,
                                                            title = catalog.title,
                                                            courses = courses
                                                        )
                                                    }
                                            }
                                        }.awaitAll().filterNotNull()
                                    _showPaginationLoading.update { false }
                                    _showRefreshLoading.update { false }
                                    CatalogScreenState.Success(sections)
                                },
                                onFailure = {
                                    _showPaginationLoading.update { false }
                                    _showRefreshLoading.update { false }
                                    CatalogScreenState.Error(Res.string.error_unknown)
                                }
                            )
                        }
                }.collect { value ->
                    _state.update {
                        value
                    }
                }
        }
    }

    private fun retry() {
        viewModelScope.launch {
            retryTrigger.emit(Unit)
            _state.update {
                CatalogScreenState.Loading
            }
        }
    }

    private fun loadNextCourses() {
        viewModelScope.launch {
            _showPaginationLoading.value = true
            loadNextPageUseCase()
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _showRefreshLoading.update { true }
            refreshUseCase()
        }
    }

    fun onHandleIntents(intent: CatalogScreenIntent) {
        when (intent) {
            is CatalogScreenIntent.CourseClicked -> {
                _effects.tryEmit(CatalogScreenEffects.NavigateToCourseDetail(intent.courseId))
            }
            CatalogScreenIntent.LoadMoreCourses -> loadNextCourses()
            CatalogScreenIntent.Refresh -> refresh()
            CatalogScreenIntent.TryAgain -> retry()
        }
    }
}
