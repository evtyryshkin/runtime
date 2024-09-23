package ru.tyryshkin.tracker.presentation.race

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tyryshkin.core.informer.error.ErrorHandler
import ru.tyryshkin.core.presentation.State
import ru.tyryshkin.core.test.TestTag
import ru.tyryshkin.coreui.complex.RaceInfoWidgets
import ru.tyryshkin.coreui.component.RBottomBar
import ru.tyryshkin.coreui.component.RButton
import ru.tyryshkin.coreui.component.RScaffold
import ru.tyryshkin.coreui.component.RSpacer
import ru.tyryshkin.tracker.R

@Composable
fun RaceScreen(errorHandler: ErrorHandler, vm: RaceViewModel, onNavigateToFinish: (Long) -> Unit) {
    val state = vm.state.collectAsState().value
    val raceIdState by vm.raceIdState.collectAsState()

    LaunchedEffect(raceIdState) {
        raceIdState?.let { onNavigateToFinish.invoke(it) }
    }

    RScaffold<State.Content>(
        state = state,
        errorHandler = errorHandler,
        onRetryClick = vm::onRetry,
        onBackClick = {},
        bottomBar = {
            val raceContent = state as? RaceContent
            if (raceContent != null) {
                BottomBar(
                    state = raceContent.state,
                    onPauseRaceClick = vm::onPauseRaceClick,
                    onResumeRaceClick = vm::onResumeRaceClick,
                    onFinishClick = vm::onFinishClick
                )
            }
        }
    ) { stateContent ->
        if (stateContent is RaceContent) {
            RaceInfoWidgets(
                date = stateContent.date,
                time = stateContent.time,
                distance = stateContent.distance
            )
        } else if (stateContent is CountDownContent) {
            CountDownContent(
                value = stateContent.valueInSeconds
            )
        }
    }
}

@Composable
fun BottomBar(
    state: RaceContent.State,
    onPauseRaceClick: () -> Unit,
    onResumeRaceClick: () -> Unit,
    onFinishClick: () -> Unit
) {
    RBottomBar {
        when (state) {
            RaceContent.State.RUNTIME -> {
                RButton(
                    title = stringResource(R.string.button_stop),
                    onClick = onPauseRaceClick
                )
            }

            RaceContent.State.PAUSE -> {
                RButton(
                    title = stringResource(R.string.button_resume),
                    onClick = onResumeRaceClick
                )
                RSpacer(height = 16.dp)
                RButton(
                    title = stringResource(R.string.button_finish),
                    onClick = onFinishClick
                )
            }
        }
    }
}

@Composable
private fun CountDownContent(value: Int) {
    val scale = remember(value) { Animatable(initialValue = 1f) }
    LaunchedEffect(value) {
        scale.animateTo(
            targetValue = 1.5f,
            animationSpec = tween(durationMillis = 1000)
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text( // TODO Поменять на RText
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer {
                    scaleX = scale.value
                    scaleY = scale.value
                }
                .testTag(TestTag.Tracker.Race.TEXT_COUNT_DOWN),
            text = value.toString(),
            fontSize = 160.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
