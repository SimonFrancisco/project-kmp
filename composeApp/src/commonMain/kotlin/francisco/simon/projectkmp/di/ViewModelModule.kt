package francisco.simon.projectkmp.di

import francisco.simon.projectkmp.app.AppViewModel
import francisco.simon.projectkmp.features.auth.ui.AuthScreenViewModel
import francisco.simon.projectkmp.features.catalog.ui.screen.CatalogScreenViewModel
import francisco.simon.projectkmp.features.common.CourseDetailScreenViewModel
import francisco.simon.projectkmp.features.search.screen.SearchScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModel {
        AuthScreenViewModel(loginUseCase = get())
    }

    viewModel {
        CatalogScreenViewModel(
            getCoursesUseCase = get(),
            getCatalogCoursesUseCase = get(),
            loadNextPageUseCase = get()
        )
    }

    viewModel { params ->
        CourseDetailScreenViewModel(
            courseId = params.get(),
            getCourseUseCase = get()
        )
    }

    viewModel {
        SearchScreenViewModel(
            searchCoursesUseCase = get(),
            loadNextSearchPageUseCase = get()
        )
    }

    viewModel {
        AppViewModel(
            tokenStorage = get()
        )
    }
}
