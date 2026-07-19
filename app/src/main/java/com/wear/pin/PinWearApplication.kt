package com.wear.pin

import android.app.Application
import com.wear.pin.di.AppContainer

class PinWearApplication : Application() {
    // AppContainer instance used by the rest of classes to obtain dependencies
    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}
