package com.github.coneys.coroutineLocation

import com.github.coneys.coroutineLocation.initalizer.ProductionStrategy

object CoroutineLocationSettings {

    var strategy: LocationStrategy = ProductionStrategy

    fun enableLogging() {
        Logger.isEnabled = true
    }

    fun disableLogging() {
        Logger.isEnabled = false
    }


}