package com.devstruktor.coroutineLocation.provider

import com.devstruktor.coroutineLocation.state.LocationState


suspend fun getLastLocation(): LocationState {
    return InternalLastLocationProvider().getLastLocation()
}

fun isLocationEnabled() = InternalLastLocationProvider().isLocationEnabled()
