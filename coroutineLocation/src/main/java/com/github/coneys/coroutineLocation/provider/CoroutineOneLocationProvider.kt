package com.github.coneys.coroutineLocation.provider

import com.github.coneys.coroutineLocation.CoroutineLocationSettings
import com.github.coneys.coroutineLocation.state.LocationState
import com.github.coneys.coroutinePermission.staticPermission.SuspendPermissions


internal var cachedLocation: LocationState = LocationState.NoLocation

fun getLastCachedLocation(): LocationState {
    return cachedLocation
}

suspend fun getLastLocation(permissions: SuspendPermissions): LocationState {
    val location = permissions.requestLocation()
    return if (location)
        CoroutineLocationSettings.strategy.getLastUserLocation().also { cachedLocation = it }
    else LocationState.NoPermission
}

fun isLocationEnabled() = CoroutineLocationSettings.strategy.isLocationEnabled()


