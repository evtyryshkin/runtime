package ru.tyryshkin.tracker.service

import android.location.Location
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getLocationUpdates(): Flow<Location>
    fun getCurrentLocation(onSuccessListener: OnSuccessListener<Location>)
}

class MissingLocationPermissionException : Exception()
class GpsDisabledException : Exception()
