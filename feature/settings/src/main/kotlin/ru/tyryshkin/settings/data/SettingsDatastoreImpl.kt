package ru.tyryshkin.settings.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import ru.tyryshkin.settings.domain.SettingsRepository
import javax.inject.Inject

private const val DEFAULT_COUNT_DOWN_BEFORE_RUN_VALUE = 3

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
private val COUNT_DOWN_BEFORE_RUN_VALUE = intPreferencesKey("COUNT_DOWN_BEFORE_RUN_VALUE")

class SettingsDatastoreImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsRepository {
    private val settingsDataStore = context.dataStore

    override suspend fun setCountDownBeforeRunValue(value: Int?) {
        settingsDataStore.edit { preferences -> preferences[COUNT_DOWN_BEFORE_RUN_VALUE] = value ?: 0 }
    }

    override suspend fun getCountDownBeforeRunValue(): Int? {
        return settingsDataStore.data.map { preferences ->
            val value = preferences[COUNT_DOWN_BEFORE_RUN_VALUE]
            if (value == null) DEFAULT_COUNT_DOWN_BEFORE_RUN_VALUE else if (value == 0) null else value
        }.firstOrNull()
    }
}
