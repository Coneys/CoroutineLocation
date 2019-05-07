package com.devstruktor.coroutineLocation.provider

import com.devstruktor.coroutinelocationprovider.locationProviderExample.state.LocationState


suspend fun getLastLocation(): LocationState {
    return InternalLastLocationProvider().getLastLocation()
}

fun isLocationEnabled() = InternalLastLocationProvider().isLocationEnabled()
