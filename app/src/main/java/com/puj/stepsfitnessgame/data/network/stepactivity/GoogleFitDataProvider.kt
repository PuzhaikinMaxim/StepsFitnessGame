package com.puj.stepsfitnessgame.data.network.stepactivity

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.HistoryClient
import com.google.android.gms.fitness.data.DataSource
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.DataType.TYPE_DISTANCE_DELTA
import com.google.android.gms.fitness.data.DataType.TYPE_STEP_COUNT_DELTA
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import com.puj.stepsfitnessgame.domain.models.stepactivity.StepData
import com.puj.stepsfitnessgame.presentation.ApplicationContextProvider
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GoogleFitDataProvider: StepActivityDataProvider {

    private val context = ApplicationContextProvider.getApplicationContext()

    private val historyClient: HistoryClient

    init {
        val fitnessOptions = FitnessOptions.builder()
            .addDataType(TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .addDataType(TYPE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
            .build()

        val googleSignInAccount = GoogleSignIn.getAccountForExtension(context, fitnessOptions)
        historyClient = Fitness.getHistoryClient(context, googleSignInAccount)
    }

    override suspend fun getTodayStepCount(): Int {
        getTodayData(TYPE_STEP_COUNT_DELTA, Field.FIELD_STEPS)
        TODO()
    }

    override suspend fun getTodayMetersPassed(): Int {
        getTodayData(TYPE_DISTANCE_DELTA, Field.FIELD_DISTANCE)
        TODO()
    }

    private fun getTodayData(
        dataType: DataType,
        field: Field,
    ) {
        var value: Int
        historyClient
            .readDailyTotal(dataType)
            .addOnSuccessListener {
                value = it.dataPoints.firstOrNull()?.getValue(field)?.asInt() ?: 0
            }
    }

    override suspend fun getStepCountInInterval(
        start: LocalDateTime,
        end: LocalDateTime
    ): Int = suspendCoroutine { cont ->
        val datasource = DataSource.Builder()
            .setAppPackageName("com.google.android.gms")
            .setDataType(TYPE_STEP_COUNT_DELTA)
            .setType(DataSource.TYPE_DERIVED)
            .setStreamName("estimated_steps")
            .build()

        val request = DataReadRequest.Builder()
            .aggregate(datasource)
            .bucketByTime(1, TimeUnit.DAYS)
            .setTimeRange(
                start.toEpochSecond(ZoneOffset.UTC),
                end.toEpochSecond(ZoneOffset.UTC),
                TimeUnit.SECONDS
            )
            .build()

        var stepCount: Int = 0
        historyClient.readData(request)
            .addOnSuccessListener { response ->
                stepCount = response.buckets
                    .flatMap { it.dataSets }
                    .flatMap { it.dataPoints }
                    .sumOf { it.getValue(Field.FIELD_STEPS).asInt() }
                cont.resume(stepCount)
            }
            .addOnFailureListener {
                cont.resume(stepCount)
            }
    }

    override suspend fun getTodayStepData(): StepData {
        TODO("Not yet implemented")
    }
}