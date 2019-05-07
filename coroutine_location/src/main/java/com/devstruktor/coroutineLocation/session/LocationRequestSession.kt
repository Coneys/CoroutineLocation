package com.devstruktor.coroutineLocation.session

import android.content.Context
import com.devstruktor.coroutineLocation.Logger
import com.devstruktor.coroutineLocation.toLocationState
import com.devstruktor.coroutinelocationprovider.locationProviderExample.state.LocationState
import com.devstruktor.coroutineLocation.state.LocationStateListener

import com.google.android.gms.location.*

class LocationRequestSession(val request: LocationRequest, context: Context) {
    private val locationProvider: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val listeners = ArrayList<LocationStateListener>()
    private val callback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            listeners.forEach { it.onNewState(result.toLocationState()) }
        }

        override fun onLocationAvailability(result: LocationAvailability) {
            if (!result.isLocationAvailable)
                listeners.forEach { it.onNewState(LocationState.LocationDisabled) }
        }
    }

    @Synchronized
    fun signUpForLocation(listener: LocationStateListener) {
        listeners.add(listener)
        if (listeners.size == 1) {
            try {
                locationProvider.requestLocationUpdates(request, callback, null)
            } catch (s: SecurityException) {
                listeners.forEach { it.onNewState(LocationState.NoPermission) }
            }
        }
    }

    @Synchronized
    fun signOffFromLocation(listener: LocationStateListener) {
        listeners.remove(listener)
        if (listeners.isEmpty()) {
            Logger.closingSession(this)
            locationProvider.removeLocationUpdates(callback)
            SessionManager.sessions.remove(this)
        }
    }

    override fun toString(): String {
        return "LocationSession $request"
    }

}