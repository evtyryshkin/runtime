package ru.tyryshkin.tracker.domain.usecase

import ru.tyryshkin.tracker.domain.repository.TrackingStorageRepository
import javax.inject.Inject

class UpdateDistanceUseCase @Inject constructor(
    private val repository: TrackingStorageRepository
) {
    suspend fun invoke(distance: Float) = repository.updateDistance(distance)
}
