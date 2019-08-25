package com.devstruktor.coroutineLocationExample.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.coneys.coroutineLocation.provider.getLastLocation
import com.github.coneys.coroutineLocation.provider.observeLocation
import com.devstruktor.coroutineLocationExample.R
import com.github.coneys.coroutineLocation.provider.waitForLocation
import com.github.coneys.coroutinePermission.CoroutinePermissions
import com.google.android.gms.location.LocationRequest
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class LocationActivityExample : AppCompatActivity(), CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Default + Job()


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        getLastLocationExample()
        //observeLocationExample()


    }



    private fun observeLocationExample() {

        val request = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(6000)
            .setFastestInterval(1000)

        val request2 = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(6000)
            .setFastestInterval(1000)

        val request3 = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(4000)
            .setFastestInterval(800)

        val permissions = CoroutinePermissions.createInstanceForActivity(this)

        launch {

            val job = observeLocation(request, permissions) {
                println("New state 1 $it")
            }

            val job2 = observeLocation(request2, permissions) {
                println("New state 2  $it")
            }

            val job3 = observeLocation(request3, permissions) {
                println("New state 3  $it")
            }

            delay(1000)

            println("SENDING")

            val closeDialog = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
            sendBroadcast(closeDialog)


            delay(4000)

            job.cancel()

            delay(4000)
            job2.cancel()

        }


    }


    private fun getLastLocationExample() {
        launch {
            val location = getLastLocation(CoroutinePermissions.getInstance())
            println("State $location")

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
