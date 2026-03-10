package francisco.simon.projectkmp.core.data

import francisco.simon.projectkmp.core.domain.entity.Course
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseDto(
    @SerialName("id")
    val id: Int,
    @SerialName("summary")
    val summary: String,
    @SerialName("cover")
    val cover: String,
    @SerialName("workload")
    val workload: String,
    @SerialName("description")
    val description: String
)

internal fun CourseDto.toDomain(): Course{
    return Course(
        id = id,
        summary = summary,
        cover = cover,
        workload = workload,
        description = description
    )
}
