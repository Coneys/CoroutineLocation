package com.github.coneys.coroutineLocation.savedLocation

import android.content.Context
import android.location.Location
import com.github.coneys.coroutineLocation.state.LocationState
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

internal class SavedLocationPreferences(private val appContext: Context) {

    private val latKey = "lat_coneys_coroutine_location_preferences"
    private val lonKey = "lon_coneys_coroutine_location_preferences"
    private val timestampKey = "timestamp_coneys_coroutine_location_preferences"

    @ExperimentalTime
    fun loadLocationState(noOlderThan: Duration?): LocationState {
        val preferences = appContext.getSharedPreferences(
            "coneys_coroutine_location_preferences",
            Context.MODE_PRIVATE
        )
        val lat = preferences.getFloat(latKey, 0.0f)
        val lon = preferences.getFloat(lonKey, 0.0f)
        val timestamp = preferences.getLong(timestampKey, 0)

        val savedTimeDuration = timestamp.milliseconds

        return if ((lat != 0.0f) && (lon != 0.0f)) {
            val location = Location("location")
            location.latitude = lat.toDouble()
            location.longitude = lon.toDouble()
            val lastSuccessLocation = LocationState.Success(location)

            if (noOlderThan == null) {
                lastSuccessLocation
            } else {
                val now = System.currentTimeMillis().milliseconds
                val checkTime = now - noOlderThan
                if (savedTimeDuration >= checkTime) {
                    lastSuccessLocation
                } else {
                    LocationState.NoLocation
                }
            }
        } else {
            LocationState.NoLocation
        }
    }

    fun saveLocation(locationState: LocationState) {
        val preferences = appContext.getSharedPreferences(
            "coneys_coroutine_location_preferences",
            Context.MODE_PRIVATE
        )
        if (locationState is LocationState.Success) {
            preferences.edit().putFloat(latKey, locationState.location.latitude.toFloat()).apply()
            preferences.edit().putFloat(lonKey, locationState.location.longitude.toFloat()).apply()
            preferences.edit().putLong(timestampKey, System.currentTimeMillis()).apply()
        }
    }
}