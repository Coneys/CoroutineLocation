package com.github.coneys.coroutineLocation

import com.github.coneys.coroutineLocation.state.LocationState
import com.github.coneys.coroutineLocation.state.LocationStateListener
import com.github.coneys.coroutinePermission.staticPermission.SuspendPermissions
import com.google.android.gms.location.LocationRequest

interface LocationStrategy {
    suspend fun startListening(
        coroutinePermissions: SuspendPermissions?,
        locationRequest: LocationRequest,
        locationListener: LocationStateListener
    )

    fun isLocationEnabled():Boolean

    suspend fun getLastUserLocation(): LocationState
}