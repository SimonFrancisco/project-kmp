package francisco.simon.projectkmp.core.data.dto

import francisco.simon.projectkmp.core.domain.entity.Course
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseResponseDto(
    @SerialName("courses")
    val courses: List<CourseDto>
)

internal fun CourseResponseDto.toDomain(): List<Course> {
    return courses.map {
        it.toDomain()
    }
}
