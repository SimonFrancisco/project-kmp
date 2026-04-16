package francisco.simon.projectkmp.core.data.dto

import francisco.simon.projectkmp.core.domain.entity.Review
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewDto(
    @SerialName("id")
    val id: Int,
    @SerialName("user")
    val userId: Long,
    @SerialName("score")
    val score: Int,
    @SerialName("text")
    val text: String
)

fun ReviewDto.toDomain(): Review {
    return Review(
        id = id,
        userId = userId,
        score = score,
        text = text
    )
}
