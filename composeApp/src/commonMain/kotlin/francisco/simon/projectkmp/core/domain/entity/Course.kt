package francisco.simon.projectkmp.core.domain.entity

data class Course(
    val id: Int,
    val title: String,
    val summary: String,
    val cover: String,
    val participants: Int,
    val workload: String,
    val description: String,
    val isPaid: Boolean,
    val priceDisplayed: String?,
    val hasCertificate: Boolean,
    val audience: String,
    val requirements: String,
    val sections: List<Int>,
    val introVideo: IntroVideo?,
    val acquiredSkills: List<String>,
    val language: String,
    val timeToComplete: Long,
    val certificateRegularThreshold: Int?,
    val certificateDistinctionThreshold: Int?
)
