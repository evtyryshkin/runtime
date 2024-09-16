package ru.tyryshkin.tracker.di

import android.app.Application
import android.content.Intent
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.tyryshkin.tracker.data.TrackingStorageImpl
import ru.tyryshkin.tracker.domain.repository.TrackingStorageRepository
import ru.tyryshkin.tracker.service.LocationClient
import ru.tyryshkin.tracker.service.LocationClientImpl
import ru.tyryshkin.tracker.service.LocationService
import ru.tyryshkin.tracker.service.TimerService
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface TrackingModule {

    @Binds
    @Singleton
    fun provideTrackingStorage(impl: TrackingStorageImpl): TrackingStorageRepository

    companion object {
        @Provides
        @Timer
        fun provideTimerServiceIntent(application: Application): Intent =
            Intent(application, TimerService::class.java)

        @Provides
        @Location
        fun provideLocationServiceIntent(application: Application): Intent =
            Intent(application, LocationService::class.java)

        @Provides
        fun provideLocationClient(application: Application): LocationClient =
            LocationClientImpl(application)
    }
}
