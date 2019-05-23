package com.devstruktor.coroutineLocationExample.viewModel

import androidx.lifecycle.ViewModel
import com.devstruktor.coroutineLocation.provider.observeLocation
import com.github.coneys.coroutinePermission.CoroutinePermissions
import com.google.android.gms.location.LocationRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext


class LocationViewModel : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Main + Job()

    init {

        val request = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(6000)
            .setFastestInterval(1000)

        observeLocation(request, CoroutinePermissions.getInstance()) {
            println("State in view model $it")
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("Called on cleared!!")
        cancel()

    }

    fun start() {
        println("start")
    }

}