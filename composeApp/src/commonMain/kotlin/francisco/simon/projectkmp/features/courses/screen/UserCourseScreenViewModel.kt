package francisco.simon.projectkmp.features.courses.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.projectkmp.features.courses.domain.usecase.GetUserCoursesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
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
class UserCourseScreenViewModel(
    private val getUserCoursesUseCase: GetUserCoursesUseCase,
) : ViewModel() {

    private val retryTrigger = MutableSharedFlow<Unit>(replay = 1)

    private val _state: MutableStateFlow<UserCourseScreenState> =
        MutableStateFlow(UserCourseScreenState.Loading)
    val state = _state.asStateFlow()

    private val _effects: MutableSharedFlow<UserCourseScreenEffect> = MutableSharedFlow()
    val effects = _effects.asSharedFlow()

    init {
        loadCourses()
    }

    private fun loadCourses() {
        viewModelScope.launch {
            retryTrigger
                .onStart { emit(Unit) }
                .flatMapLatest {
                    getUserCoursesUseCase().mapLatest { result ->
                        result.fold(
                            onSuccess = { courses ->
                                UserCourseScreenState.Success(courses)
                            },
                            onFailure = {
                                UserCourseScreenState.Error(Res.string.error_unknown)
                            }
                        )
                    }
                }.collect { value ->
                    _state.value = value
                }
        }
    }

    private fun retry() {
        retryTrigger.tryEmit(Unit)
        _state.update { UserCourseScreenState.Loading }
    }

    private fun handleCourseClicked(courseId: Int) {
        viewModelScope.launch {
            _effects.emit(UserCourseScreenEffect.NavigateToCourseDetail(courseId))
        }
    }

    fun onHandleIntent(intent: UserCourseScreenIntent) {
        when (intent) {
            is UserCourseScreenIntent.CourseClicked -> handleCourseClicked(intent.courseId)
            UserCourseScreenIntent.TryAgain -> retry()
        }
    }
}
