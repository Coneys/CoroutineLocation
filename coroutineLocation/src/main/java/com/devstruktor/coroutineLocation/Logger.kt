package com.devstruktor.coroutineLocation

import com.devstruktor.coroutineLocation.session.LocationRequestSession

object Logger {

    var isEnabled = true

    private fun pr(any: Any?) {
        if (isEnabled)
            println("CoroutineLocation   | $any")
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