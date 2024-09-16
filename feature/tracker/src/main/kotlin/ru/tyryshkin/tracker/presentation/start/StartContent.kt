package ru.tyryshkin.tracker.presentation.start

import com.google.android.gms.maps.model.LatLng
import ru.tyryshkin.core.presentation.State

sealed class StartContent : State.Content() {
    data object Empty : StartContent()
    data object NoLocationPermissionError : StartContent()
    data class Start(val currentLocation: LatLng) : StartContent()
}
