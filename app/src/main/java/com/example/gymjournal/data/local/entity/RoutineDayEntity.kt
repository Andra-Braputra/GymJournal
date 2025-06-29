package com.example.gymjournal.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "routine_days",
    foreignKeys = [
        ForeignKey(
            entity = RoutineEntity::class,
            parentColumns = ["id"],
            childColumns = ["routineId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["routineId"])]
)
data class RoutineDayEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val routineId: Int,
    val name: String,             // e.g. "Monday", "Tuesday"
    val order: Int,               // 0 for Sunday, 1 for Monday, ..., 6 for Saturday
    val isRestDay: Boolean = true
)