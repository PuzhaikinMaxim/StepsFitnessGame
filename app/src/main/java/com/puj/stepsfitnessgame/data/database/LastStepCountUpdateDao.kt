package com.puj.stepsfitnessgame.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LastStepCountUpdateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setLastStepCountUpdate(lastUpdate: LastStepCountUpdateDbModel)

    @Query("SELECT * FROM last_step_count_update LIMIT 1")
    fun getLastUpdate(): LastStepCountUpdateDbModel?
}