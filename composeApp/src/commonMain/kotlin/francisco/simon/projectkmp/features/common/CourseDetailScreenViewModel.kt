package francisco.simon.projectkmp.features.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.projectkmp.core.domain.usecase.GetCourseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.error_unknown

class CourseDetailScreenViewModel(
    private val courseId: Int,
    private val getCourseUseCase: GetCourseUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<CourseDetailScreenSate> = MutableStateFlow(
        CourseDetailScreenSate.Loading
    )
    val state = _state.asStateFlow()

    init {
        loadCourse()
    }

    private fun loadCourse() {
        viewModelScope.launch {
            _state.update {
                CourseDetailScreenSate.Loading
            }
            getCourseUseCase(courseId)
                .onSuccess { course ->
                    _state.update {
                        CourseDetailScreenSate.Success(course)
                    }
                }.onFailure {
                    CourseDetailScreenSate.Error(Res.string.error_unknown)
                }
        }
    }

    fun retry() {
        loadCourse()
    }
}
