package ru.tyryshkin.core.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@Composable
fun RequestLocationPermission(
    onPermissionResult: (RequestPermissionResult) -> Unit
) {
    val permissions = buildList {
        add(Manifest.permission.ACCESS_COARSE_LOCATION)
        add(Manifest.permission.ACCESS_FINE_LOCATION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            add(Manifest.permission.FOREGROUND_SERVICE_LOCATION)
        }
    }

    RequestPermission(
        permissions = permissions,
        onPermissionResult = onPermissionResult
    )
}

fun Context.areLocationPermissionsGranted(): Boolean =
    ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED &&
        ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED &&
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.FOREGROUND_SERVICE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun RequestPermission(
    permissions: List<String>,
    onPermissionResult: (RequestPermissionResult) -> Unit
) {
    val permissionState = rememberMultiplePermissionsState(permissions) { result ->
        if (result.values.all { isGranted -> isGranted }) {
            onPermissionResult(RequestPermissionResult.GRANTED)
        } else {
            onPermissionResult(RequestPermissionResult.DENIED)
        }
    }

    LaunchedEffect(permissionState) {
        val permissionsToRequest = permissionState.permissions.filter {
            !it.status.isGranted
        }

        if (permissionsToRequest.isNotEmpty()) permissionState.launchMultiplePermissionRequest()
    }
}

enum class RequestPermissionResult {
    GRANTED, DENIED
}
