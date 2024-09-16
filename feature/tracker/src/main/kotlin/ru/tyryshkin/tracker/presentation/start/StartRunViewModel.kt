package ru.tyryshkin.tracker.presentation.start

import android.app.Application
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import ru.tyryshkin.core.informer.snackbar.SnackbarController
import ru.tyryshkin.core.informer.snackbar.SnackbarStyle
import ru.tyryshkin.core.location.RequestPermissionResult
import ru.tyryshkin.core.location.areLocationPermissionsGranted
import ru.tyryshkin.core.presentation.State
import ru.tyryshkin.core.presentation.executeAndUpdateWithState
import ru.tyryshkin.core.presentation.executeWithState
import ru.tyryshkin.tracker.R
import ru.tyryshkin.tracker.domain.repository.PermissionRepository
import ru.tyryshkin.tracker.service.LocationClient
import javax.inject.Inject

@HiltViewModel
class StartRunViewModel @Inject constructor(
    private val application: Application,
    private val locationClient: LocationClient,
    private val snackbarController: SnackbarController,
    private val permissionRepository: PermissionRepository
) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()
    private val _showRequestLocationPermissionsState = MutableStateFlow(false)
    val needToRequestLocationPermissionsState = _showRequestLocationPermissionsState.asStateFlow()

    init {
        checkPermissions()
    }

    fun onResume() = checkPermissions()

    fun onRetryClick() {
        loadCurrentLocation()
    }

    fun onPermissionResult(result: RequestPermissionResult) = executeWithState(_state) {
        when (result) {
            RequestPermissionResult.GRANTED -> {
                _showRequestLocationPermissionsState.value = false
                loadCurrentLocation()
            }

            RequestPermissionResult.DENIED -> {
                _state.update { StartContent.NoLocationPermissionError }
            }
        }
    }

    private fun checkPermissions() = executeWithState(_state) {
        val isGranted = application.areLocationPermissionsGranted()
        if (!isGranted) {
            _state.update { StartContent.Empty }
            if (!permissionRepository.snackbarShown.first()) {
                snackbarController.show(
                    message = R.string.location_permission_hint,
                    style = SnackbarStyle.INFO,
                    actionLabel = R.string.location_permission_hint_action,
                    onActionClick = {
                        _showRequestLocationPermissionsState.value = true
                    }
                )
            } else {
                _state.update { StartContent.NoLocationPermissionError }
            }
        } else {
            loadCurrentLocation()
        }
    }

    private fun loadCurrentLocation() = locationClient.getCurrentLocation { location ->
        executeAndUpdateWithState(_state) {
            StartContent.Start(
                currentLocation = LatLng(
                    location.latitude,
                    location.longitude
                )
            )
        }
    }
}
