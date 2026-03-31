package francisco.simon.projectkmp.di

import francisco.simon.projectkmp.createDataStore
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

internal actual val platformModule = module {
    single(createdAtStart = true) {
        Darwin.create()
    }
    single(createdAtStart = true){
        createDataStore()
    }
}
