package francisco.simon.projectkmp.features.onboarding.prefs

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OnboardingManagerImpl(
    private val dataStore: DataStore<Preferences>
) : OnboardingManager {

    private companion object {
        val ONBOARDING = booleanPreferencesKey("onboarding")
    }

    override val onBoardingCompleted: Flow<Boolean> =
        dataStore.data.map {
            it[ONBOARDING] ?: false
        }

    override suspend fun completeOnBoarding() {
        dataStore.edit { preferences ->
            preferences[ONBOARDING] = true
        }
    }

    override suspend fun clear() {
        dataStore.edit { it.clear() }
    }
}
