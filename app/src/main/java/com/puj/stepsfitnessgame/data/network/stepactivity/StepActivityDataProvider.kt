package com.puj.stepsfitnessgame.data.network.stepactivity

import com.puj.stepsfitnessgame.domain.models.stepactivity.StepData

interface StepActivityDataProvider {

    fun getTodayStepCount(): Int

    fun getTodayStepData(): StepData
}