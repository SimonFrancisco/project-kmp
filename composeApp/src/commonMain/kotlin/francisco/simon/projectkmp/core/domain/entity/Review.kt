package francisco.simon.projectkmp.core.domain.entity

data class Review(
    val id: Int,
    val userId: Long,
    val score: Int,
    val text: String
)
