package ru.tyryshkin.runtime.informer.snackbar

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import ru.tyryshkin.core.informer.snackbar.SnackbarController
import ru.tyryshkin.core.informer.snackbar.getString
import ru.tyryshkin.core.informer.snackbar.nextSnackbar
import ru.tyryshkin.core.informer.snackbar.rememberSnackbarInfo
import ru.tyryshkin.core.informer.snackbar.rememberSnackbarState
import ru.tyryshkin.coreui.component.RSnackbar

@Composable
fun AppSnackbarHost(snackbarController: SnackbarController) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarState = rememberSnackbarState()
    var currentSnackbarInfo by rememberSnackbarInfo()

    LaunchedEffect(snackbarState.value) {
        delay(200)
        currentSnackbarInfo = snackbarController.snackBarInfoChannel.receive()
        currentSnackbarInfo?.run {
            snackbarHostState.showSnackbar(
                message = message.getString(context),
                actionLabel = actionLabel?.getString(context),
                withDismissAction = withDismissAction,
                duration = duration
            )
        }
    }

    LaunchedEffect(snackbarHostState.currentSnackbarData) {
        if (snackbarHostState.currentSnackbarData == null) snackbarState.nextSnackbar()
    }

    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { data ->
            RSnackbar(
                message = data.visuals.message,
                style = currentSnackbarInfo?.style,
                duration = data.visuals.duration,
                withDismissAction = data.visuals.withDismissAction,
                actionLabel = data.visuals.actionLabel,
                onActionClick = currentSnackbarInfo?.onActionClick,
                onDismiss = {
                    data.dismiss()
                    snackbarState.nextSnackbar()
                }
            )
        }
    )
}
