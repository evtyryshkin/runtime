package ru.tyryshkin.core.domain.entity

import com.google.android.gms.maps.model.LatLng

data class RaceInfo(
    val id: Long = 0,
    val date: String,
    val distance: Float,
    val time: Long,
    val points: List<LatLng>
)
