package com.devstruktor.coroutineLocation.state

import com.devstruktor.coroutinelocationprovider.locationProviderExample.state.LocationState

typealias LocationStateListenerL = (LocationState) -> Unit

interface LocationStateListener {
    fun onNewState(state: LocationState)
}

