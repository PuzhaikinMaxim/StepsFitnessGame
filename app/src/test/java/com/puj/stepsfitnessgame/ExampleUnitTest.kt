package com.puj.stepsfitnessgame

import android.app.Instrumentation
import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.puj.stepsfitnessgame.data.network.stepactivity.GoogleFitDataProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import java.time.LocalDateTime
import java.time.ZoneOffset

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    lateinit var context: Context

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().context
    }
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun time() {
        val localDateTime = LocalDateTime.now().withHour(23).withMinute(59)
        println(localDateTime)
        println(localDateTime.atOffset(ZoneOffset.UTC))
        println(localDateTime.atOffset(ZoneOffset.MIN))
        println(localDateTime.atOffset(ZoneOffset.MAX))
        val end = LocalDateTime.now()
        println("End " + end)
        val start = end
            .minusMonths(12)
            .withDayOfMonth(1)
            .withHour(0)
            .withMinute(0)
        println(start)
        println(end)
    }
    @Test
    fun getStepData() {
        val googleFitDataProvider = GoogleFitDataProvider()
        val coroutine = CoroutineScope(Dispatchers.Default)
        coroutine.launch {
            val l = googleFitDataProvider.getLastMonthsStatistics(12)
            println(l)
        }
    }
}