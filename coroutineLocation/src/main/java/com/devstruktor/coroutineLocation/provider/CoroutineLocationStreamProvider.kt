package com.devstruktor.coroutineLocation.provider

import com.devstruktor.coroutineLocation.Logger
import com.devstruktor.coroutineLocation.session.LocationRequestSession
import com.devstruktor.coroutineLocation.session.SessionManager
import com.devstruktor.coroutineLocationCore.state.LocationState
import com.devstruktor.coroutineLocationCore.state.LocationStateListener
import com.devstruktor.coroutineLocationCore.state.LocationStateListenerL
import com.devstruktor.coroutineLocationSettings.CoroutineLocationSettings
import com.github.coneys.coroutinePermission.staticPermission.SuspendPermissions
import com.google.android.gms.location.LocationRequest
import kotlinx.coroutines.*


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

    return launch {
      CoroutineLocationSettings.strategy.test(coroutinePermissions, locationRequest, locationListener)
    }
}

