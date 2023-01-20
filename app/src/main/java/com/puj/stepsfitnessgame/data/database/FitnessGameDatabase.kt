package com.puj.stepsfitnessgame.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import java.lang.RuntimeException

@Database(entities = [LastStepCountUpdateTable::class], version = 1, exportSchema = false)
@TypeConverters(GregorianCalendarConverter::class)
abstract class FitnessGameDatabase: RoomDatabase() {
    abstract fun taskDao(): LastStepCountUpdateDao

    companion object{
        @Volatile
        private var INSTANCE: FitnessGameDatabase? = null

        fun getDatabase(context: Context): FitnessGameDatabase{
            val tempDatabase = INSTANCE
            if(tempDatabase != null){
                return tempDatabase
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FitnessGameDatabase::class.java,
                    "task_database.db"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }

        fun getDatabase(): FitnessGameDatabase{
            val tempDatabase = INSTANCE
            if(tempDatabase != null){
                return tempDatabase
            }
            else {
                throw RuntimeException("To be corrected")
            }
        }
    }
}