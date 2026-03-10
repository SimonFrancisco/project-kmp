package francisco.simon.projectkmp.di

import francisco.simon.projectkmp.core.data.CoreRepositoryImpl
import francisco.simon.projectkmp.core.domain.repository.CoreRepository
import francisco.simon.projectkmp.features.catalog.data.CatalogRepositoryImpl
import francisco.simon.projectkmp.features.catalog.domain.repository.CatalogRepository
import io.ktor.client.HttpClient
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
    single(createdAtStart = true) {
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

    singleOf(constructor = ::CatalogRepositoryImpl){
        bind<CatalogRepository>()
    }

    singleOf(constructor = ::CoreRepositoryImpl){
        bind<CoreRepository>()
    }
}
