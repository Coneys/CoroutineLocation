package com.devstruktor.coroutineLocationExample

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.devstruktor.coroutineLocation.provider.getLastLocation
import com.devstruktor.coroutineLocation.provider.observeLocation
import com.devstruktor.coroutine_permission.CoroutinePermissions
import com.google.android.gms.location.LocationRequest
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity2 : AppCompatActivity(), CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Main + Job()


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        //getLastLocationExample()
        observeLocationExample()


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

        val permissions = CoroutinePermissions(this)

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


            delay(4000)

            job.cancel()

            delay(4000)
            job2.cancel()

        }


    }


    private fun getLastLocationExample() {
        launch {
            val location = getLastLocation()
            println("State $location")

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}