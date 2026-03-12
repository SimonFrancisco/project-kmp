package francisco.simon.projectkmp.features.catalog.ui.screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.projectkmp.core.domain.usecase.GetCoursesUseCase
import francisco.simon.projectkmp.features.catalog.domain.usecase.GetCatalogCoursesUseCase
import francisco.simon.projectkmp.features.catalog.domain.usecase.LoadNextPageUseCase
import francisco.simon.projectkmp.features.catalog.ui.model.CoursesUI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.error_unknown

@OptIn(ExperimentalCoroutinesApi::class)
internal class CatalogScreenViewModel(
    private val getCatalogCoursesUseCase: GetCatalogCoursesUseCase,
    private val loadNextPageUseCase: LoadNextPageUseCase,
    private val getCoursesUseCase: GetCoursesUseCase
) : ViewModel() {

    private val retryTrigger = MutableSharedFlow<Unit>()
    val showLoading = mutableStateOf<Boolean>(false)

    private val _state = MutableStateFlow<CatalogScreenState>(CatalogScreenState.Loading)
    val state: StateFlow<CatalogScreenState> = _state

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
                                    showLoading.value = false
                                    CatalogScreenState.Success(sections) as CatalogScreenState
                                },
                                onFailure = {
                                    showLoading.value = false
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


    fun retry() {
        viewModelScope.launch {
            retryTrigger.emit(Unit)
            _state.update {
                CatalogScreenState.Loading
            }
        }
    }

    fun loadNextCourses() {
        viewModelScope.launch {
            showLoading.value = true
            loadNextPageUseCase()
        }
    }

}
