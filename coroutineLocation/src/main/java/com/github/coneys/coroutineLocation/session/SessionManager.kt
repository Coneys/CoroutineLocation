package com.github.coneys.coroutineLocation.session

import android.content.Context
import com.github.coneys.coroutineLocation.initalizer.LocationInitProvider
import com.github.coneys.coroutineLocation.session.LocationRequestSession
import com.google.android.gms.location.LocationRequest
import java.util.*
import kotlin.collections.ArrayList

internal object SessionManager  {

    @Synchronized
    fun addSession(newSession: LocationRequestSession) {
        sessions.add(newSession)
    }

    @Synchronized
    fun removeSession(sessionToRemove: LocationRequestSession) {
        sessions.remove(sessionToRemove)
    }

    @Synchronized
    fun findSession(locationRequest: LocationRequest): LocationRequestSession? {
        return sessions.find { it.request == locationRequest }
    }

    internal val appContext: Context by lazy { LocationInitProvider.appContext}
    private val sessions = ArrayList<LocationRequestSession>()


}