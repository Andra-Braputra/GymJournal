package com.example.gymjournal.data.dto

import com.google.firebase.firestore.PropertyName

data class ExerciseDto(
    @get:PropertyName("id") @set:PropertyName("id")
    var id: Int = 0,

    @get:PropertyName("name") @set:PropertyName("name")
    var name: String = "",

    @get:PropertyName("muscle") @set:PropertyName("muscle")
    var muscle: String = "",

    @get:PropertyName("equipment") @set:PropertyName("equipment")
    var equipment: String = "",

    @get:PropertyName("description") @set:PropertyName("description")
    var description: String = ""


)


