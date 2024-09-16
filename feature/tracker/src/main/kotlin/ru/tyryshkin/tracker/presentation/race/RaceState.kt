package ru.tyryshkin.tracker.presentation.race

import ru.tyryshkin.core.domain.entity.Distance
import ru.tyryshkin.core.presentation.State

data class RaceContent(
    val date: String,
    val time: String,
    val distance: Distance,
    val state: State
) : State.Content() {

    enum class State { RUNTIME, PAUSE }
}

data class CountDownContent(
    val value: Long
) : State.Content() {
    val valueInSeconds = (value / 1000).toInt()
}
