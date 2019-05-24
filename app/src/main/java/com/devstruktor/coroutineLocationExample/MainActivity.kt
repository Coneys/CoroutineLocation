package com.devstruktor.coroutineLocationExample

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.devstruktor.coroutineLocationExample.activity.LocationActivityExample
import com.devstruktor.coroutineLocationExample.viewModel.LocationViewModelExample
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity_example.setOnClickListener {
            startActivity(Intent(this, LocationActivityExample::class.java))
        }

        viewmodel_example.setOnClickListener {
            startActivity(Intent(this, LocationViewModelExample::class.java))
        }



    }


}
