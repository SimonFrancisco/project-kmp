package francisco.simon.projectkmp.di

import francisco.simon.projectkmp.features.search.data.FakeLoginRepositoryImpl
import francisco.simon.projectkmp.features.search.domain.LoginRepository

internal object FakeServiceLocator {
    val loginRepository: LoginRepository = FakeLoginRepositoryImpl()
}
