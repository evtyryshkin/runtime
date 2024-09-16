package ru.tyryshkin.tracker.presentation.finish

import ru.tyryshkin.core.domain.entity.Distance
import ru.tyryshkin.core.domain.entity.RaceInfo
import ru.tyryshkin.core.presentation.State

data class FinishContent(
    val raceInfo: RaceInfo,
    val date: String,
    val time: String,
    val distance: Distance
) : State.Content()
