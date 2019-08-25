package com.github.coneys.coroutineLocation

import com.github.coneys.coroutineLocation.session.LocationRequestSession
import com.github.coneys.coroutineLocation.state.LocationState

internal object Logger {

    var isEnabled = true

    private fun pr(any: Any?) {
        if (isEnabled)
            println("CoroutineLocation   | $any")
    }


    fun waitingForLocation(waitingTimeInSeconds: Int) {
        pr("Waiting $waitingTimeInSeconds seconds for location")
    }

    fun waitingCancelledGotSuccess() {
        pr("Waiting cancelled, got success location")
    }

    fun waitingFinished(waitingTimeInSeconds: Int, lastState: LocationState) {
        pr("Waiting for $waitingTimeInSeconds seconds finished. Last state $lastState")
    }

    internal fun closingSession(locationRequest: LocationRequestSession) {
        pr("Closing session $locationRequest")

    }

    internal fun noPermissionSessionNotCreated() {
        pr("No permission. Session wasn't created")
    }

    internal fun creatingNewSession(newSession: LocationRequestSession) {
        pr("Creating new session $newSession")
    }

    internal fun addingListenersToSession(session: LocationRequestSession) {
        pr("Adding listener to session $session")
    }

    internal fun removingListenerFromSession(
        session: LocationRequestSession
    ) {
        pr("Removing listener from session $session")
    }

}