package francisco.simon.projectkmp.di

import francisco.simon.projectkmp.features.catalog.screen.CatalogScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModel {
        CatalogScreenViewModel(
            getCoursesUseCase = get(),
            getCatalogCoursesUseCase = get(),
            loadNextPageUseCase = get()
        )
    }
}
