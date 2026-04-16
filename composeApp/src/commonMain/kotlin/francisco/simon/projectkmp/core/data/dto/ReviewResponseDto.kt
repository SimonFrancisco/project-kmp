package francisco.simon.projectkmp.core.data.dto

import francisco.simon.projectkmp.core.domain.entity.Review
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewResponseDto(
    @SerialName("course-reviews")
    val reviews: List<ReviewDto>
)

fun ReviewResponseDto.toDomain(): List<Review> {
    return reviews.map {
        it.toDomain()
    }
}
