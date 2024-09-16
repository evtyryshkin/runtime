package ru.tyryshkin.tracker.service

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import ru.tyryshkin.core.informer.error.DomainException
import ru.tyryshkin.core.location.areLocationPermissionsGranted
import ru.tyryshkin.tracker.R
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("MissingPermission")
class LocationClientImpl @Inject constructor(private val context: Context) : LocationClient {
    private val client = LocationServices.getFusedLocationProviderClient(context)
    private val accuracy = Priority.PRIORITY_HIGH_ACCURACY

    override fun getCurrentLocation(onSuccessListener: OnSuccessListener<Location>) {
        if (context.areLocationPermissionsGranted()) {
            client.getCurrentLocation(accuracy, CancellationTokenSource().token)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener { exception ->
                    Timber.e(exception)
                    throw DomainException.Default(context.getString(R.string.get_current_location_exception_message))
                }
        }
    }

    override fun getLocationUpdates(): Flow<Location> = callbackFlow {
        if (!context.areLocationPermissionsGranted()) {
            throw MissingLocationPermissionException()
        }

        val locationManager = ContextCompat.getSystemService(
            context,
            LocationManager::class.java
        )
        val isGpsEnabled =
            locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled =
            locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (isGpsEnabled == true || isNetworkEnabled == true) {
            val request = LocationRequest.Builder(accuracy, 3000)
                .setMaxUpdateDelayMillis(3000)
                .build()

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    result.locations.lastOrNull()?.let { location ->
                        launch { send(location) }
                    }
                }
            }

            client.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper()
            )

            awaitClose { client.removeLocationUpdates(locationCallback) }
        } else {
            throw GpsDisabledException()
        }
    }
}
