package com.puj.stepsfitnessgame.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserGoalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setNewGoal(newGoal: UserGoalDbModel)

    @Query("SELECT * FROM user_goal LIMIT 1")
    fun getGoal(): UserGoalDbModel?
}