package francisco.simon.projectkmp

import android.app.Application
import francisco.simon.projectkmp.di.initKoin
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Napier.base(DebugAntilog())
        initKoin {
            androidContext(androidContext = this@MyApp)
            androidLogger(level = Level.INFO)
        }
    }
}
