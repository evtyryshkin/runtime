package ru.tyryshkin.core.informer.snackbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun rememberSnackbarState() = remember { mutableStateOf(true) }

@Composable
fun rememberSnackbarInfo() = remember { mutableStateOf<SnackbarInfo?>(null) }

fun MutableState<Boolean>.nextSnackbar() {
    value = !value
}
