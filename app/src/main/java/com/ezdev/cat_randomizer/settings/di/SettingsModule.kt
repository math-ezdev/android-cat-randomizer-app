package com.ezdev.cat_randomizer.settings.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.ezdev.cat_randomizer.settings.data.ThemeModeRepository
import com.ezdev.cat_randomizer.settings.data.ThemeModeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsModule {
    companion object {
        private const val SETTINGS_PREFERENCES_NAME = "SETTINGS_PREFERENCES_NAME"
        private val Context.dataStore by preferencesDataStore(name = SETTINGS_PREFERENCES_NAME)

        @Singleton
        @Provides
        fun providePreferencesDatastore(@ApplicationContext context: Context): DataStore<Preferences> =
            context.dataStore

    }

    @Singleton
    @Binds
    abstract fun bindThemeModeRepository(
        themeModeRepositoryImpl: ThemeModeRepositoryImpl
    ): ThemeModeRepository
}