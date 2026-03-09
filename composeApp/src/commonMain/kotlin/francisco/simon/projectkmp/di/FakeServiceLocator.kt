package francisco.simon.projectkmp.di

import francisco.simon.projectkmp.features.friends.data.FakeFriendsRepositoryImpl
import francisco.simon.projectkmp.features.friends.domain.repository.FriendsRepository
import francisco.simon.projectkmp.features.login.data.FakeLoginRepositoryImpl
import francisco.simon.projectkmp.features.login.domain.LoginRepository

internal object FakeServiceLocator {
    val friendsRepository: FriendsRepository = FakeFriendsRepositoryImpl()
    val loginRepository: LoginRepository = FakeLoginRepositoryImpl()
}
