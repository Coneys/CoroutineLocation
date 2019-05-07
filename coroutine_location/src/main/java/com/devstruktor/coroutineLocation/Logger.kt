package com.devstruktor.coroutineLocation

import com.devstruktor.coroutineLocation.session.LocationRequestSession

object Logger {

    private fun pr(any: Any?) = println("CoroutineLocation   | $any")


    fun closingSession(locationRequest: LocationRequestSession) {
        pr("Closing session $locationRequest")

    }

    fun noPermissionSessionNotCreated() {
        pr("No permission. Session wasn't created")
    }

    fun creatingNewSession(newSession: LocationRequestSession) {
        pr("Creating new session $newSession")
    }

    fun addingListenersToSession(session: LocationRequestSession) {
        pr("Adding listener to session $session")
    }

    fun removingListenerFromSession(
        session: LocationRequestSession
    ) {
        pr("Removing listener from session $session")
    }

}