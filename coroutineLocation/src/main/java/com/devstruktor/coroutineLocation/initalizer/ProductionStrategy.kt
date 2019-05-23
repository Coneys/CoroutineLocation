package com.devstruktor.coroutineLocation.initalizer

import com.devstruktor.coroutineLocation.Logger
import com.devstruktor.coroutineLocation.session.LocationRequestSession
import com.devstruktor.coroutineLocation.session.SessionManager
import com.devstruktor.coroutineLocationCore.LocationStrategy
import com.devstruktor.coroutineLocationCore.state.LocationState
import com.devstruktor.coroutineLocationCore.state.LocationStateListener
import com.github.coneys.coroutinePermission.staticPermission.SuspendPermissions
import com.google.android.gms.location.LocationRequest
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal object ProductionStrategy : LocationStrategy {
    override suspend fun test(
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