package com.github.coneys.coroutineLocation.provider

import com.github.coneys.coroutineLocation.CoroutineLocationSettings
import com.github.coneys.coroutineLocation.state.LocationState


suspend fun getLastLocation(): LocationState {
    return CoroutineLocationSettings.strategy.getLastUserLocation()
}

fun isLocationEnabled() = CoroutineLocationSettings.strategy.isLocationEnabled()


