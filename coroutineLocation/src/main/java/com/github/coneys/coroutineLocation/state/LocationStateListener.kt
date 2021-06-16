package com.github.coneys.coroutineLocation.state

typealias LocationStateListenerL = (LocationState) -> Unit

interface LocationStateListener {
    fun onNewState(state: LocationState)
}

