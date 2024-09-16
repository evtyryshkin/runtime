package ru.tyryshkin.tracker.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.tyryshkin.tracker.data.PermissionsRepositoryImpl
import ru.tyryshkin.tracker.domain.repository.PermissionRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface DatastoreModule {

    @Binds
    @Singleton
    fun providePermissionDatastore(impl: PermissionsRepositoryImpl): PermissionRepository
}
