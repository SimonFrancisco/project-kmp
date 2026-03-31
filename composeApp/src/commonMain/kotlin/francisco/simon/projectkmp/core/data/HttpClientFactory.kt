package francisco.simon.projectkmp.core.data

import francisco.simon.projectkmp.features.auth.domain.repository.AuthRepository
import francisco.simon.projectkmp.features.auth.prefs.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine = engine) {
            install(plugin = HttpCache)
            install(plugin = ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
            install(plugin = Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }
    }

    fun create(
        engine: HttpClientEngine,
        authRepository: AuthRepository,
        tokenStorage: TokenStorage
    ): HttpClient {
        return HttpClient(engine = engine) {
            install(plugin = HttpCache)
            install(plugin = ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
            install(plugin = Auth) {
                bearer {
                    loadTokens {
                        tokenStorage.getTokens()
                    }
                    refreshTokens {
                        val refreshToken = oldTokens?.refreshToken ?: return@refreshTokens null
                        val newToken = authRepository.refreshToken(refreshToken).getOrNull()
                        if (newToken != null) {
                            tokenStorage.saveTokens(newToken)
                            BearerTokens(
                                newToken.accessToken,
                                newToken.refreshToken
                            )
                        } else {
                            tokenStorage.clear()
                            null
                        }
                    }
                }
            }
            install(plugin = Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }
    }
}
