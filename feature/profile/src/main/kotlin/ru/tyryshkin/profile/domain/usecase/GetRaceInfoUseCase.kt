package ru.tyryshkin.profile.domain.usecase

import ru.tyryshkin.profile.domain.repository.RaceRepository
import javax.inject.Inject

class GetRaceInfoUseCase @Inject constructor(
    private val repository: RaceRepository
) {
    suspend fun invoke(raceId: Long) = repository.getRaceInfo(raceId)
}
