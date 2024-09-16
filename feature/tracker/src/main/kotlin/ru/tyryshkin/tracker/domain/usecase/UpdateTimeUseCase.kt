package ru.tyryshkin.tracker.domain.usecase

import ru.tyryshkin.tracker.domain.repository.TrackingStorageRepository
import javax.inject.Inject

class UpdateTimeUseCase @Inject constructor(
    private val repository: TrackingStorageRepository
) {
    suspend fun invoke(time: Long) = repository.updateTime(time)
}
