package francisco.simon.projectkmp.features.common.screen.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.projectkmp.core.domain.usecase.GetCourseReviewsUseCase
import francisco.simon.projectkmp.core.domain.usecase.GetUserCoreUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.error_unknown

class CourseDetailReviewScreenViewModel(
    private val courseId: Int,
    private val getCourseReviewsUseCase: GetCourseReviewsUseCase,
    private val getUserCoreUseCase: GetUserCoreUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<CourseDetailReviewScreenSate> = MutableStateFlow(
        CourseDetailReviewScreenSate.Loading
    )
    val state = _state.asStateFlow()

    init {
        loadReviews()
    }

    private fun loadReviews() {
        viewModelScope.launch {
            _state.update {
                CourseDetailReviewScreenSate.Loading
            }
            getCourseReviewsUseCase(courseId)
                .onSuccess { reviewList ->
                    val reviewUI = reviewList.map { review ->
                        async {
                            getUserCoreUseCase(review.userId).map { userCore ->
                                ReviewUI(
                                    id = review.id,
                                    userFullName = userCore.fullName,
                                    userAvatarUrl = userCore.image,
                                    score = review.score,
                                    text = review.text
                                )
                            }
                        }
                    }.awaitAll()
                    val hasError = reviewUI.any { it.isFailure }
                    if (hasError && reviewUI.all { it.isFailure }) {
                        _state.update {
                            CourseDetailReviewScreenSate.Error(Res.string.error_unknown)
                        }
                    } else {
                        _state.update {
                            CourseDetailReviewScreenSate.Success(
                                reviewUI.mapNotNull { it.getOrNull() }
                            )
                        }
                    }
                }.onFailure {
                    _state.update {
                        CourseDetailReviewScreenSate.Error(Res.string.error_unknown)
                    }
                }
        }
    }

    fun retry() {
        loadReviews()
    }
}
