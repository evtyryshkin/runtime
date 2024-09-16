package ru.tyryshkin.settings.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.tyryshkin.settings.data.SettingsDatastoreImpl
import ru.tyryshkin.settings.domain.SettingsRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface DatastoreModule {

    @Binds
    @Singleton
    fun provideSettingsDatastore(impl: SettingsDatastoreImpl): SettingsRepository
}
