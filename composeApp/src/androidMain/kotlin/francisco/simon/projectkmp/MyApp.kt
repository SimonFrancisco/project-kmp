package francisco.simon.projectkmp

import android.app.Application
import francisco.simon.projectkmp.di.initKoin

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.publicvalue.multiplatform.oidc.appsupport.AndroidCodeAuthFlowFactory

class MyApp : Application() {

    val codeAuthFlowFactory by lazy {
        AndroidCodeAuthFlowFactory(useWebView = false)
    }

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(androidContext = this@MyApp)
            androidLogger(level = Level.INFO)
        }
    }

}
