package francisco.simon.projectkmp.features.profile.data.dto

import francisco.simon.projectkmp.features.profile.domain.entity.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponseDto(
    @SerialName("users")
    val users: List<UserDto>
)

fun ProfileResponseDto.toDomain(): User {
    return users.first().toDomain()
}
