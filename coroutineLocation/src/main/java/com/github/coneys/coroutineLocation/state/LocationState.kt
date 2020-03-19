package com.github.coneys.coroutineLocation.state

import android.location.Location
import kotlinx.serialization.Serializable

@Serializable
sealed class LocationState {
    @Serializable
    data class Success(val location: @Serializable(with = LocationSerializer::class) Location) :
        LocationState()

    @Serializable
    object NoPermission : LocationState()

    @Serializable
    object LocationDisabled : LocationState()

    @Serializable
    object NoLocation : LocationState()
}