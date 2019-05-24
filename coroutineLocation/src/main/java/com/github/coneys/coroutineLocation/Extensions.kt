package com.github.coneys.coroutineLocation

import android.location.Location
import com.github.coneys.coroutineLocation.state.LocationState
import com.google.android.gms.location.LocationResult
import com.google.android.gms.tasks.Task

internal fun Task<Location>.toLocationState(): LocationState {
    val result = this.result
    return if (this.isSuccessful && result != null) {
        LocationState.Success(result)
    } else
        LocationState.NoLocation

}

internal fun LocationResult.toLocationState(): LocationState {
    return LocationState.Success(this.lastLocation)
}