package com.devstruktor.coroutineLocationTest

import com.devstruktor.coroutineLocationCore.state.LocationState
import com.devstruktor.coroutineLocationCore.state.LocationStateListener
import com.google.android.gms.location.LocationRequest

class TestProvider internal constructor() {

    private val map = HashMap<LocationRequest, LocationStateListener>()
    fun registerRequest(locationRequest: LocationRequest, locationListener: LocationStateListener) {
        map[locationRequest] = locationListener
    }

    fun pushStateForRequest(locationRequest: LocationRequest, state: LocationState) {
        map.filter { it.key == locationRequest }.values.forEach {
            it.onNewState(state)
        }
    }

    fun pushStateForAll(state: LocationState) {
        map.values.forEach {
            it.onNewState(state)
        }
    }
}