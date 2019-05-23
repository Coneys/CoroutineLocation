package com.devstruktor.coroutineLocation.provider

import com.devstruktor.coroutineLocation.Logger
import com.devstruktor.coroutineLocation.session.LocationRequestSession
import com.devstruktor.coroutineLocation.session.SessionManager
import com.devstruktor.coroutineLocation.state.LocationState
import com.devstruktor.coroutineLocation.state.LocationStateListener
import com.devstruktor.coroutineLocation.state.LocationStateListenerL
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
        val done = CompletableDeferred<Unit>()


        val hasPermission = coroutinePermissions?.requestLocation() ?: true
        if (!hasPermission) {
            locationListener.onNewState(LocationState.NoPermission)
            Logger.noPermissionSessionNotCreated()
            return@launch
        }

        val foundSession = SessionManager.sessions.find { it.request == locationRequest }

        val currentSession = if (foundSession == null) {
            val newSession = LocationRequestSession(
                locationRequest,
                SessionManager.appContext
            )

            SessionManager.sessions.add(newSession)
            Logger.creatingNewSession(newSession)
            newSession
        } else {
            Logger.addingListenersToSession(foundSession)
            foundSession
        }


        withContext(Dispatchers.Main) {
            currentSession.signUpForLocation(locationListener)
        }
        try {
            done.await()
        } finally {
            Logger.removingListenerFromSession(currentSession)
            currentSession.signOffFromLocation(locationListener)
        }

    }
}

