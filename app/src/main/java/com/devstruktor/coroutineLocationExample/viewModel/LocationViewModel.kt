package com.devstruktor.coroutineLocationExample.viewModel

import androidx.lifecycle.ViewModel
import com.github.coneys.coroutineLocation.provider.waitForLocation
import com.github.coneys.coroutinePermission.CoroutinePermissions
import com.google.android.gms.location.LocationRequest
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class LocationViewModel : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Main + Job()

    init {

        val request = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(6000)
            .setFastestInterval(1000)


    }

    override fun onCleared() {
        super.onCleared()
        println("Called on cleared!!")
        cancel()

    }

    fun start() {
        println("start")
        waitForLocationExample()

    }

    private fun waitForLocationExample() {


        val request = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(6000)
            .setFastestInterval(1000)

        launch {
            val location = waitForLocation(request, CoroutinePermissions.getInstance(), 8)
            println("Location arrived $location")
        }

    }

}