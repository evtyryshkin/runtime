package ru.tyryshkin.profile.domain.repository

import ru.tyryshkin.core.domain.entity.RaceInfo

interface RaceRepository {
    suspend fun getRaces(): List<RaceInfo>
}
