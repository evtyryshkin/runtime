package ru.tyryshkin.core.informer.snackbar

import androidx.annotation.StringRes
import kotlinx.coroutines.channels.Channel

interface SnackbarController {
    val snackBarInfoChannel: Channel<SnackbarInfo>

    fun show(
        message: String,
        style: SnackbarStyle? = null,
        withDismissAction: Boolean = false
    )
    fun show(
        @StringRes message: Int,
        vararg args: Any,
        style: SnackbarStyle? = null,
        withDismissAction: Boolean = false
    )
    fun show(
        message: String,
        style: SnackbarStyle? = null,
        actionLabel: String,
        onActionClick: () -> Unit,
        withDismissAction: Boolean = false
    )
    fun show(
        @StringRes message: Int,
        vararg args: Any,
        style: SnackbarStyle? = null,
        actionLabel: String,
        onActionClick: () -> Unit,
        withDismissAction: Boolean = false
    )
    fun show(
        message: String,
        style: SnackbarStyle? = null,
        @StringRes actionLabel: Int,
        onActionClick: () -> Unit,
        withDismissAction: Boolean = false
    )
    fun show(
        @StringRes message: Int,
        vararg args: Any,
        style: SnackbarStyle? = null,
        @StringRes actionLabel: Int,
        onActionClick: () -> Unit,
        withDismissAction: Boolean = false
    )
}
