package com.puj.stepsfitnessgame.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.LocalDateTime

@Entity(tableName = "last_step_count_update")
data class LastStepCountUpdateDbModel(
    @PrimaryKey
    val id: Int = ID,
    @field:TypeConverters(LocalDateTimeConverter::class)
    val lastStepCountUpdate: LocalDateTime
){
    companion object {
        const val ID = 1
    }
}