package francisco.simon.projectkmp.core.data.dto

import francisco.simon.projectkmp.core.domain.entity.Course
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseDto(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("summary")
    val summary: String,
    @SerialName("cover")
    val cover: String,
    @SerialName("workload")
    val workload: String,
    @SerialName("description")
    val description: String,
    @SerialName("learners_count")
    val participants: Int,
    @SerialName("is_paid")
    val isPaid: Boolean,
    @SerialName("display_price")
    val priceDisplayed: String?
)

internal fun CourseDto.toDomain(): Course {
    return Course(
        id = id,
        summary = summary,
        title = title,
        cover = cover,
        workload = workload,
        description = description,
        participants = participants,
        isPaid = isPaid,
        priceDisplayed = priceDisplayed
    )
}
