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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.error_unknown

internal class CatalogScreenViewModel(
    getCatalogCoursesUseCase: GetCatalogCoursesUseCase,
    private val loadNextPageUseCase: LoadNextPageUseCase,
    private val getCoursesUseCase: GetCoursesUseCase
) : ViewModel() {

    private val loadNextDataEvents = MutableSharedFlow<Unit>(replay = 1)
    val showLoading = mutableStateOf<Boolean>(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = getCatalogCoursesUseCase().flatMapLatest { result ->
        flow {
            result.onSuccess { catalogs ->
                val sections = supervisorScope {
                    catalogs.map { catalog ->
                        async {
                            getCoursesUseCase(catalog.courses).getOrNull()?.let { courses ->
                                CoursesUI(
                                    id = catalog.id,
                                    title = catalog.title,
                                    courses = courses
                                )
                            }
                        }
                    }.awaitAll().filterNotNull()
                }
                emit(
                    CatalogScreenState.Success(
                        courses = sections
                    ) as CatalogScreenState
                )
                showLoading.value = false
            }.onFailure {
                emit(CatalogScreenState.Error(Res.string.error_unknown))
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        CatalogScreenState.Loading
    )

    fun loadNextCourses() {
        viewModelScope.launch {
            showLoading.value = true
            loadNextPageUseCase()
            loadNextDataEvents.emit(Unit)
        }
    }
}

