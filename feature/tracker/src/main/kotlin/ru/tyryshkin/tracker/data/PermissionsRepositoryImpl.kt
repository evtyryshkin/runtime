package ru.tyryshkin.tracker.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.tyryshkin.tracker.domain.repository.PermissionRepository
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "permissions")
private val SNACKBAR_SHOWN = booleanPreferencesKey("SNACKBAR_SHOWN")

class PermissionsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PermissionRepository {
    private val snackbarDatastore = context.dataStore

    override val snackbarShown: Flow<Boolean> = snackbarDatastore.data
        .map { preferences -> preferences[SNACKBAR_SHOWN] ?: false }

    override suspend fun snackbarShown() {
        snackbarDatastore.edit { preferences -> preferences[SNACKBAR_SHOWN] = true }
    }
}
