package com.example.linisnasugbu.model

class CollectionPoint(
    name: String,
    latitude: Double,
    longitude: Double,
    val wasteType: String
) : Location(name, latitude, longitude)