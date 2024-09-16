package ru.tyryshkin.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.tyryshkin.db.AppDatabase.Companion.DATABASE_VERSION
import ru.tyryshkin.db.dao.RaceDao
import ru.tyryshkin.db.entity.Converters
import ru.tyryshkin.db.entity.Race

@Database(entities = [Race::class], version = DATABASE_VERSION)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun raceDao(): RaceDao

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "app_database"
    }
}
