package ru.tyryshkin.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Race(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val date: String,
    val time: Long,
    val distance: Float,
    val points: List<RoomLatLng>
)
