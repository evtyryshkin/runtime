package ru.tyryshkin.tracker.data

import com.google.android.gms.maps.model.LatLng
import ru.tyryshkin.core.domain.entity.RaceInfo
import ru.tyryshkin.db.dao.RaceDao
import ru.tyryshkin.db.entity.Race
import ru.tyryshkin.db.entity.RoomLatLng
import ru.tyryshkin.tracker.domain.repository.RaceRepository
import javax.inject.Inject

class RaceRepositoryImpl @Inject constructor(private val raceDao: RaceDao) : RaceRepository {
    override suspend fun saveRace(raceInfo: RaceInfo): Long {
        val race = Race(
            id = raceInfo.id,
            date = raceInfo.date,
            distance = raceInfo.distance,
            time = raceInfo.time,
            points = raceInfo.points.map { RoomLatLng(it.latitude, it.longitude) }
        )
        return raceDao.insert(race)
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
