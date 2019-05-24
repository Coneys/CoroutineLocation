package com.github.coneys.coroutineLocation.state

import com.github.coneys.coroutineLocation.state.LocationState

typealias LocationStateListenerL = (LocationState) -> Unit

interface LocationStateListener {
    fun onNewState(state: LocationState)
}

