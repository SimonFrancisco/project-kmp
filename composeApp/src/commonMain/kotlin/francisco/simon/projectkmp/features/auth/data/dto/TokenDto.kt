package francisco.simon.projectkmp.features.auth.data.dto

import francisco.simon.projectkmp.features.auth.domain.entity.Token
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenDto(
    @SerialName("access_token") val accessToken: String,
    @SerialName("token_type") val tokenType: String,
    @SerialName("expires_in") val expiresIn: Long,
    @SerialName("scope") val scope: String,
    @SerialName("refresh_token") val refreshToken: String
)

internal fun TokenDto.toDomain(): Token {
    return Token(
        accessToken = accessToken,
        tokenType = tokenType,
        expiresIn = expiresIn,
        scope = scope,
        refreshToken = refreshToken
    )
}
