package com.github.coneys.coroutineLocation.state

import android.location.Location

sealed class LocationState {
    data class Success(val location: Location) : LocationState()
    object NoPermission : LocationState()
    object LocationDisabled : LocationState()
    object NoLocation : LocationState()
}