package ru.tyryshkin.db.entity

import kotlinx.serialization.Serializable

@Serializable
data class RoomLatLng(
    val latitude: Double,
    val longitude: Double
)
