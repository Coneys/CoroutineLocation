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

    val job = observeLocation(locationRequest, coroutinePermissions) {

        if (!done.isActive) return@observeLocation

        if (it is LocationState.Success) {
            Logger.waitingCancelledGotSuccess()
            done.complete(it)
        }
        lastState = it

    }

    val asyncJob = async { delay(waitTimeInSeconds * 1000L) }.also {
        it.invokeOnCompletion {
            if (it !is CancellationException) {
                Logger.waitingFinished(waitTimeInSeconds, lastState)
                done.complete(lastState)
            }
        }
    }


    done.await().also {
        job.cancel()
        asyncJob.cancel()
    }
}


