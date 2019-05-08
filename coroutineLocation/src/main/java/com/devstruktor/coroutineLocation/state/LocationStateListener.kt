package com.devstruktor.coroutineLocation.state

typealias LocationStateListenerL = (LocationState) -> Unit

interface LocationStateListener {
    fun onNewState(state: LocationState)
}

