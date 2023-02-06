package com.puj.stepsfitnessgame.presentation

import android.app.Application
import android.content.Context

object ApplicationContextProvider {

    private lateinit var application: Application

    fun setApplicationContext(application: Application) {
        this.application = application
    }

    fun getApplicationContext(): Context {
        return application.applicationContext
    }
}