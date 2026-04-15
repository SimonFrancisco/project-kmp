package francisco.simon.projectkmp.core.data.dto

import francisco.simon.projectkmp.core.domain.entity.IntroVideo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IntroVideoDto(
    @SerialName("id")
    val id: Int,
    @SerialName("thumbnail")
    val thumbnail: String
)

internal fun IntroVideoDto.toDomain(): IntroVideo {
    return IntroVideo(
        id = id,
        thumbnail = thumbnail
    )
}
