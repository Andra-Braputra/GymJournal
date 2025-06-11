package com.example.gymjournal.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gymjournal.data.local.dao.ExerciseDao


abstract class AppDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gymjournal_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
