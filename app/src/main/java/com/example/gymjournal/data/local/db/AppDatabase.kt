package com.example.gymjournal.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gymjournal.data.local.dao.ExerciseDao
import com.example.gymjournal.data.local.dao.GoalDao
import com.example.gymjournal.data.local.dao.ProfileDao
import com.example.gymjournal.data.local.dao.RoutineDao
import com.example.gymjournal.data.local.dao.RoutineDayDao
import com.example.gymjournal.data.local.dao.RoutineExerciseDao
import com.example.gymjournal.data.local.entity.ExerciseEntity
import com.example.gymjournal.data.local.entity.GoalEntity
import com.example.gymjournal.data.local.entity.ProfileEntity
import com.example.gymjournal.data.local.entity.RoutineDayEntity
import com.example.gymjournal.data.local.entity.RoutineEntity
import com.example.gymjournal.data.local.entity.RoutineExerciseEntity

@Database(
    entities = [
        ExerciseEntity::class,
        GoalEntity::class,
        RoutineEntity::class,
        RoutineDayEntity::class,
        RoutineExerciseEntity::class,
        ProfileEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun exerciseDao(): ExerciseDao

    abstract fun goalDao(): GoalDao

    abstract fun routineDao(): RoutineDao
    abstract fun routineDayDao(): RoutineDayDao
    abstract fun routineExerciseDao(): RoutineExerciseDao

    abstract fun profileDao(): ProfileDao // ✅ Tambahkan ini
}