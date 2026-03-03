package francisco.simon.projectkmp.di

import francisco.simon.projectkmp.features.friends.data.FakeFriendsRepositoryImpl
import francisco.simon.projectkmp.features.friends.domain.repository.FriendsRepository

internal object FakeServiceLocator {
    val friendsRepository: FriendsRepository = FakeFriendsRepositoryImpl()
}
