package com.devstruktor.coroutineLocationCore.state

typealias LocationStateListenerL = (LocationState) -> Unit

interface LocationStateListener {
    fun onNewState(state: LocationState)
}

