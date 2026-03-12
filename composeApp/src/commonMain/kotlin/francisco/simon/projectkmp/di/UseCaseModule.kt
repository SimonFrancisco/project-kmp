package francisco.simon.projectkmp.di

import francisco.simon.projectkmp.core.domain.usecase.GetCourseUseCase
import francisco.simon.projectkmp.core.domain.usecase.GetCoursesUseCase
import francisco.simon.projectkmp.features.catalog.domain.usecase.GetCatalogCoursesUseCase
import francisco.simon.projectkmp.features.catalog.domain.usecase.LoadNextPageUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val useCaseModule = module {
    singleOf(constructor = ::GetCoursesUseCase)
    singleOf(constructor = ::GetCatalogCoursesUseCase)
    singleOf(constructor = ::LoadNextPageUseCase)
    singleOf(constructor = ::GetCourseUseCase)
}
