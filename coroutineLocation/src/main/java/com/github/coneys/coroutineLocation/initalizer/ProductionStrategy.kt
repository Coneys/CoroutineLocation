package com.github.coneys.coroutineLocation.initalizer

import com.github.coneys.coroutineLocation.LocationStrategy
import com.github.coneys.coroutineLocation.Logger
import com.github.coneys.coroutineLocation.provider.InternalLastLocationProvider
import com.github.coneys.coroutineLocation.session.LocationRequestSession
import com.github.coneys.coroutineLocation.session.SessionManager
import com.github.coneys.coroutineLocation.state.LocationState
import com.github.coneys.coroutineLocation.state.LocationStateListener
import com.github.coneys.coroutinePermission.staticPermission.SuspendPermissions
import com.google.android.gms.location.LocationRequest
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal object ProductionStrategy : LocationStrategy {
    override fun isLocationEnabled(): Boolean {
        return InternalLastLocationProvider().isLocationEnabled()
    }

    override suspend fun getLastUserLocation(): LocationState {
     return InternalLastLocationProvider().getLastLocation()
    }

    override suspend fun startListening(
        coroutinePermissions: SuspendPermissions?,
        locationRequest: LocationRequest,
        locationListener: LocationStateListener
    ) {

        val done = CompletableDeferred<Unit>()


        val hasPermission = coroutinePermissions?.requestLocation() ?: true
        if (!hasPermission) {
            locationListener.onNewState(LocationState.NoPermission)
            Logger.noPermissionSessionNotCreated()
            return
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