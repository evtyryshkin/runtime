package ru.tyryshkin.coreui.complex

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.CustomCap
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import ru.tyryshkin.core.domain.entity.RaceInfo
import ru.tyryshkin.core.location.DEFAULT_ZOOM_VALUE
import ru.tyryshkin.coreui.bitmap.CircleType
import ru.tyryshkin.coreui.bitmap.getCircleBitmap

@Composable
fun PolylineMap(raceInfo: RaceInfo) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            raceInfo.points.first(), // TODO Научиться размещать трек в середине карты
            DEFAULT_ZOOM_VALUE
        )
    }
    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .clip(shape = RoundedCornerShape(20.dp)),
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
