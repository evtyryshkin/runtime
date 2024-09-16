package ru.tyryshkin.db.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.tyryshkin.db.AppDatabase
import ru.tyryshkin.db.dao.RaceDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext applicationContext: Context): AppDatabase =
        Room.databaseBuilder(
            context = applicationContext,
            klass = AppDatabase::class.java,
            name = AppDatabase.DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideRaceDao(database: AppDatabase): RaceDao = database.raceDao()
}
