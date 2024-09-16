package ru.tyryshkin.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

fun ViewModel.executeAndUpdateWithState(
    state: MutableStateFlow<State>,
    function: suspend (MutableStateFlow<State>) -> State
) = viewModelScope.launch {
    val previousState = MutableStateFlow(state.value)
    state.update { State.Loading }
    runCatching {
        state.update { function(previousState) }
    }.getOrElse { throwable ->
        val exception = throwable as? Exception
        if (exception == null) {
            Timber.e(throwable)
        } else {
            state.update { State.Error(exception) }
        }
    }
}

fun ViewModel.executeWithState(
    state: MutableStateFlow<State>,
    function: suspend (MutableStateFlow<State>) -> Unit
) {
    executeAndUpdateWithState(state) {
        function(state)
        state.value
    }
}
