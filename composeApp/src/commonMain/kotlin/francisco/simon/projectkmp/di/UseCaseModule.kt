package francisco.simon.projectkmp.di

import francisco.simon.projectkmp.core.domain.usecase.GetCourseUseCase
import francisco.simon.projectkmp.core.domain.usecase.GetCoursesUseCase
import francisco.simon.projectkmp.features.auth.domain.usecase.LoginUseCase
import francisco.simon.projectkmp.features.catalog.domain.usecase.GetCatalogCoursesUseCase
import francisco.simon.projectkmp.features.catalog.domain.usecase.LoadNextPageUseCase
import francisco.simon.projectkmp.features.catalog.domain.usecase.RefreshUseCase
import francisco.simon.projectkmp.features.search.domain.usecase.LoadNextSearchPageUseCase
import francisco.simon.projectkmp.features.search.domain.usecase.SearchCoursesUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val useCaseModule = module {
    factoryOf(constructor = ::GetCoursesUseCase)
    factoryOf(constructor = ::GetCatalogCoursesUseCase)
    factoryOf(constructor = ::LoadNextPageUseCase)
    factoryOf(constructor = ::GetCourseUseCase)
    factoryOf(constructor = ::SearchCoursesUseCase)
    factoryOf(constructor = ::LoadNextSearchPageUseCase)
    factoryOf(constructor = ::LoginUseCase)
    factoryOf(constructor = ::RefreshUseCase)
}
