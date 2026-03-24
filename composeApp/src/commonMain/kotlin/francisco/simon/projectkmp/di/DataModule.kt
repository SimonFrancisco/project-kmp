package francisco.simon.projectkmp.di

import francisco.simon.projectkmp.core.data.impl.CoreRepositoryImpl
import francisco.simon.projectkmp.core.domain.repository.CoreRepository
import francisco.simon.projectkmp.features.auth.data.AuthRepositoryImpl
import francisco.simon.projectkmp.features.auth.domain.repository.AuthRepository
import francisco.simon.projectkmp.features.auth.prefs.TokenStorage
import francisco.simon.projectkmp.features.auth.prefs.TokenStorageImpl
import francisco.simon.projectkmp.features.catalog.data.CatalogRepositoryImpl
import francisco.simon.projectkmp.features.catalog.domain.repository.CatalogRepository
import francisco.simon.projectkmp.features.search.data.SearchRepositoryImpl
import francisco.simon.projectkmp.features.search.domain.repository.SearchRepository
import io.ktor.client.HttpClient
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
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val dataModule = module {
    single(createdAtStart = true, qualifier = PUBLIC_CLIENT) {
        HttpClient(engine = get()) {
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

    single(createdAtStart = true, qualifier = AUTH_CLIENT) {
        HttpClient(engine = get()) {
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
            install(Auth) {
                bearer {
                    val tokenStorage: TokenStorage = get()
                    val authRepository: AuthRepository = get()
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
    single<AuthRepository> {
        AuthRepositoryImpl(
            httpClient = get(qualifier = PUBLIC_CLIENT)
        )
    }

    single<CatalogRepository> {
        CatalogRepositoryImpl(
            httpClient = get(qualifier = AUTH_CLIENT)
        )
    }

    single<CoreRepository> {
        CoreRepositoryImpl(
            httpClient = get(qualifier = AUTH_CLIENT)
        )
    }

    single<SearchRepository> {
        SearchRepositoryImpl(
            httpClient = get(qualifier = AUTH_CLIENT)
        )
    }

    singleOf(constructor = ::TokenStorageImpl) {
        bind<TokenStorage>()
    }
}
