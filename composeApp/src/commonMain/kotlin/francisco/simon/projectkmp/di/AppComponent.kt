package francisco.simon.projectkmp.di

import org.koin.core.module.Module

internal val appComponent: List<Module> = listOf(
    dataModule,
    useCaseModule,
    viewModelModule
)
