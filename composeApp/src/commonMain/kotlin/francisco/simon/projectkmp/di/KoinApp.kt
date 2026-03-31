package francisco.simon.projectkmp.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        printLogger()
        includes(configurations = arrayOf(config))
        modules(modules = appComponent)
    }
}
