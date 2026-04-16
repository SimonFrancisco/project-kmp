package francisco.simon.projectkmp.features.search.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchCourseDto(
    @SerialName("course")
    val id: Int?
)
