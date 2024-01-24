package com.app.bugtrackerapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Perform initialization tasks here
        // This method is called once when the application is created
    }
}