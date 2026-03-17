package francisco.simon.projectkmp.auth

import io.github.aakira.napier.Napier
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.parameters
import org.publicvalue.multiplatform.oidc.OpenIdConnectClient
import org.publicvalue.multiplatform.oidc.flows.CodeAuthFlowFactory
import org.publicvalue.multiplatform.oidc.types.CodeChallengeMethod
import org.publicvalue.multiplatform.oidc.types.remote.AccessTokenResponse

class TestAuth(
    private val authFlowFactory: CodeAuthFlowFactory
) {
    private val client = OpenIdConnectClient {
        endpoints {
            authorizationEndpoint = "https://stepik.org/oauth2/authorize/"
            tokenEndpoint = "https://stepik.org/oauth2/token/"
        }

        clientId = ""
        clientSecret = ""

        codeChallengeMethod = CodeChallengeMethod.S256

        redirectUri = "https://francisco.com/redirect"
    }

    suspend fun authorize(accessToken: (AccessTokenResponse) -> Unit) {
        val authFlow = authFlowFactory.createAuthFlow(client)
        authFlow.startLogin {
            parameters {
                append("response_type", "code")
            }
        }
        if (authFlow.canContinueLogin()) {
            val token = authFlow.continueLogin {
                contentType(ContentType.Application.FormUrlEncoded)
                method = HttpMethod.Post
                parameters {
                    append("grant_type", "authorization_code")
                }
            }
            Napier.d(tag = "TestAuth", message = token.toString())
            accessToken(token)
        }
    }
}
