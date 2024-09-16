package ru.tyryshkin.tracker.presentation.race

import android.app.Application
import android.content.Intent
import android.location.Location
import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.tyryshkin.core.domain.TimeConverter
import ru.tyryshkin.core.domain.entity.Distance
import ru.tyryshkin.core.presentation.State
import ru.tyryshkin.core.presentation.executeWithState
import ru.tyryshkin.core.presentation.getValue
import ru.tyryshkin.core.presentation.updateContent
import ru.tyryshkin.core.utils.DateTimeFormatter
import ru.tyryshkin.settings.domain.usecase.GetCountDownBeforeRunValueUseCase
import ru.tyryshkin.tracker.di.Timer
import ru.tyryshkin.tracker.domain.usecase.GetCurrentLocationUseCase
import ru.tyryshkin.tracker.domain.usecase.GetCurrentTimeUseCase
import ru.tyryshkin.tracker.domain.usecase.SaveCurrentDateUseCase
import ru.tyryshkin.tracker.domain.usecase.SaveRaceUseCase
import ru.tyryshkin.tracker.domain.usecase.UpdateDistanceUseCase
import java.time.LocalDateTime
import javax.inject.Inject
import ru.tyryshkin.tracker.di.Location as LocationQualifier

@HiltViewModel
class RaceViewModel @Inject constructor(
    private val application: Application,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val getCurrentTimeUseCase: GetCurrentTimeUseCase,
    private val updateDistanceUseCase: UpdateDistanceUseCase,
    private val saveCurrentDateUseCase: SaveCurrentDateUseCase,
    private val saveRaceUseCase: SaveRaceUseCase,
    private val getCountDownBeforeRunValueUseCase: GetCountDownBeforeRunValueUseCase,
    @Timer private val timerServiceIntent: Intent,
    @LocationQualifier private val locationIntent: Intent
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()
    private val _raceIdState = MutableStateFlow<Long?>(null)
    val raceIdState = _raceIdState.asStateFlow()

    init {
        launchCountDown()
    }

    fun onRetry() = startRace()

    fun onPauseRaceClick() {
        _state.updateContent<RaceContent> { it.copy(state = RaceContent.State.PAUSE) }
        application.stopService(timerServiceIntent)
        application.stopService(locationIntent)
    }

    fun onResumeRaceClick() {
        _state.updateContent<RaceContent> { it.copy(state = RaceContent.State.RUNTIME) }
        application.startService(timerServiceIntent)
        application.startService(locationIntent)
    }

    fun onFinishClick() = executeWithState(_state) {
        _raceIdState.update { saveRaceUseCase.invoke() }
    }

    private fun launchCountDown() = executeWithState(_state) {
        val countDownValue = getCountDownBeforeRunValueUseCase.invoke()
        if (countDownValue != null) {
            object : CountDownTimer((countDownValue * 1000).toLong(), 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    _state.update { CountDownContent(millisUntilFinished + 1000) }
                }

                override fun onFinish() {
                    startRace()
                }
            }.run { start() }
        } else {
            startRace()
        }
    }

    private fun startRace() = executeWithState(_state) {
        val dateTime = DateTimeFormatter.DATE_TIME.format(LocalDateTime.now())
        saveCurrentDateUseCase.invoke(dateTime)
        _state.update {
            RaceContent(
                state = RaceContent.State.RUNTIME,
                date = dateTime,
                distance = Distance(0f),
                time = TimeConverter.convert(0)
            )
        }
        startService()
    }

    private fun startService() {
        application.startService(timerServiceIntent)
        application.startService(locationIntent)

        viewModelScope.launch {
            getCurrentTimeUseCase.invoke()
                .onEach { time ->
                    _state.updateContent<RaceContent> {
                        it.copy(time = TimeConverter.convert(time))
                    }
                }.collect()
        }
        var lastLocation: Location? = null
        viewModelScope.launch {
            getCurrentLocationUseCase.invoke()
                .onEach { location ->
                    if (location != null && lastLocation != null) {
                        val newDistance =
                            _state.getValue<RaceContent, Float> { it.distance.value }.plus(
                                location.distanceTo(lastLocation!!)
                            )
                        updateDistanceUseCase.invoke(newDistance)
                        _state.updateContent<RaceContent> {
                            it.copy(
                                distance = it.distance.copy(
                                    newDistance
                                )
                            )
                        }
                    }
                    lastLocation = location
                }.collect()
        }
    }
}
