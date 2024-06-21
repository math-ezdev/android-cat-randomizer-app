package com.ezdev.cat_randomizer.settings.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val TAG = "ThemeModeRepository"

interface ThemeModeRepository {
    val themeMode: Flow<String>

    suspend fun save(themeMode: ThemeMode)
}


class ThemeModeRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ThemeModeRepository {
    private companion object {
        val THEME_MODE_KEY = stringPreferencesKey("THEME_MODE_KEY")
        val THEME_MODE_DEFAULT_VALUE: String = ThemeMode.DEFAULT_SYSTEM.name
    }

    override val themeMode: Flow<String> = dataStore.data
        .catch { cause ->
            if (cause is IOException) {
                emit(emptyPreferences())
                Log.e(TAG, "Error reading preferences!", cause)
                return@catch
            }

            throw cause
        }
        .map { preferences ->
            preferences[THEME_MODE_KEY] ?: THEME_MODE_DEFAULT_VALUE
        }

    override suspend fun save(themeMode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[THEME_MODE_KEY] = themeMode.name
        }
    }
}