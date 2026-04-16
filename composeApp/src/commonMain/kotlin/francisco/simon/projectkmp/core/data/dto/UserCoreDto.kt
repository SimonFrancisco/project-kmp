package francisco.simon.projectkmp.core.data.dto

import francisco.simon.projectkmp.core.domain.entity.UserCore
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserCoreDto(
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

fun UserCoreDto.toDomain(): UserCore {
    return UserCore(
        id = id,
        fullName = fullName,
        image = image,
        bio = bio,
        registrationDate = registrationDate
    )
}
