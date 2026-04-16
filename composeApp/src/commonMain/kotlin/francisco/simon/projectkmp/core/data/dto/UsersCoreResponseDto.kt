package francisco.simon.projectkmp.core.data.dto

import francisco.simon.projectkmp.core.domain.entity.UserCore
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsersCoreResponseDto(
    @SerialName("users")
    val users: List<UserCoreDto>
)

fun UsersCoreResponseDto.toDomain(): UserCore {
    return users.first().toDomain()
}
