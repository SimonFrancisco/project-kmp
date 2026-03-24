package francisco.simon.projectkmp.features.auth.domain.entity

data class Token(
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Long,
    val scope: String,
    val refreshToken: String
)
