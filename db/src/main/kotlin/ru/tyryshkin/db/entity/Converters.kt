package ru.tyryshkin.db.entity

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromPoints(points: List<RoomLatLng>): String =
        Json.encodeToString(points)

    @TypeConverter
    fun toPoints(string: String): List<RoomLatLng> =
        Json.decodeFromString<List<RoomLatLng>>(string)
}
