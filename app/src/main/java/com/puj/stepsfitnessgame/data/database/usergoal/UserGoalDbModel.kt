package com.puj.stepsfitnessgame.data.database.usergoal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_goal")
data class UserGoalDbModel(
    @PrimaryKey
    val id: Int = ID,
    val goal: Int
) {
    companion object {
        const val ID = 1
    }
}