package ru.tyryshkin.tracker.presentation.start

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import ru.tyryshkin.core.informer.error.ErrorHandler
import ru.tyryshkin.core.location.DEFAULT_ZOOM_VALUE
import ru.tyryshkin.core.location.RequestLocationPermission
import ru.tyryshkin.core.test.TestTag
import ru.tyryshkin.coreui.component.RButton
import ru.tyryshkin.coreui.component.RErrorPlaceholder
import ru.tyryshkin.coreui.component.RScaffold
import ru.tyryshkin.coreui.component.Shape
import ru.tyryshkin.tracker.R

@Composable
fun StartRunScreen(
    vm: StartRunViewModel,
    errorHandler: ErrorHandler,
    onNavigateToRace: () -> Unit
) {
    val state = vm.state.collectAsState().value
    val needToRequestLocationPermissionsState = vm.needToRequestLocationPermissionsState.collectAsState().value

    LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_RESUME) vm.onResume()
    }

    RScaffold<StartContent>(
        state = state,
        errorHandler = errorHandler,
        onRetryClick = vm::onRetryClick
    ) { content ->
        Content(content, onNavigateToRace)
    }

    if (needToRequestLocationPermissionsState) {
        RequestLocationPermission(onPermissionResult = vm::onPermissionResult)
    }
}

@Composable
private fun Content(content: StartContent, onNavigateToRace: () -> Unit) {
    when (content) {
        StartContent.Empty -> {}
        StartContent.NoLocationPermissionError -> NoLocationPermissionErrorContent()
        is StartContent.Start -> {
            StartContent(
                location = content.currentLocation,
                onNavigateToRace = onNavigateToRace
            )
        }
    }
}

@Composable
private fun StartContent(location: LatLng, onNavigateToRace: () -> Unit) {
    Box {
        Column {
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(location, DEFAULT_ZOOM_VALUE)
            }
            /*RMapStub(
                modifier = Modifier.fillMaxSize()
            )*/ // TODO
            GoogleMap(
                modifier = Modifier
                    .padding(16.dp)
                    .clip(shape = RoundedCornerShape(20.dp)),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                    mapToolbarEnabled = false,
                    scrollGesturesEnabled = false,
                    zoomControlsEnabled = false,
                    zoomGesturesEnabled = false,
                    rotationGesturesEnabled = false
                )
            ) {
                Marker(state = MarkerState(position = location))
            }
        }
        RButton(
            modifier = Modifier
                .padding(bottom = 100.dp)
                .align(Alignment.BottomCenter)
                .testTag(TestTag.Tracker.BUTTON_START),
            title = stringResource(R.string.button_start),
            icon = ru.tyryshkin.coreui.R.drawable.ic_start,
            onClick = onNavigateToRace,
            shape = Shape.CIRCLE,
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 20.dp)
        )
    }
}

@Composable
private fun NoLocationPermissionErrorContent() {
    RErrorPlaceholder(
        title = stringResource(R.string.error_content_permission_denied),
        painter = painterResource(ru.tyryshkin.coreui.R.drawable.error_no_location),
        onActionLabel = stringResource(R.string.error_content_permission_denied_button)
    )
}
