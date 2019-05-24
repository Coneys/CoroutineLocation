package com.github.coneys.coroutineLocation.session

import android.content.Context
import com.github.coneys.coroutineLocation.initalizer.LocationInitProvider
import com.github.coneys.coroutineLocation.session.LocationRequestSession
import java.util.*

internal object SessionManager  {
    internal val appContext: Context by lazy { LocationInitProvider.appContext}
    internal val sessions: MutableList<LocationRequestSession> = ArrayList()

}