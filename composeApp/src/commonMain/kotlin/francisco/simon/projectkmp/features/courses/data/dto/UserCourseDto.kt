package francisco.simon.projectkmp.features.courses.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserCourseDto(
    @SerialName("course")
    val id: Int
)
