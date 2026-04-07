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
import francisco.simon.projectkmp.features.courses.data.UserCoursesRepositoryImpl
import francisco.simon.projectkmp.features.courses.domain.repository.UserCoursesRepository
import francisco.simon.projectkmp.features.onboarding.data.OnboardingRepositoryImpl
import francisco.simon.projectkmp.features.onboarding.domain.repository.OnboardingRepository
import francisco.simon.projectkmp.features.onboarding.prefs.OnboardingManager
import francisco.simon.projectkmp.features.onboarding.prefs.OnboardingManagerImpl
import francisco.simon.projectkmp.features.profile.data.ProfileRepositoryImpl
import francisco.simon.projectkmp.features.profile.domain.repository.ProfileRepository
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

    single<UserCoursesRepository> {
        UserCoursesRepositoryImpl(
            httpClient = get(qualifier = AUTH_CLIENT)
        )
    }

    single<ProfileRepository> {
        ProfileRepositoryImpl(
            httpClient = get(qualifier = AUTH_CLIENT),
            tokenStorage = get()
        )
    }

    singleOf(constructor = ::TokenStorageImpl) {
        bind<TokenStorage>()
    }

    singleOf(constructor = ::OnboardingManagerImpl) {
        bind<OnboardingManager>()
    }

    singleOf(constructor = ::OnboardingRepositoryImpl) {
        bind<OnboardingRepository>()
    }
}
