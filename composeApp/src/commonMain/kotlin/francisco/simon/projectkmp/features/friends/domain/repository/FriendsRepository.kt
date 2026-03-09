package francisco.simon.projectkmp.features.friends.domain.repository

import francisco.simon.projectkmp.features.friends.domain.entity.Friend

interface FriendsRepository {
    suspend fun getFriends(): List<Friend>
}
