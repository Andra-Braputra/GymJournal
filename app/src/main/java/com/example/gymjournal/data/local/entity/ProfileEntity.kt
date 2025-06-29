package com.example.gymjournal.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey val id: Int = 0, // Only 1 profile allowed
    val name: String,
    val height: String,
    val weight: String,
    val gender: String,
    val dateOfBirth: String
)