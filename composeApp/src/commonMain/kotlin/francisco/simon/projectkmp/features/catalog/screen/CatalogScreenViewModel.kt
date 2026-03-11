package francisco.simon.projectkmp.features.catalog.screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.projectkmp.core.domain.usecase.GetCoursesUseCase
import francisco.simon.projectkmp.features.catalog.domain.usecase.GetCatalogCoursesUseCase
import francisco.simon.projectkmp.features.catalog.domain.usecase.LoadNextPageUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class CatalogScreenViewModel(
    getCatalogCoursesUseCase: GetCatalogCoursesUseCase,
    private val loadNextPageUseCase: LoadNextPageUseCase,
    private val getCoursesUseCase: GetCoursesUseCase
) : ViewModel() {

    private val recommendationFlow = getCatalogCoursesUseCase()
    private val loadNextDataEvents = MutableSharedFlow<Unit>()
    val showLoading = mutableStateOf<Boolean>(false)

    val state = getCatalogCoursesUseCase()
        .filter { it.isNotEmpty() }
        .map { coursesList ->
            val sections = coursesList.map { catalog ->
                viewModelScope.async {
                    val courses = getCoursesUseCase(catalog.courses)
                        .getOrThrow()
                    CoursesUI(
                        id = catalog.id,
                        title = catalog.title,
                        courses = courses
                    )
                }
            }.awaitAll()
            CatalogScreenState.Success(
                courses = sections,
                nextDataLoading = false
            ) as CatalogScreenState
        }.onStart {
            emit(CatalogScreenState.Loading)
        }.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            CatalogScreenState.Loading
        )

    fun loadNextCourses() {
        viewModelScope.launch {
            loadNextPageUseCase()
        }
    }
}

