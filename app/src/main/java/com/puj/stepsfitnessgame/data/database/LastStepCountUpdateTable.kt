package com.puj.stepsfitnessgame.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.GregorianCalendar

@Entity(tableName = "last_step_count_update")
data class LastStepCountUpdateTable(
    @PrimaryKey
    val id: Int,
    @field:TypeConverters(GregorianCalendarConverter::class)
    val lastStepCountUpdate: LocalDateTime
){
    companion object {
        const val ID = 1
    }
}