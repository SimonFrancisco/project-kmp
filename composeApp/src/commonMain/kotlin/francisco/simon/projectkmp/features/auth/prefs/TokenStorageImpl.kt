package francisco.simon.projectkmp.features.auth.prefs

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import francisco.simon.projectkmp.features.auth.domain.entity.Token
import francisco.simon.projectkmp.features.auth.domain.repository.AuthRepository
import io.ktor.client.plugins.auth.providers.BearerTokens
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Clock

/**
 * MOVE TOKEN REFRESH TO WORK MANAGER IN FUTURE
 */
class TokenStorageImpl(
    private val dataStore: DataStore<Preferences>,
    private val authRepository: AuthRepository
) : TokenStorage {

    private companion object {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val EXPIRES_AT = longPreferencesKey("token_expire_period")
        const val MILLISECONDS_IN_SECOND = 1000
        const val FIFTEEN_MINUTES_IN_MILLISECONDS = 15 * 60 * 1000
    }

    private val refreshMutex = Mutex()

    override val tokensFlow: Flow<BearerTokens?> =
        dataStore.data.map { prefs ->
            val access = prefs[ACCESS_TOKEN]
            val refresh = prefs[REFRESH_TOKEN]
            val expiresAt = prefs[EXPIRES_AT]

            if (expiresAt != null && access != null && refresh != null) {
                BearerTokens(access, refresh)
            } else {
                null
            }
        }

    override suspend fun getTokens(): BearerTokens? {
        val prefs = dataStore.data.first()

        val accessToken = prefs[ACCESS_TOKEN]
        val refreshToken = prefs[REFRESH_TOKEN]
        val expiresAt = prefs[EXPIRES_AT]

        if (accessToken == null || refreshToken == null || expiresAt == null) {
            return null
        }
        val now = Clock.System.now().toEpochMilliseconds()
        val isExpired = now >= (expiresAt - FIFTEEN_MINUTES_IN_MILLISECONDS)

        return if (!isExpired) {
            BearerTokens(accessToken, refreshToken)
        } else {
            refreshTokenDoubleCheck()
        }
    }

    override suspend fun saveTokens(token: Token) {
        val expiresAt =
            Clock.System.now().toEpochMilliseconds() + (token.expiresIn * MILLISECONDS_IN_SECOND)
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN] = token.accessToken
            prefs[REFRESH_TOKEN] = token.refreshToken
            prefs[EXPIRES_AT] = expiresAt
        }
    }

    override suspend fun clear() {
        dataStore.edit {
            it.remove(ACCESS_TOKEN)
            it.remove(REFRESH_TOKEN)
            it.remove(EXPIRES_AT)
        }
    }

    private suspend fun refreshToken(refreshToken: String): Token? {
        val newToken = authRepository.refreshToken(refreshToken).getOrNull()
        return if (newToken != null) {
            saveTokens(newToken)
            newToken
        } else {
            clear()
            null
        }
    }

    private suspend fun refreshTokenDoubleCheck(): BearerTokens? = refreshMutex.withLock {
        val currentPrefs = dataStore.data.first()
        val currentExpires = currentPrefs[EXPIRES_AT]
        val currentAccess = currentPrefs[ACCESS_TOKEN]
        val currentRefresh = currentPrefs[REFRESH_TOKEN]

        val now = Clock.System.now().toEpochMilliseconds()
        if (currentExpires != null && now < (currentExpires - FIFTEEN_MINUTES_IN_MILLISECONDS)
        ) {
            return BearerTokens(requireNotNull(currentAccess), currentRefresh)
        }

        return if (currentRefresh != null) {
            val newToken = refreshToken(currentRefresh)
            newToken?.let { BearerTokens(it.accessToken, it.refreshToken) }
        } else {
            null
        }
    }
}
