package francisco.simon.projectkmp.auth.data

import francisco.simon.projectkmp.auth.StepikAuthConfig
import francisco.simon.projectkmp.core.utils.runCatchingCancellable
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.formUrlEncode
import io.ktor.http.parameters
import io.ktor.util.encodeBase64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val httpClient: HttpClient
) : AuthRepository {

    override suspend fun exchangeCodeForToken(code: String): Result<StepikToken> =
        withContext(Dispatchers.IO) {
            runCatchingCancellable {
                val credentials = "${StepikAuthConfig.CLIENT_ID}:${StepikAuthConfig.CLIENT_SECRET}"
                val basicAuth = credentials.encodeToByteArray().encodeBase64()
                val response = httpClient.post(StepikAuthConfig.TOKEN_URL) {
                    header("Authorization", "Basic $basicAuth")
                    contentType(ContentType.Application.FormUrlEncoded)
                    setBody(parameters {
                        append("grant_type", "authorization_code")
                        append("code", code)
                        append("redirect_uri", StepikAuthConfig.REDIRECT_URI)
                    }.formUrlEncode())
                }
                response.body<StepikToken>()
            }
        }
}
