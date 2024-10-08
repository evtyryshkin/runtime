package ru.tyryshkin.tracker.presentation.finish

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import ru.tyryshkin.core.informer.error.ErrorHandler
import ru.tyryshkin.coreui.complex.PolylineMap
import ru.tyryshkin.coreui.complex.RaceInfoWidgets
import ru.tyryshkin.coreui.component.RBottomBar
import ru.tyryshkin.coreui.component.RButton
import ru.tyryshkin.coreui.component.RScaffold
import ru.tyryshkin.tracker.R

@Composable
fun FinishScreen(errorHandler: ErrorHandler, vm: FinishViewModel, onNavigateToMain: () -> Unit) {
    val state = vm.state.collectAsState().value

    RScaffold<FinishContent>(
        state = state,
        errorHandler = errorHandler,
        onRetryClick = vm::onRetry,
        onBackClick = {},
        bottomBar = {
            RBottomBar {
                RButton(
                    title = stringResource(R.string.navigate_to_main),
                    onClick = onNavigateToMain
                )
            }
        }
    ) { finishContent ->
        Content(content = finishContent)
    }
}

@Composable
private fun Content(content: FinishContent) {
    Column {
        RaceInfoWidgets(
            date = content.date,
            time = content.time,
            distance = content.distance
        )
        if (content.raceInfo.points.firstOrNull() != null) { // TODO Показать экран если никаких данных за пробежку нет
            PolylineMap(content.raceInfo)
        }
    }
}
