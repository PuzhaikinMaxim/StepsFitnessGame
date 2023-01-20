package com.puj.stepsfitnessgame.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.GregorianCalendar

@Entity(tableName = "last_step_count_update")
data class LastStepCountUpdateTable(
    @PrimaryKey
    val id: Int,
    @field:TypeConverters(GregorianCalendarConverter::class)
    val lastStepCountUpdate: GregorianCalendar
){
    companion object {
        const val ID = 1
    }
}