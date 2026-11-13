package com.example.linisnasugbu.model

data class Schedule(
    val scheduleId: Int,
    val date: String,
    val wasteType: String,
    val collectionPoint: CollectionPoint
)