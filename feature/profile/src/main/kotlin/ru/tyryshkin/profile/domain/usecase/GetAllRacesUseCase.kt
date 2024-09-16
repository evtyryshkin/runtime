package ru.tyryshkin.profile.domain.usecase

import ru.tyryshkin.profile.domain.repository.RaceRepository
import javax.inject.Inject

class GetAllRacesUseCase @Inject constructor(private val repository: RaceRepository) {
    suspend fun invoke() = repository.getRaces().sortedByDescending { it.date }
}
