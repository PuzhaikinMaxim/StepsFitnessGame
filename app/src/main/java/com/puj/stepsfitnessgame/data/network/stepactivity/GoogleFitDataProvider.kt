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
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.TemporalAdjusters.lastDayOfMonth
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
        println(googleSignInAccount)
        historyClient = Fitness.getHistoryClient(context, googleSignInAccount)
        Fitness
            .getRecordingClient(context, googleSignInAccount)
            .subscribe(TYPE_STEP_COUNT_DELTA)
        Fitness
            .getRecordingClient(context, googleSignInAccount)
            .subscribe(TYPE_DISTANCE_DELTA)
    }

    override suspend fun getTodayStepCount(): Int {
        return getTodayData(TYPE_STEP_COUNT_DELTA, Field.FIELD_STEPS)
    }

    override suspend fun getTodayMetersPassed(): Int {
        return getTodayData(TYPE_DISTANCE_DELTA, Field.FIELD_DISTANCE)
    }

    private suspend fun getTodayData(
        dataType: DataType,
        field: Field,
    ): Int = suspendCoroutine { cont ->
        var value = 0
        historyClient
            .readDailyTotal(dataType)
            .addOnSuccessListener {
                value = it.dataPoints.firstOrNull()?.getValue(field)?.asInt() ?: 0
                println(it)
                cont.resume(value)
            }
            .addOnFailureListener {
                println(it)
                cont.resume(value)
            }
    }

    override suspend fun getStepCountInInterval(
        start: LocalDateTime,
        end: LocalDateTime
    ): Int = suspendCoroutine { cont ->
        val datasource = getDataSource(TYPE_STEP_COUNT_DELTA)

        val request = DataReadRequest.Builder()
            .aggregate(datasource)
            .bucketByTime(1, TimeUnit.DAYS)
            .setTimeRange(
                start.toEpochSecond(ZoneOffset.UTC),
                end.toEpochSecond(ZoneOffset.UTC),
                TimeUnit.SECONDS
            )
            .build()

        var stepCount = 0
        historyClient.readData(request)
            .addOnSuccessListener { response ->
                println(response.buckets)
                stepCount = response.buckets
                    .flatMap { it.dataSets }
                    .flatMap { println(it.dataPoints); it.dataPoints }
                    .sumOf { it.getValue(Field.FIELD_STEPS).asInt() }
                cont.resume(stepCount)
            }
            .addOnFailureListener {
                cont.resume(stepCount)
            }
    }

    override suspend fun getLastMonthsStatistics(amount: Int): List<StepData> {
        var end = LocalDateTime
            .now()
            .with(lastDayOfMonth())
            .withHour(23)
            .withMinute(59)
        println("End " + end)
        var start = end
            .withDayOfMonth(1)
            .withHour(0)
            .withMinute(0)
        println("Start " + start)
        val list = ArrayList<StepData>()
        for (i in 1 until  amount + 1){
            val monthDayDuration = end.dayOfMonth
            val stepAmount = getIntervalStatistics(
                start,
                end,
                monthDayDuration,
                TimeUnit.DAYS,
                TYPE_STEP_COUNT_DELTA
            )
            val metersAmount = getIntervalStatistics(
                start,
                end,
                monthDayDuration,
                TimeUnit.DAYS,
                TYPE_DISTANCE_DELTA
            )
            list.add(
                StepData(
                    stepAmount,
                    metersAmount,
                    start.toLocalDate()
                )
            )
            println(start)
            println(end)
            start = start.minusMonths(1)
            end = end.minusMonths(1)
        }
        return list
    }

    override suspend fun getLastWeeksStatistics(amount: Int): List<StepData> {
        var end = LocalDateTime
            .now()
            .with(DayOfWeek.SUNDAY)
            .withHour(23)
            .withMinute(59)
        println("End " + end)
        var start = end
            .with(DayOfWeek.MONDAY)
            .withHour(0)
            .withMinute(0)
        val list = ArrayList<StepData>()
        for(i in 1 until amount + 1){
            val stepAmount = getIntervalStatistics(
                start,
                end,
                WEEK,
                TimeUnit.DAYS,
                TYPE_STEP_COUNT_DELTA
            )
            val metersAmount = getIntervalStatistics(
                start,
                end,
                WEEK,
                TimeUnit.DAYS,
                TYPE_DISTANCE_DELTA
            )
            list.add(
                StepData(
                    stepAmount,
                    metersAmount,
                    start.toLocalDate()
                )
            )
            start = start.minusWeeks(1)
            end = end.minusWeeks(1)
        }
        return list
    }

    override suspend fun getLastDaysStatistics(amount: Int): List<StepData> {
        var end = LocalDateTime
            .now()
            .withHour(23)
            .withMinute(59)
        println("End " + end)
        var start = end
            .withHour(0)
            .withMinute(0)
        val list = ArrayList<StepData>()
        for(i in 1 until amount + 1){
            val stepAmount = getIntervalStatistics(
                start,
                end,
                DAY,
                TimeUnit.DAYS,
                TYPE_STEP_COUNT_DELTA
            )
            val metersAmount = getIntervalStatistics(
                start,
                end,
                DAY,
                TimeUnit.DAYS,
                TYPE_DISTANCE_DELTA
            )
            list.add(
                StepData(
                    stepAmount,
                    metersAmount,
                    start.toLocalDate()
                )
            )
            start = start.minusDays(1)
            end = end.minusDays(1)
        }
        return list
    }

    /*
    private suspend fun getLastIntervalStatistics(
        start: LocalDateTime,
        end: LocalDateTime,
        duration: Int,
        timeUnit: TimeUnit
    ): List<StepData> = suspendCoroutine { cont ->
        val datasource = DataSource.Builder()
            .setAppPackageName("com.google.android.gms")
            .setDataType(AGGREGATE_STEP_COUNT_DELTA)
            .setType(DataSource.TYPE_DERIVED)
            .setStreamName("estimated_steps")
            .build()

        val request = DataReadRequest.Builder()
            .aggregate(datasource)
            .bucketByTime(duration, timeUnit)
            .setTimeRange(
                start.toEpochSecond(ZoneOffset.UTC),
                end.toEpochSecond(ZoneOffset.UTC),
                TimeUnit.SECONDS
            )
            .build()

        val list = ArrayList<StepData>()

        historyClient.readData(request)
            .addOnSuccessListener { response ->
                println(response.buckets)
                response.buckets
                    .flatMap { println(it); it.dataSets }
                    .flatMap { println(it.dataPoints); it.dataPoints }
                    .forEach {
                        val stepCount = it.getValue(Field.FIELD_STEPS).asInt()
                        val date = Instant.ofEpochMilli(
                            it.getStartTime(TimeUnit.MILLISECONDS)
                        ).atZone(ZoneId.systemDefault()).toLocalDate()
                        println(date)
                        println(
                            Instant.ofEpochMilli(
                                it.getEndTime(TimeUnit.MILLISECONDS)
                            ).atZone(ZoneId.systemDefault()).toLocalDate()
                        )
                        val stepData = StepData(
                            stepCount,
                            0,
                            date
                        )
                        list.add(stepData)
                    }
                cont.resume(list)
            }
            .addOnFailureListener {
                cont.resume(listOf())
            }
    }

     */

    private suspend fun getIntervalStatistics(
        start: LocalDateTime,
        end: LocalDateTime,
        duration: Int,
        timeUnit: TimeUnit,
        dataType: DataType
    ): Int = suspendCoroutine { cont ->
        val datasource = DataSource.Builder()
            .setAppPackageName("com.google.android.gms")
            .setDataType(dataType)
            .setType(DataSource.TYPE_DERIVED)
            .setStreamName("estimated_steps")
            .build()

        val request = DataReadRequest.Builder()
            .aggregate(datasource)
            .bucketByTime(duration, timeUnit)
            .setTimeRange(
                start.toEpochSecond(ZoneOffset.UTC),
                end.toEpochSecond(ZoneOffset.UTC),
                TimeUnit.SECONDS
            )
            .build()

        var value = 0

        historyClient.readData(request)
            .addOnSuccessListener { response ->
                value = response.buckets
                    .flatMap { it.dataSets }
                    .flatMap { it.dataPoints }
                    .sumOf { it.getValue(Field.FIELD_STEPS).asInt() }
                cont.resume(value)
            }
            .addOnFailureListener {
                cont.resume(value)
            }
    }

    private fun getDataSource(dataType: DataType): DataSource {
        return DataSource.Builder()
            .setAppPackageName("com.google.android.gms")
            .setDataType(dataType)
            .setType(DataSource.TYPE_DERIVED)
            .setStreamName("estimated_steps")
            .build()
    }

    companion object {
        private const val DAY = 1
        private const val WEEK = 7
    }
}