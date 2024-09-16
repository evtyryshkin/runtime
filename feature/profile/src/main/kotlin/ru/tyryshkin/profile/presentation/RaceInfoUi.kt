package ru.tyryshkin.profile.presentation

import ru.tyryshkin.core.domain.entity.Distance

data class RaceInfoUi(
    val id: Long,
    val date: String,
    val distance: Distance,
    val time: String
)
