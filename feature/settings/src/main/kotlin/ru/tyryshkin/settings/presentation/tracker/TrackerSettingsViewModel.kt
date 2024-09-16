package ru.tyryshkin.settings.presentation.tracker

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.tyryshkin.core.presentation.State
import ru.tyryshkin.core.presentation.executeAndUpdateWithState
import ru.tyryshkin.core.presentation.updateContent
import ru.tyryshkin.settings.domain.usecase.GetCountDownBeforeRunValueUseCase
import ru.tyryshkin.settings.domain.usecase.GetCountDownValuesUseCase
import ru.tyryshkin.settings.domain.usecase.SetCountDownBeforeRunValueUseCase
import ru.tyryshkin.settings.presentation.tracker.model.CountDownForSelect
import javax.inject.Inject

@HiltViewModel
class TrackerSettingsViewModel @Inject constructor(
    private val getCountDownBeforeRunValueUseCase: GetCountDownBeforeRunValueUseCase,
    private val setCountDownBeforeRunValueUseCase: SetCountDownBeforeRunValueUseCase,
    private val getCountDownValuesUseCase: GetCountDownValuesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State> = _state.asStateFlow()

    init {
        loadData()
    }

    fun onRetry() = loadData()

    fun onCountDownSettingClick() = _state.updateContent<TrackerSettingsContent> {
        it.copy(bottomSheet = TrackerSettingsContent.BottomSheet.CountDown)
    }

    fun onDismissBottomSheet() = _state.updateContent<TrackerSettingsContent> {
        it.copy(bottomSheet = TrackerSettingsContent.BottomSheet.Hidden)
    }

    fun onSelectCountDown(newSelectedValue: Int?) {
        onDismissBottomSheet()
        executeAndUpdateWithState(_state) { state ->
            setCountDownBeforeRunValueUseCase.invoke(newSelectedValue)
            state.updateContent<TrackerSettingsContent> {
                it.copy(countDownForSelect = it.countDownForSelect.copy(selected = newSelectedValue))
            }
        }
    }

    private fun loadData() = executeAndUpdateWithState(_state) {
        val countDownValue = getCountDownBeforeRunValueUseCase.invoke()
        val defaultCountDownValues = getCountDownValuesUseCase.invoke()
        TrackerSettingsContent(
            countDownForSelect = CountDownForSelect(
                selected = countDownValue,
                values = defaultCountDownValues
            )
        )
    }
}
