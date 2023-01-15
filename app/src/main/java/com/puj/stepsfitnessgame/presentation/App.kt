package com.puj.stepsfitnessgame.presentation

import android.app.Application


class App : Application() {

    init {
        ApplicationContextProvider.setApplication(this)
    }
}