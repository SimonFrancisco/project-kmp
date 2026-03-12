package francisco.simon.projectkmp.di

import francisco.simon.projectkmp.features.catalog.ui.screen.CatalogScreenViewModel
import francisco.simon.projectkmp.features.common.CourseDetailScreenViewModel
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

    viewModel {params->
        CourseDetailScreenViewModel(
            courseId = params.get(),
            getCourseUseCase = get()
        )
    }
}
