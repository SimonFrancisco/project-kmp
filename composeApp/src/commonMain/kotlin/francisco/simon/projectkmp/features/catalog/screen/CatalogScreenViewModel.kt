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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.error_unknown

internal class CatalogScreenViewModel(
    getCatalogCoursesUseCase: GetCatalogCoursesUseCase,
    private val loadNextPageUseCase: LoadNextPageUseCase,
    private val getCoursesUseCase: GetCoursesUseCase
) : ViewModel() {

    private val recommendationFlow = getCatalogCoursesUseCase()
    private val loadNextDataEvents = MutableSharedFlow<Unit>()
    val showLoading = mutableStateOf<Boolean>(false)

    private val loadNextDataFlow = flow {
        loadNextDataEvents.collect {
            recommendationFlow.onSuccess { listCourses ->
                val courses = listCourses.value.map { coursesList ->
                    viewModelScope.async {
                        getCoursesUseCase(coursesList.courses)
                    }
                }.awaitAll()
                courses.map { result ->
                    result.onSuccess { courses ->
                        showLoading.value = true
                        emit(
                            CatalogScreenState.Success(
                                courses = listCourses.value.map {
                                    CoursesUI(
                                        id = it.id,
                                        title = it.title,
                                        courses = courses
                                    )
                                },
                                nextDataLoading = true
                            )
                        )
                        showLoading.value = false
                    }.onFailure {
                        emit(CatalogScreenState.Error(Res.string.error_unknown))
                    }
                }
            }.onFailure {
                emit(CatalogScreenState.Error(Res.string.error_unknown))
            }
        }
    }


    val state = loadNextDataFlow.onStart {
        emit(CatalogScreenState.Loading)
    }

    fun loadNextCourses() {
        viewModelScope.launch {
            loadNextDataEvents.emit(Unit)
            loadNextPageUseCase()
        }
    }
}

