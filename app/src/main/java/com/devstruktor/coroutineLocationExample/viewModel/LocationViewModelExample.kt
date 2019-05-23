package com.devstruktor.coroutineLocationExample.viewModel

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.devstruktor.coroutineLocationExample.R


class LocationViewModelExample : AppCompatActivity() {

    val viewModel: LocationViewModel by lazy { ViewModelProviders.of(this).get(LocationViewModel::class.java) }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        viewModel.start()

    }

}
