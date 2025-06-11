package com.example.gymjournal.data.model

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val password: String, // jangan kirim ini ke API publik
    val createdAt: String
)