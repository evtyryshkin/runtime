package ru.tyryshkin.core.informer.snackbar

import androidx.compose.material3.SnackbarDuration

data class SnackbarInfo(
    val message: Message,
    val style: SnackbarStyle?,
    val withDismissAction: Boolean,
    val duration: SnackbarDuration,
    val actionLabel: Label?,
    val onActionClick: (() -> Unit)?
)
