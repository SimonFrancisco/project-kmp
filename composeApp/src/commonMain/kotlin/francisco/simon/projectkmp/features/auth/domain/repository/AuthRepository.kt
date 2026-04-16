package francisco.simon.projectkmp.features.auth.domain.repository

import francisco.simon.projectkmp.features.auth.domain.entity.Token

interface AuthRepository {

    suspend fun exchangeCodeForToken(code: String): Result<Token>

    suspend fun refreshToken(refreshToken: String): Result<Token>
}
