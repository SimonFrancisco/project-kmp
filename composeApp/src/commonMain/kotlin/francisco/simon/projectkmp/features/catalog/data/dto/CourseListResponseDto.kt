package francisco.simon.projectkmp.features.catalog.data.dto

import francisco.simon.projectkmp.features.catalog.domain.entity.Courses
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseListResponseDto(
    @SerialName("meta")
    val meta: MetaDto,
    @SerialName("course-lists")
    val coursesList: List<CoursesListDto>
)

fun CourseListResponseDto.toDomain(): List<Courses> {
    return coursesList.map {
        it.toDomain()
    }
}
