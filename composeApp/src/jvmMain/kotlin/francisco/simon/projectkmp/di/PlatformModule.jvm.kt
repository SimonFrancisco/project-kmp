package francisco.simon.projectkmp.di

import francisco.simon.projectkmp.createDataStore
import io.ktor.client.engine.java.Java
import org.koin.dsl.module

internal actual val platformModule = module {
    single(createdAtStart = true) {
        Java.create()
    }
    single(createdAtStart = true){
        createDataStore()
    }
}
