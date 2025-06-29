package com.example.gymjournal.data.mapper

import com.example.gymjournal.data.local.entity.ProfileEntity
import com.example.gymjournal.domain.model.Profile

fun Profile.toEntity(): ProfileEntity {
    return ProfileEntity(
        name = name,
        height = height,
        weight = weight,
        gender = gender,
        dateOfBirth = dateOfBirth
    )
}

fun ProfileEntity.toDomain(): Profile {
    return Profile(
        name = name,
        height = height,
        weight = weight,
        gender = gender,
        dateOfBirth = dateOfBirth
    )
}