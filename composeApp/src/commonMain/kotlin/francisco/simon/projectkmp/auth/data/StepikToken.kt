package francisco.simon.projectkmp.auth.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StepikToken(
    @SerialName("access_token") val accessToken: String,
    @SerialName("token_type") val tokenType: String,
    @SerialName("expires_in") val expiresIn: Long,
    @SerialName("scope") val scope: String = "",
    @SerialName("refresh_token") val refreshToken: String = ""
)
