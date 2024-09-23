package ru.tyryshkin.profile.data

import com.google.android.gms.maps.model.LatLng
import ru.tyryshkin.core.domain.entity.RaceInfo
import ru.tyryshkin.db.dao.RaceDao
import ru.tyryshkin.db.entity.Race
import ru.tyryshkin.profile.domain.repository.RaceRepository
import javax.inject.Inject

class RaceRepositoryImpl @Inject constructor(private val raceDao: RaceDao) : RaceRepository {

    override suspend fun getRaces(): List<RaceInfo> {
        return raceDao.getAll().map(Race::asDomain)
    }

    override suspend fun getRaceInfo(raceId: Long): RaceInfo {
        val race = raceDao.getRace(raceId)
        return RaceInfo(
            id = race.id,
            date = race.date,
            time = race.time,
            distance = race.distance,
            points = race.points.map { LatLng(it.latitude, it.longitude) }
        )
    }
}

private fun Race.asDomain() = RaceInfo(
    id = id,
    distance = distance,
    date = date,
    time = time,
    points = points.map { LatLng(it.latitude, it.longitude) }
)
