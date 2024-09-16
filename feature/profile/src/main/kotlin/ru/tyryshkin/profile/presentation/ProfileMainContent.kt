package ru.tyryshkin.profile.presentation

import ru.tyryshkin.core.presentation.State

data class ProfileMainContent(
    val races: List<RaceInfoUi>
) : State.Content()
