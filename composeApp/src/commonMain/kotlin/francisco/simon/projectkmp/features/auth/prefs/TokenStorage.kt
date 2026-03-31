package francisco.simon.projectkmp.features.auth.prefs

import francisco.simon.projectkmp.features.auth.domain.entity.Token
import io.ktor.client.plugins.auth.providers.BearerTokens
import kotlinx.coroutines.flow.Flow

interface TokenStorage {
    val tokensFlow: Flow<BearerTokens?>
    suspend fun getTokens(): BearerTokens?
    suspend fun saveTokens(token: Token)
    suspend fun clear()
}
