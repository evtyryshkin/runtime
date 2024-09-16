package ru.tyryshkin.tracker.domain.usecase

import ru.tyryshkin.tracker.domain.repository.TrackingStorageRepository
import javax.inject.Inject

class SaveCurrentDateUseCase @Inject constructor(
    private val repository: TrackingStorageRepository
) {
    suspend fun invoke(date: String) = repository.updateDate(date)
}
