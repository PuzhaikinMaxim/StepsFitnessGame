package com.puj.stepsfitnessgame.data.network.stepactivity

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.HistoryClient
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.puj.stepsfitnessgame.domain.models.stepactivity.StepData
import com.puj.stepsfitnessgame.presentation.ApplicationContextProvider

class GoogleFitDataProvider: StepActivityDataProvider {

    private val context = ApplicationContextProvider.getApplicationContext()

    private val historyClient: HistoryClient

    init {
        val fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.TYPE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
            .build()

        val googleSignInAccount = GoogleSignIn.getAccountForExtension(context, fitnessOptions)

        historyClient = Fitness.getHistoryClient(context, googleSignInAccount)
    }

    override fun getTodayStepCount(): Int {
        var totalSteps: Int? = null
        historyClient
            .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA  )
            .addOnSuccessListener {
                totalSteps = it.dataPoints.firstOrNull()?.getValue(Field.FIELD_STEPS)?.asInt() ?: 0
            }
        return totalSteps ?: 0
    }

    override fun getTodayStepData(): StepData {
        TODO("Not yet implemented")
    }
}