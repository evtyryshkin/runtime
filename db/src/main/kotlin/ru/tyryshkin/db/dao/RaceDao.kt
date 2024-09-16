package ru.tyryshkin.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.tyryshkin.db.entity.Race

@Dao
interface RaceDao {
    @Query("SELECT * FROM race")
    suspend fun getAll(): List<Race>

    @Query("SELECT * FROM race WHERE id = :raceId")
    suspend fun getRace(raceId: Long): Race

    @Insert
    suspend fun insert(race: Race): Long

    @Delete
    suspend fun delete(race: Race)
}
