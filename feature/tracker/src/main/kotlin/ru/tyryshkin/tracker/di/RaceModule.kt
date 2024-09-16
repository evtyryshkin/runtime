package ru.tyryshkin.tracker.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.tyryshkin.tracker.data.RaceRepositoryImpl
import ru.tyryshkin.tracker.domain.repository.RaceRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RaceModule {
    @Binds
    @Singleton
    fun provideRaceRepository(impl: RaceRepositoryImpl): RaceRepository
}
