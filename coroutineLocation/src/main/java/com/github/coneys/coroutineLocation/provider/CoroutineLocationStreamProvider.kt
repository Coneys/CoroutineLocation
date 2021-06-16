package com.github.coneys.coroutineLocation.provider

import com.github.coneys.coroutineLocation.CoroutineLocationSettings
import com.github.coneys.coroutineLocation.initalizer.LocationInitProvider
import com.github.coneys.coroutineLocation.state.LocationState
import com.github.coneys.coroutineLocation.state.LocationStateListener
import com.github.coneys.coroutineLocation.state.LocationStateListenerL
import com.github.coneys.coroutinePermission.staticPermission.SuspendPermissions
import com.google.android.gms.location.LocationRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


fun CoroutineScope.observeLocation(
    locationRequest: LocationRequest,
    coroutinePermissions: SuspendPermissions?,
    locationListener: LocationStateListenerL
): Job {

    val listener = object :
        LocationStateListener {
        override fun onNewState(state: LocationState) {
            locationListener.invoke(state)
        }
    }

    return observeLocation(locationRequest, coroutinePermissions, listener)
}

fun CoroutineScope.observeLocation(
    locationRequest: LocationRequest,
    coroutinePermissions: SuspendPermissions?,
    locationListener: LocationStateListener
): Job {

    val interceptListener = object : LocationStateListener {
        override fun onNewState(state: LocationState) {
            locationListener.onNewState(state)
            cachedLocation = state
            LocationInitProvider.savedLocation.saveLocation(state)
        }
    }

    return launch {
        CoroutineLocationSettings.strategy.startListening(
            coroutinePermissions,
            locationRequest,
            interceptListener
        )
    }
}

