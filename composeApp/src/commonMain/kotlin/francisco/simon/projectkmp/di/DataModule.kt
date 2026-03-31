package francisco.simon.projectkmp.di

import francisco.simon.projectkmp.core.data.HttpClientFactory
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
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val dataModule = module {
    single(createdAtStart = true, qualifier = PUBLIC_CLIENT) {
        HttpClientFactory.create(engine = get())
    }

    single(createdAtStart = true, qualifier = AUTH_CLIENT) {
        HttpClientFactory.create(
            engine = get(),
            authRepository = get(),
            tokenStorage = get()
        )
    }
    factory<AuthRepository> {
        AuthRepositoryImpl(
            httpClient = get(qualifier = PUBLIC_CLIENT)
        )
    }

    single<CatalogRepository> {
        CatalogRepositoryImpl(
            httpClient = get(qualifier = AUTH_CLIENT)
        )
    }

    factory<CoreRepository> {
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
