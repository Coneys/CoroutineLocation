package com.devstruktor.coroutineLocationTest

import com.devstruktor.coroutineLocationCore.LocationStrategy
import com.devstruktor.coroutineLocationCore.state.LocationStateListener
import com.github.coneys.coroutinePermission.staticPermission.SuspendPermissions
import com.google.android.gms.location.LocationRequest

internal class TestStrategy(val testProvider: TestProvider) : LocationStrategy {


    override suspend fun test(
        coroutinePermissions: SuspendPermissions?,
        locationRequest: LocationRequest,
        locationListener: LocationStateListener
    ) {
        testProvider.registerRequest(locationRequest, locationListener)
    }

}