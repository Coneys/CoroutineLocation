package com.github.coneys.coroutineLocation.session

import android.content.Context
import com.github.coneys.coroutineLocation.initalizer.LocationInitProvider
import com.github.coneys.coroutineLocation.session.LocationRequestSession
import java.util.*
import kotlin.collections.ArrayList

internal object SessionManager  {

    @Synchronized
    fun addSession(newSession: LocationRequestSession) {
        mutableSessions.add(newSession)
    }

    @Synchronized
    fun removeSession(sessionToRemove: LocationRequestSession) {
        mutableSessions.remove(sessionToRemove)
    }

    internal val appContext: Context by lazy { LocationInitProvider.appContext}
    private val mutableSessions = ArrayList<LocationRequestSession>()
    internal val sessions: List<LocationRequestSession> =mutableSessions


}