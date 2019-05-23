package com.devstruktor.coroutineLocationExample

import androidx.lifecycle.ViewModel
import com.devstruktor.coroutineLocation.provider.observeLocation
import com.devstruktor.coroutineLocationCore.state.LocationState
import com.devstruktor.coroutineLocationTest.CoroutineLocationTest
import com.devstruktor.coroutineLocationTest.TestProvider
import com.github.coneys.coroutinePermission.staticPermission.SuspendPermissions
import com.github.coneys.coroutinePermissionTest.TestCoroutinePermission
import com.google.android.gms.location.LocationRequest
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.coroutines.CoroutineContext

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        val testProvider = CoroutineLocationTest.startTestMode()

        val viewModel = DummyViewModel()
        viewModel.observeLocationState()

        testProvider.pushStateForAll(LocationState.LocationDisabled)
        testProvider.pushStateForAll(LocationState.NoLocation)

    }
}

class DummyViewModel() : ViewModel(), CoroutineScope {
    override var coroutineContext: CoroutineContext = Dispatchers.Unconfined


    fun observeLocationState() {

        val request = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(6000)
            .setFastestInterval(1000)

        val permissions = TestCoroutinePermission(true)


        launch {
            observeLocation(request, permissions) {
                println("New location state $it")
            }
        }
    }

}