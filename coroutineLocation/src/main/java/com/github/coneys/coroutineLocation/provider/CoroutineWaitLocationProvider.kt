package com.github.coneys.coroutineLocation.provider

import com.github.coneys.coroutineLocation.Logger
import com.github.coneys.coroutineLocation.state.LocationState
import com.github.coneys.coroutinePermission.staticPermission.SuspendPermissions
import com.google.android.gms.location.LocationRequest
import kotlinx.coroutines.*


suspend fun waitForLocation(
    locationRequest: LocationRequest,
    coroutinePermissions: SuspendPermissions?,
    waitTimeInSeconds: Int
): LocationState = coroutineScope {

    Logger.waitingForLocation(waitTimeInSeconds)

    val done = CompletableDeferred<LocationState>()

    var lastState: LocationState = LocationState.NoLocation

    observeLocation(locationRequest, coroutinePermissions) {

        if (it is LocationState.Success) {
            Logger.waitingCancelledGotSuccess()
            done.complete(it)
        }
        lastState = it

    }

    async { delay(waitTimeInSeconds * 1000L) }.invokeOnCompletion {
        if (it !is CancellationException) {
            Logger.waitingFinished(waitTimeInSeconds, lastState)
            done.complete(lastState)
        }
    }


    done.await().also { cancel() }
}
