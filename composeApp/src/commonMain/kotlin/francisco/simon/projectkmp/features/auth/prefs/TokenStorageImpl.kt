package francisco.simon.projectkmp.features.auth.prefs

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import francisco.simon.projectkmp.features.auth.domain.entity.Token
import io.ktor.client.plugins.auth.providers.BearerTokens
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TokenStorageImpl(
    private val dataStore: DataStore<Preferences>
): TokenStorage {

    private companion object Keys {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    override val tokensFlow: Flow<BearerTokens?> =
        dataStore.data.map { prefs ->
            val access = prefs[ACCESS_TOKEN]
            val refresh = prefs[REFRESH_TOKEN]

            if (access != null && refresh != null) {
                BearerTokens(access, refresh)
            } else null
        }

    override suspend fun getTokens(): BearerTokens? {
        val prefs = dataStore.data.first()

        val access = prefs[ACCESS_TOKEN]
        val refresh = prefs[REFRESH_TOKEN]

        return if (access != null && refresh != null) {
            BearerTokens(access, refresh)
        } else {
            null
        }
    }

    override suspend fun saveTokens(token: Token) {
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN] = token.accessToken
            prefs[REFRESH_TOKEN] = token.refreshToken
        }
    }

    override suspend fun clear() {
        dataStore.edit { it.clear() }
    }
}
