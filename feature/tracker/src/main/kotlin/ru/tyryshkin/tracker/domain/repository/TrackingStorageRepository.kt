package ru.tyryshkin.tracker.domain.repository

import android.location.Location
import kotlinx.coroutines.flow.Flow
import ru.tyryshkin.core.domain.entity.RaceInfo

interface TrackingStorageRepository {
    suspend fun updateLocation(location: Location)
    suspend fun updateSteps(steps: Long)
    suspend fun updateTime(time: Long)
    suspend fun updateDate(date: String)
    suspend fun updateDistance(distance: Float)
    suspend fun getCurrentLocation(): Flow<Location?>
    suspend fun getTime(): Flow<Long>
    fun getRaceInfo(): RaceInfo
}
