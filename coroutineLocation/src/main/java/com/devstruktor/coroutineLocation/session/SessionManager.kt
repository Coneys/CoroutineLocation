package com.devstruktor.coroutineLocation.session

import android.app.Application
import android.content.Context
import com.devstruktor.coroutineLocation.initalizer.LocationInitProvider
import java.util.*

internal object SessionManager  {
    internal val appContext: Context by lazy { LocationInitProvider.appContext}
    internal val sessions: MutableList<LocationRequestSession> = ArrayList()

}