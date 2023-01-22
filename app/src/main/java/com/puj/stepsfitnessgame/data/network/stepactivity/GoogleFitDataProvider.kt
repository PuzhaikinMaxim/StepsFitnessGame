package com.puj.stepsfitnessgame.data.network.stepactivity

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.HistoryClient
import com.google.android.gms.fitness.data.DataSource
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import com.puj.stepsfitnessgame.domain.models.stepactivity.StepData
import com.puj.stepsfitnessgame.presentation.ApplicationContextProvider
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import java.util.concurrent.TimeUnit

class GoogleFitDataProvider: StepActivityDataProvider {

    private val context = ApplicationContextProvider.getApplicationContext()

    private val historyClient: HistoryClient

    init {
        val fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.TYPE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
            .build()

        val googleSignInAccount = GoogleSignIn.getAccountForExtension(context, fitnessOptions)
        //val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(context)
        historyClient = Fitness.getHistoryClient(context, googleSignInAccount)
    }

    override fun getTodayStepCount(): Int {
        var totalSteps: Int? = null
        historyClient
            .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA  )
            .addOnSuccessListener {
                totalSteps = it.dataPoints.firstOrNull()?.getValue(Field.FIELD_STEPS)?.asInt() ?: 0
            }
            .addOnFailureListener {

            }
        println(totalSteps)
        return totalSteps ?: 0
    }

    override fun getStepCountInInterval(
        start: LocalDateTime,
        end: LocalDateTime,
        onDataGotListener: (Int) -> Unit
    ) {
        println(start.toEpochSecond(ZoneOffset.UTC))
        val datasource = DataSource.Builder()
            .setAppPackageName("com.google.android.gms")
            .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
            .setType(DataSource.TYPE_DERIVED)
            .setStreamName("estimated_steps")
            .build()

        val request = DataReadRequest.Builder()
            .aggregate(datasource)
            .bucketByTime(1, TimeUnit.DAYS)
            .setTimeRange(start.toEpochSecond(ZoneOffset.UTC), end.toEpochSecond(ZoneOffset.UTC), TimeUnit.SECONDS)
            .build()

        var stepCount = 0
        historyClient.readData(request)
            .addOnSuccessListener { response ->
                println("Bucket: " + response.buckets)
                stepCount = response.buckets
                    .flatMap { it.dataSets }
                    .flatMap { it.dataPoints }
                    .sumOf { it.getValue(Field.FIELD_STEPS).asInt() }
                onDataGotListener.invoke(stepCount)
            }
            .addOnFailureListener {
                println(it)
            }
    }

    override fun getTodayStepData(): StepData {
        TODO("Not yet implemented")
    }
}