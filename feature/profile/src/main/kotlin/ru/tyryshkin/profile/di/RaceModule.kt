package ru.tyryshkin.profile.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.tyryshkin.profile.data.RaceRepositoryImpl
import ru.tyryshkin.profile.domain.repository.RaceRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RaceModule {
    @Binds
    @Singleton
    fun provideRaceRepository(impl: RaceRepositoryImpl): RaceRepository
}
