package ru.tyryshkin.tracker.domain.usecase

import ru.tyryshkin.tracker.domain.repository.RaceRepository
import ru.tyryshkin.tracker.domain.repository.TrackingStorageRepository
import javax.inject.Inject

class SaveRaceUseCase @Inject constructor(
    private val trackingStorageRepository: TrackingStorageRepository,
    private val repository: RaceRepository
) {
    suspend fun invoke(): Long {
        val raceInfo = trackingStorageRepository.getRaceInfo()
        return repository.saveRace(raceInfo)
    }
}
