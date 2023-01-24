package com.puj.stepsfitnessgame.data.database

import androidx.room.TypeConverter
import java.time.LocalDateTime

class LocalDateTimeConverter {

    @TypeConverter
    fun fromGregorianCalendar(timeDate: LocalDateTime): String {
        return timeDate.toString()
    }

    @TypeConverter
    fun toGregorianCalendar(string: String): LocalDateTime {
        return LocalDateTime.parse(string)
    }
}