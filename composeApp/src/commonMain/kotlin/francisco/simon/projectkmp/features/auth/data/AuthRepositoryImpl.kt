package francisco.simon.projectkmp.features.auth.data

import francisco.simon.projectkmp.StepikAppSecrets
import francisco.simon.projectkmp.core.utils.runCatchingCancellable
import francisco.simon.projectkmp.features.auth.data.dto.TokenDto
import francisco.simon.projectkmp.features.auth.data.dto.toDomain
import francisco.simon.projectkmp.features.auth.domain.entity.Token
import francisco.simon.projectkmp.features.auth.domain.repository.AuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.formUrlEncode
import io.ktor.http.parameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlin.io.encoding.Base64

class AuthRepositoryImpl(
    private val httpClient: HttpClient
) : AuthRepository {

    override suspend fun exchangeCodeForToken(code: String): Result<Token> {
        return withContext(Dispatchers.IO) {
            runCatchingCancellable {
                val credentials =
                    "${StepikAppSecrets.STEPIK_CLIENT_ID}:${StepikAppSecrets.STEPIK_CLIENT_SECRET}"
                val basicAuth = Base64.encode(credentials.encodeToByteArray())
                val response = httpClient.post(StepikAuthConfig.TOKEN_URL) {
                    header("Authorization", "Basic $basicAuth")
                    contentType(ContentType.Application.FormUrlEncoded)
                    setBody(
                        parameters {
                            append("grant_type", "authorization_code")
                            append("code", code)
                            append("redirect_uri", StepikAuthConfig.REDIRECT_URI)
                        }.formUrlEncode()
                    )
                }
                response.body<TokenDto>().toDomain()
            }
        }
    }

    override suspend fun refreshToken(refreshToken: String): Result<Token> {
        return withContext(Dispatchers.IO) {
            runCatchingCancellable {
                val credentials =
                    "${StepikAppSecrets.STEPIK_CLIENT_ID}:${StepikAppSecrets.STEPIK_CLIENT_SECRET}"
                val basicAuth = Base64.encode(credentials.encodeToByteArray())
                val response = httpClient.post(StepikAuthConfig.TOKEN_URL) {
                    header("Authorization", "Basic $basicAuth")
                    contentType(ContentType.Application.FormUrlEncoded)
                    setBody(
                        parameters {
                            append("grant_type", "refresh_token")
                            append("refresh_token", refreshToken)
                        }.formUrlEncode()
                    )
                }
                response.body<TokenDto>().toDomain()
            }
        }
    }
}
