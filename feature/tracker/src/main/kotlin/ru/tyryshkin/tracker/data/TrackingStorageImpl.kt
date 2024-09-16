package ru.tyryshkin.tracker.data

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ru.tyryshkin.core.domain.entity.RaceInfo
import ru.tyryshkin.core.utils.DateTimeFormatter
import ru.tyryshkin.tracker.domain.repository.TrackingStorageRepository
import java.time.LocalDateTime
import javax.inject.Inject

class TrackingStorageImpl @Inject constructor() : TrackingStorageRepository {
    private val currentDateTime = MutableStateFlow<String?>(null)
    private val currentLocation = MutableStateFlow<Location?>(null)
    private val timeInSeconds = MutableStateFlow(0L)
    private val currentDistanceInMeters = MutableStateFlow(0f)
    private val points = mutableListOf<LatLng>()
    private val sumSteps = MutableStateFlow(0L)

    override suspend fun getCurrentLocation() = currentLocation
    override suspend fun getTime() = timeInSeconds
    override fun getRaceInfo(): RaceInfo =
        RaceInfo(
            date = currentDateTime.value ?: DateTimeFormatter.DATE_TIME.format(LocalDateTime.now()),
            distance = currentDistanceInMeters.value,
            time = timeInSeconds.value,
            points = points
        )

    override suspend fun updateLocation(location: Location) {
        currentLocation.update { location }
        points.add(LatLng(location.latitude, location.longitude))
    }

    override suspend fun updateDate(date: String) = currentDateTime.update { date }
    override suspend fun updateSteps(steps: Long) = sumSteps.update { steps }
    override suspend fun updateTime(time: Long) = timeInSeconds.update { time }
    override suspend fun updateDistance(distance: Float) = currentDistanceInMeters.update { distance }
}
