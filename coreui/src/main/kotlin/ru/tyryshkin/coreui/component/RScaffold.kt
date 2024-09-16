package ru.tyryshkin.coreui.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.tyryshkin.core.informer.error.ErrorHandler
import ru.tyryshkin.core.presentation.State
import ru.tyryshkin.coreui.R
import timber.log.Timber

@Suppress("UNCHECKED_CAST")
@Composable
fun <T : State.Content> RScaffold(
    state: State,
    errorHandler: ErrorHandler,
    modifier: Modifier = Modifier,
    onRetryClick: (() -> Unit)? = null,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    onBackClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.(T) -> Unit
) {
    if (onBackClick != null) BackHandler(onBack = onBackClick)
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar
    ) { paddings ->
        Box(Modifier.padding(paddings)) {
            when (state) {
                is State.Loading -> RFullScreenLoader()

                is State.Content -> {
                    (state as? T)?.let {
                        Column { content(state) }
                    } ?: Timber.e(stringResource(R.string.class_cast_exception, state::class.java.name))
                }

                is State.Error -> {
                    errorHandler.OnError(exception = state.exception, onRetryClick = onRetryClick)
                }
            }
        }
    }
}
