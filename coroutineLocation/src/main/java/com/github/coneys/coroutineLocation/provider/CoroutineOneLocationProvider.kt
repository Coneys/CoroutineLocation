package com.github.coneys.coroutineLocation.provider

import com.github.coneys.coroutineLocation.CoroutineLocationSettings
import com.github.coneys.coroutineLocation.state.LocationState


internal var cachedLocation: LocationState = LocationState.NoLocation

fun getLastCachedLocation(): LocationState {
    return cachedLocation
}

suspend fun getLastLocation(): LocationState {
    return CoroutineLocationSettings.strategy.getLastUserLocation().also { cachedLocation = it }
}

fun isLocationEnabled() = CoroutineLocationSettings.strategy.isLocationEnabled()


