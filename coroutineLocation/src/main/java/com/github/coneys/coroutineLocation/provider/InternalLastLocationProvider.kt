package com.github.coneys.coroutineLocation.provider

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import com.github.coneys.coroutineLocation.initalizer.LocationInitProvider
import com.github.coneys.coroutineLocation.state.LocationState
import com.github.coneys.coroutineLocation.toLocationState
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

internal class InternalLastLocationProvider {

    private val provider by lazy { LocationInitProvider.provider }
    private val context by lazy { provider.applicationContext }


    @SuppressLint("MissingPermission")
    suspend fun getLastLocation(): LocationState {
        return suspendCancellableCoroutine { continuation ->
            try {
                if (isLocationEnabled()) {
                    provider.lastLocation.addOnCompleteListener {
                        try {
                            val state = it.toLocationState()
                            continuation.resume(state)
                        }catch (s:Throwable){
                            continuation.resume(LocationState.NoPermission)
                        }

                    }
                } else {
                    continuation.resume(LocationState.LocationDisabled)
                }


            } catch (s: Throwable) {
                continuation.resume(LocationState.NoPermission)
            }
        }
    }


    fun isLocationEnabled(): Boolean {

        val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val gpsEnabled = isGpsEnabled(manager)

        val networkEnabled = isNetworkLocationEnabled(manager)

        return gpsEnabled or networkEnabled

    }

    private fun isNetworkLocationEnabled(manager: LocationManager): Boolean {
        return try {
            manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (ex: Exception) {
            false
        }
    }

    private fun isGpsEnabled(lm: LocationManager): Boolean {
        return try {
            lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (ex: Exception) {
            false
        }
    }

}