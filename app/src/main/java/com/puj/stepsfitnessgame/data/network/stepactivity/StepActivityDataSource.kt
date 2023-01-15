package com.puj.stepsfitnessgame.data.network.stepactivity

class StepActivityDataSource {

    val stepActivityDataProvider: StepActivityDataProvider by lazy {
        GoogleFitDataProvider()
    }

    init {

    }
}