package francisco.simon.projectkmp.features.profile.data

import francisco.simon.projectkmp.features.auth.prefs.TokenStorage
import francisco.simon.projectkmp.features.profile.data.dto.ProfileResponseDto
import francisco.simon.projectkmp.features.profile.data.dto.toDomain
import francisco.simon.projectkmp.features.profile.domain.entity.User
import francisco.simon.projectkmp.features.profile.domain.repository.ProfileRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ProfileRepositoryImpl(
    private val httpClient: HttpClient,
    private val tokenStorage: TokenStorage
) : ProfileRepository {

    override suspend fun getUser(): User {
        val response = httpClient.get(
            urlString = "https://stepik.org/api/stepics/1"
        ) {
        }.body<ProfileResponseDto>()
        return response.toDomain()
    }

    override suspend fun logout() {
        tokenStorage.clear()
    }
}
