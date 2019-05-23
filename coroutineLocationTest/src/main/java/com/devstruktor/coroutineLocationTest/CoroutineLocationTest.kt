package com.devstruktor.coroutineLocationTest

import com.devstruktor.coroutineLocationSettings.CoroutineLocationSettings


object CoroutineLocationTest {
    internal val testProvider by lazy { TestProvider() }

    fun startTestMode(): TestProvider {
        if (!BuildConfig.DEBUG) {
            throw IllegalStateException("Test mode cannot be invoked on release build")
        }

        CoroutineLocationSettings.strategy = TestStrategy(testProvider)
        return testProvider
    }
}