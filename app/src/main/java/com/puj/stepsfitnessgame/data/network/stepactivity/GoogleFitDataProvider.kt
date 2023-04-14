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
import com.puj.stepsfitnessgame.data.stepactivity.StepActivityDataProvider
import com.puj.stepsfitnessgame.domain.models.stepstatistics.StepData
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
        historyClient = Fitness.getHistoryClient(context, googleSignInAccount)
        Fitness
            .getRecordingClient(context, googleSignInAccount)
            .subscribe(TYPE_STEP_COUNT_DELTA)
        Fitness
            .getRecordingClient(context, googleSignInAccount)
            .subscribe(TYPE_DISTANCE_DELTA)
        println(googleSignInAccount.account)
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
                if(dataType == TYPE_STEP_COUNT_DELTA) {
                    value = it.dataPoints.firstOrNull()?.getValue(field)?.asInt() ?: 0
                    println(value)
                }
                else if(dataType == TYPE_DISTANCE_DELTA){
                    value = it.dataPoints.firstOrNull()?.getValue(field)?.asFloat()?.toInt() ?: 0
                }
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

        var start = end
            .withDayOfMonth(1)
            .withHour(0)
            .withMinute(0)

        val list = ArrayList<StepData>()

        for (i in 1 until  amount + 1){
            val monthDayDuration = end.dayOfMonth
            val stepAmount = getIntervalStatistics(
                start,
                end,
                monthDayDuration,
                TimeUnit.DAYS,
                FitnessDataType.STEPS
            )
            var metersAmount = getIntervalStatistics(
                start,
                end,
                monthDayDuration,
                TimeUnit.DAYS,
                FitnessDataType.DISTANCE
            )
            if(stepAmount != 0 && metersAmount == 0){
                metersAmount = (stepAmount / 1.4).toInt()
            }
            val month = start.toLocalDate().month.value
            list.add(
                StepData(
                    stepAmount,
                    metersAmount,
                    start.toLocalDate(),
                    stepAmount / amount,
                    StepData.getMonthRepresentation(month)
                )
            )
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
                FitnessDataType.STEPS
            )
            var metersAmount = getIntervalStatistics(
                start,
                end,
                WEEK,
                TimeUnit.DAYS,
                FitnessDataType.DISTANCE
            )
            if(stepAmount != 0 && metersAmount == 0){
                metersAmount = (stepAmount / 1.4).toInt()
            }
            list.add(
                StepData(
                    stepAmount,
                    metersAmount,
                    start.toLocalDate(),
                    stepAmount/amount
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
                FitnessDataType.STEPS
            )
            var metersAmount = getIntervalStatistics(
                start,
                end,
                DAY,
                TimeUnit.DAYS,
                FitnessDataType.DISTANCE
            )
            if(stepAmount != 0 && metersAmount == 0){
                metersAmount = (stepAmount / 1.4).toInt()
            }
            list.add(
                StepData(
                    stepAmount,
                    metersAmount,
                    start.toLocalDate(),
                    stepAmount/amount
                )
            )
            start = start.minusDays(1)
            end = end.minusDays(1)
        }

        return list
    }

    private suspend fun getIntervalStatistics(
        start: LocalDateTime,
        end: LocalDateTime,
        duration: Int,
        timeUnit: TimeUnit,
        fitnessDataType: FitnessDataType
    ): Int = suspendCoroutine { cont ->
        val dataType: DataType?
        val streamName: String?
        val filed: Field?

        when(fitnessDataType){
            FitnessDataType.STEPS -> {
                dataType = TYPE_STEP_COUNT_DELTA
                streamName = "estimated_steps"
                filed = Field.FIELD_STEPS
            }
            FitnessDataType.DISTANCE -> {
                dataType = TYPE_DISTANCE_DELTA
                streamName = "merge_distance_delta"
                filed = Field.FIELD_DISTANCE
            }
        }

        val datasource = DataSource.Builder()
            .setAppPackageName("com.google.android.gms")
            .setDataType(dataType)
            .setType(DataSource.TYPE_DERIVED)
            .setStreamName(streamName)
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
                    .sumOf {
                        if(fitnessDataType == FitnessDataType.STEPS){
                            it.getValue(filed).asInt()
                        }
                        else if(fitnessDataType == FitnessDataType.DISTANCE){
                            it.getValue(filed).asFloat().toInt()
                        }
                        else {
                            throw RuntimeException("Unknown fitness data type")
                        }
                    }
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

    private enum class FitnessDataType {
        STEPS,
        DISTANCE
    }

    companion object {
        private const val DAY = 1
        private const val WEEK = 7
    }
}