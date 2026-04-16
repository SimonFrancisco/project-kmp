package francisco.simon.projectkmp.features.courses.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponseDto(
    @SerialName("meta")
    val metaDto: MetaDto,
    @SerialName("user-courses")
    val courses: List<UserCourseDto>
)

fun UserResponseDto.toListId(): List<Int> {
    return courses.map {
        it.id
    }
}

