package ru.tyryshkin.tracker.domain.repository

import ru.tyryshkin.core.domain.entity.RaceInfo

interface RaceRepository {
    suspend fun saveRace(raceInfo: RaceInfo): Long
    suspend fun getRaceInfo(raceId: Long): RaceInfo
}
