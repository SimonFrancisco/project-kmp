package francisco.simon.projectkmp.features.search.data.dto

import francisco.simon.projectkmp.features.search.domain.entity.SearchCourse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchCourseDto(
    @SerialName("course")
    val id: Int,
    @SerialName("course_title")
    val title: String,
    @SerialName("course_cover")
    val cover: String
)

internal fun SearchCourseDto.toDomain(): SearchCourse{
    return SearchCourse(
        id = id,
        title = title,
        cover = cover
    )
}
