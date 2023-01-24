package com.puj.stepsfitnessgame.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import java.lang.RuntimeException

@Database(
    entities = [LastStepCountUpdateDbModel::class, UserGoalDbModel::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class FitnessGameDatabase: RoomDatabase() {

    abstract fun lastStepCountUpdateDao(): LastStepCountUpdateDao
    abstract fun goalDao(): UserGoalDao

    companion object{
        @Volatile
        private var INSTANCE: FitnessGameDatabase? = null

        fun initializeDatabase(context: Context){
            val tempDatabase = INSTANCE
            if(tempDatabase != null){
                return
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FitnessGameDatabase::class.java,
                    "task_database.db"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
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