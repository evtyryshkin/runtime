package ru.tyryshkin.tracker.domain.usecase

import android.location.Location
import ru.tyryshkin.tracker.domain.repository.TrackingStorageRepository
import javax.inject.Inject

class UpdateLocationUseCase @Inject constructor(
    private val repository: TrackingStorageRepository
) {
    suspend fun invoke(location: Location) = repository.updateLocation(location)
}
