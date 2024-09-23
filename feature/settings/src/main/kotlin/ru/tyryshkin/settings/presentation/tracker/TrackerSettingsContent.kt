package ru.tyryshkin.settings.presentation.tracker

import ru.tyryshkin.core.presentation.State
import ru.tyryshkin.settings.presentation.tracker.model.CountDownForSelect

data class TrackerSettingsContent(
    val countDownForSelect: CountDownForSelect,
    val bottomSheet: BottomSheet = BottomSheet.Hidden
) : State.Content() {

    sealed class BottomSheet {
        data object Hidden : BottomSheet()
        data object CountDown : BottomSheet()
    }
}
