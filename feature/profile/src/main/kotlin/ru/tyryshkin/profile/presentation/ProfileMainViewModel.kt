package ru.tyryshkin.profile.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.tyryshkin.core.domain.TimeConverter
import ru.tyryshkin.core.domain.entity.Distance
import ru.tyryshkin.core.domain.entity.RaceInfo
import ru.tyryshkin.core.presentation.State
import ru.tyryshkin.core.presentation.executeAndUpdateWithState
import ru.tyryshkin.profile.domain.usecase.GetAllRacesUseCase
import javax.inject.Inject

@HiltViewModel
class ProfileMainViewModel @Inject constructor(
    private val getAllRacesUseCase: GetAllRacesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    fun onRetry() = loadData()

    private fun loadData() = executeAndUpdateWithState(_state) {
        val races = getAllRacesUseCase.invoke()
        ProfileMainContent(races = races.map { it.toUi() })
    }

    private fun RaceInfo.toUi() = RaceInfoUi(
        id = id,
        date = date,
        distance = Distance(distance),
        time = TimeConverter.convert(time)
    )
}
