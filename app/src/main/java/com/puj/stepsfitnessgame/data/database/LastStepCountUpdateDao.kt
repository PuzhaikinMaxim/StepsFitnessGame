package com.puj.stepsfitnessgame.data.database

import androidx.room.Dao
import androidx.room.Insert
import java.util.GregorianCalendar

@Dao
interface LastStepCountUpdateDao {

    @Insert
    fun setLastStepCountUpdate(lastUpdate: LastStepCountUpdateTable)
}