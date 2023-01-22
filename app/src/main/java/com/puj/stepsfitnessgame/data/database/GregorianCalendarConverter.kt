package com.puj.stepsfitnessgame.data.database

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.*

class GregorianCalendarConverter {

    @TypeConverter
    fun fromGregorianCalendar(timeDate: LocalDateTime): String {
        return timeDate.toString()
    }

    @TypeConverter
    fun toGregorianCalendar(string: String): LocalDateTime {
        return LocalDateTime.parse(string)
    }
}