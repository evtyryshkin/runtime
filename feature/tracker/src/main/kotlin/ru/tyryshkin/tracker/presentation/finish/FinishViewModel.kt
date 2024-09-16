package ru.tyryshkin.tracker.presentation.finish

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.tyryshkin.core.domain.TimeConverter
import ru.tyryshkin.core.domain.entity.Distance
import ru.tyryshkin.core.presentation.State
import ru.tyryshkin.core.presentation.executeAndUpdateWithState
import ru.tyryshkin.tracker.domain.usecase.GetRaceInfoUseCase
import ru.tyryshkin.tracker.navigation.FinishDestination
import javax.inject.Inject

@HiltViewModel
class FinishViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRaceInfoUseCase: GetRaceInfoUseCase
) : ViewModel() {
    private val raceId = FinishDestination.extractRaceId(savedStateHandle)
    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    fun onRetry() = loadData()

    private fun loadData() = executeAndUpdateWithState(_state) {
        val raceInfo = getRaceInfoUseCase.invoke(raceId)
        FinishContent(
            raceInfo = raceInfo,
            date = raceInfo.date,
            time = TimeConverter.convert(raceInfo.time),
            distance = Distance(raceInfo.distance)
        )
    }
}
