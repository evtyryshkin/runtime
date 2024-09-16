package ru.tyryshkin.tracker.domain.usecase

import ru.tyryshkin.tracker.domain.repository.RaceRepository
import javax.inject.Inject

class GetRaceInfoUseCase @Inject constructor(
    private val repository: RaceRepository
) {
    suspend fun invoke(raceId: Long) = repository.getRaceInfo(raceId)
}
