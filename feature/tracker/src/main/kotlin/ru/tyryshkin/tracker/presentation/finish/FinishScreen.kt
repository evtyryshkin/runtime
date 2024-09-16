package ru.tyryshkin.tracker.presentation.finish

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.CustomCap
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import ru.tyryshkin.core.domain.entity.RaceInfo
import ru.tyryshkin.core.informer.error.ErrorHandler
import ru.tyryshkin.core.location.DEFAULT_ZOOM_VALUE
import ru.tyryshkin.coreui.bitmap.CircleType
import ru.tyryshkin.coreui.bitmap.getCircleBitmap
import ru.tyryshkin.coreui.component.RBottomBar
import ru.tyryshkin.coreui.component.RButton
import ru.tyryshkin.coreui.component.RScaffold
import ru.tyryshkin.tracker.R
import ru.tyryshkin.tracker.presentation.shared.RaceInfoContent

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
        RaceInfoContent(
            date = content.date,
            time = content.time,
            distance = content.distance
        )
        if (content.raceInfo.points.firstOrNull() != null) { // TODO Показать экран если никаких данных за пробежку нет
            MapContent(content.raceInfo)
        }
    }
}

@Composable
private fun MapContent(raceInfo: RaceInfo) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            raceInfo.points.first(), // TODO Научиться размещать трек в середине карты
            DEFAULT_ZOOM_VALUE
        )
    }
    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        cameraPositionState = cameraPositionState
    ) {
        Polyline(
            points = raceInfo.points,
            color = MaterialTheme.colorScheme.primary,
            startCap = CustomCap(
                BitmapDescriptorFactory.fromBitmap(getCircleBitmap(CircleType.START))
            ),
            endCap = CustomCap(
                BitmapDescriptorFactory.fromBitmap(getCircleBitmap(CircleType.FINISH))
            )
        )
    }
}
