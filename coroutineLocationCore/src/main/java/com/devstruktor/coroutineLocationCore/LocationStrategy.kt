package com.devstruktor.coroutineLocationCore

import com.devstruktor.coroutineLocationCore.state.LocationStateListener
import com.github.coneys.coroutinePermission.staticPermission.SuspendPermissions
import com.google.android.gms.location.LocationRequest

interface LocationStrategy {
    suspend fun test(
        coroutinePermissions: SuspendPermissions?,
        locationRequest: LocationRequest,
        locationListener: LocationStateListener
    )
}