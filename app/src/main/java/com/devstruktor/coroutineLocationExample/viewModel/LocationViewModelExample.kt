package com.devstruktor.coroutineLocationExample.viewModel

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.devstruktor.coroutineLocation.provider.getLastLocation
import com.devstruktor.coroutineLocation.provider.observeLocation
import com.devstruktor.coroutineLocationExample.R
import com.devstruktor.coroutine_permission.CoroutinePermissions
import com.google.android.gms.location.LocationRequest
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.cancel
import kotlin.coroutines.CoroutineContext


class LocationViewModelExample : AppCompatActivity() {

    val viewModel :LocationViewModel by lazy { ViewModelProviders.of(this).get(LocationViewModel ::class.java) }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        viewModel.start()

    }

}
