package ru.tyryshkin.runtime.informer.snackbar

import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarDuration
import kotlinx.coroutines.channels.Channel
import ru.tyryshkin.core.informer.snackbar.Label
import ru.tyryshkin.core.informer.snackbar.Message
import ru.tyryshkin.core.informer.snackbar.SnackbarController
import ru.tyryshkin.core.informer.snackbar.SnackbarInfo
import ru.tyryshkin.core.informer.snackbar.SnackbarStyle
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SnackbarControllerImpl @Inject constructor() : SnackbarController {

    private val _snackbarInfo = Channel<SnackbarInfo>(Channel.UNLIMITED)
    override val snackBarInfoChannel: Channel<SnackbarInfo> = _snackbarInfo

    override fun show(
        message: String,
        style: SnackbarStyle?,
        withDismissAction: Boolean
    ) {
        _snackbarInfo.trySend(
            SnackbarInfo(
                message = Message.MessageString(message),
                style = style,
                withDismissAction = withDismissAction,
                duration = if (withDismissAction) {
                    SnackbarDuration.Indefinite
                } else {
                    SnackbarDuration.Short
                },
                actionLabel = null,
                onActionClick = null
            )
        )
    }

    override fun show(
        @StringRes message: Int,
        vararg args: Any,
        style: SnackbarStyle?,
        withDismissAction: Boolean
    ) {
        _snackbarInfo.trySend(
            SnackbarInfo(
                message = Message.MessageRes(message, *args),
                style = style,
                withDismissAction = withDismissAction,
                duration = if (withDismissAction) {
                    SnackbarDuration.Indefinite
                } else {
                    SnackbarDuration.Short
                },
                actionLabel = null,
                onActionClick = null
            )
        )
    }

    override fun show(
        message: String,
        style: SnackbarStyle?,
        actionLabel: String,
        onActionClick: () -> Unit,
        withDismissAction: Boolean
    ) {
        _snackbarInfo.trySend(
            SnackbarInfo(
                Message.MessageString(message),
                style,
                withDismissAction,
                SnackbarDuration.Indefinite,
                Label.LabelString(actionLabel),
                onActionClick
            )
        )
    }

    override fun show(
        @StringRes message: Int,
        vararg args: Any,
        style: SnackbarStyle?,
        actionLabel: String,
        onActionClick: () -> Unit,
        withDismissAction: Boolean
    ) {
        _snackbarInfo.trySend(
            SnackbarInfo(
                Message.MessageRes(message, *args),
                style,
                withDismissAction,
                SnackbarDuration.Indefinite,
                Label.LabelString(actionLabel),
                onActionClick
            )
        )
    }
    override fun show(
        message: String,
        style: SnackbarStyle?,
        @StringRes actionLabel: Int,
        onActionClick: () -> Unit,
        withDismissAction: Boolean
    ) {
        _snackbarInfo.trySend(
            SnackbarInfo(
                Message.MessageString(message),
                style,
                withDismissAction,
                SnackbarDuration.Indefinite,
                Label.LabelRes(actionLabel),
                onActionClick
            )
        )
    }

    override fun show(
        @StringRes message: Int,
        vararg args: Any,
        style: SnackbarStyle?,
        @StringRes actionLabel: Int,
        onActionClick: () -> Unit,
        withDismissAction: Boolean
    ) {
        _snackbarInfo.trySend(
            SnackbarInfo(
                Message.MessageRes(message, *args),
                style,
                withDismissAction,
                SnackbarDuration.Indefinite,
                Label.LabelRes(actionLabel),
                onActionClick
            )
        )
    }
}
