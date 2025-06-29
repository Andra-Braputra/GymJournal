package com.example.gymjournal.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routines")
data class RoutineEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val isSelected: Boolean = false
)
