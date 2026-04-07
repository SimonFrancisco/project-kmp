package francisco.simon.projectkmp.features.profile.data.dto

import francisco.simon.projectkmp.features.profile.domain.entity.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("id")
    val id: Long,
    @SerialName("full_name")
    val fullName: String,
    @SerialName("avatar")
    val image: String,
    @SerialName("short_bio")
    val bio: String,
    @SerialName("join_date")
    val registrationDate: String
)

fun UserDto.toDomain(): User {
    return User(
        id = id,
        fullName = fullName,
        image = image,
        bio = bio,
        registrationDate = registrationDate
    )
}
