package francisco.simon.projectkmp.features.catalog.data.dto

import francisco.simon.projectkmp.features.catalog.domain.entity.Courses
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoursesListDto(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("courses")
    val courses: List<Int>
)

internal fun CoursesListDto.toDomain(): Courses {
    return Courses(
        id = id,
        title = title,
        courses = courses
    )
}
