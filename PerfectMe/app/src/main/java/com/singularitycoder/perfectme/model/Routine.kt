package com.singularitycoder.perfectme.model

data class Routine(
    val routineName: String,
    val routineDuration: String,
    val stepsList: List<RoutineStep>
)
