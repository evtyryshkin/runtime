package ru.tyryshkin.profile.presentation.race

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import ru.tyryshkin.core.informer.error.ErrorHandler
import ru.tyryshkin.coreui.complex.PolylineMap
import ru.tyryshkin.coreui.complex.RaceInfoWidgets
import ru.tyryshkin.coreui.component.RScaffold
import ru.tyryshkin.coreui.component.RTopAppBar
import ru.tyryshkin.profile.R

@Composable
fun RaceDescriptionScreen(
    errorHandler: ErrorHandler,
    vm: RaceDescriptionViewModel,
    onNavigateToBack: () -> Unit
) {
    val state = vm.state.collectAsState().value

    RScaffold<RaceDescriptionStateContent>(
        state = state,
        errorHandler = errorHandler,
        onRetryClick = vm::onRetry,
        topBar = {
            RTopAppBar(
                title = stringResource(R.string.race_description_title),
                onBackClick = onNavigateToBack
            )
        }
    ) { finishContent ->
        Content(content = finishContent)
    }
}

@Composable
private fun Content(content: RaceDescriptionStateContent) {
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
