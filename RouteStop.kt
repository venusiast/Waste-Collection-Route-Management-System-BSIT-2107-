package com.example.linisnasugbu.model

data class RouteStop(
    val stopId: Int,
    val point: CollectionPoint,
    var completed: Boolean = false
)