package com.github.coneys.coroutineLocation.savedLocation

import android.content.Context
import android.location.Location
import com.github.coneys.coroutineLocation.state.LocationState

internal class SavedLocationPreferences(private val appContext: Context) {

    private val latKey = "lat_coneys_coroutine_location_preferences"
    private val lonKey = "lon_coneys_coroutine_location_preferences"

    fun loadLocationState(): LocationState {
        val preferences = appContext.getSharedPreferences(
            "coneys_coroutine_location_preferences",
            Context.MODE_PRIVATE
        )
        val lat = preferences.getFloat(latKey, 0.0f)
        val lon = preferences.getFloat(lonKey, 0.0f)

        return if ((lat != 0.0f) && (lon != 0.0f)) {
            val location = Location("location")
            location.latitude = lat.toDouble()
            location.longitude = lon.toDouble()
            LocationState.Success(location)
        } else {
            LocationState.NoLocation
        }
    }

    fun saveLocation(locationState: LocationState) {
        val preferences = appContext.getSharedPreferences(
            "coneys_coroutine_location_preferences",
            Context.MODE_PRIVATE
        )
        when (locationState) {
            is LocationState.Success -> {
                preferences.edit().putFloat(latKey, locationState.location.latitude.toFloat())
                    .apply()
                preferences.edit().putFloat(lonKey, locationState.location.longitude.toFloat())
                    .apply()
            }
            else -> {
                preferences.edit().remove(latKey).apply()
                preferences.edit().remove(lonKey).apply()
            }
        }
    }
}