package francisco.simon.projectkmp.di

import francisco.simon.projectkmp.app.AppViewModel
import francisco.simon.projectkmp.features.auth.ui.AuthScreenViewModel
import francisco.simon.projectkmp.features.catalog.ui.screen.CatalogScreenViewModel
import francisco.simon.projectkmp.features.common.CourseDetailScreenViewModel
import francisco.simon.projectkmp.features.search.screen.SearchScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModelOf(constructor = ::AuthScreenViewModel)
    viewModelOf(constructor = ::CatalogScreenViewModel)
    viewModelOf(constructor = ::SearchScreenViewModel)
    viewModelOf(constructor = ::AppViewModel)

    viewModel { params ->
        CourseDetailScreenViewModel(
            courseId = params.get(),
            getCourseUseCase = get()
        )
    }
}
