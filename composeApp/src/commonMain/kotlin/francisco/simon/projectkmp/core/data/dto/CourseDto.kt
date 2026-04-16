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
    val priceDisplayed: String?,
    @SerialName("with_certificate")
    val hasCertificate: Boolean,
    @SerialName("target_audience")
    val audience: String,
    @SerialName("requirements")
    val requirements: String,
    @SerialName("sections")
    val sections: List<Int>,
    @SerialName("intro_video")
    val introVideo: IntroVideoDto?,
    @SerialName("acquired_skills")
    val acquiredSkills: List<String>,
    @SerialName("language")
    val language: String,
    @SerialName("time_to_complete")
    val timeToComplete: Long,
    @SerialName("certificate_regular_threshold")
    val certificateRegularThreshold: Int?,
    @SerialName("certificate_distinction_threshold")
    val certificateDistinctionThreshold: Int?
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
        priceDisplayed = priceDisplayed,
        hasCertificate = hasCertificate,
        audience = audience,
        requirements = requirements,
        sections = sections,
        introVideo = introVideo?.toDomain(),
        acquiredSkills = acquiredSkills,
        language = language,
        timeToComplete = timeToComplete,
        certificateRegularThreshold = certificateDistinctionThreshold,
        certificateDistinctionThreshold = certificateDistinctionThreshold
    )
}
