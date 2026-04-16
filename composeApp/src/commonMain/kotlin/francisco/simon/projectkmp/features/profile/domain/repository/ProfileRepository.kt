package francisco.simon.projectkmp.features.profile.domain.repository

import francisco.simon.projectkmp.features.profile.domain.entity.User

interface ProfileRepository {
    suspend fun getUser(): User
    suspend fun logout()
}
