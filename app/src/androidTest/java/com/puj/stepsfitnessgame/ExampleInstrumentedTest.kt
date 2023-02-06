package com.puj.stepsfitnessgame

import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puj.stepsfitnessgame.data.network.challenge.ChallengeRemoteDataSourceImpl
import com.puj.stepsfitnessgame.data.network.stepactivity.GoogleFitDataProvider
import com.puj.stepsfitnessgame.presentation.ApplicationContextProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.puj.stepsfitnessgame", appContext.packageName)
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

    @Test
    fun challengeApiTest() {
        val challengeRemoteDataSource = ChallengeRemoteDataSourceImpl("fuckin689c49c3-0521-4007-a4df-c07ab58020ab")
        //challengeRemoteDataSource.getChallengesListByLevel(1)
    }
}