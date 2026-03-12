package francisco.simon.projectkmp.di

import io.ktor.client.engine.android.Android
import org.koin.dsl.module

internal actual val platformModule = module {
    single(createdAtStart = true) {
        Android.create()
    }
}
