package com.example.linisnasugbu.model

class WasteFacility(
    name: String,
    latitude: Double,
    longitude: Double,
    val facilityType: String
) : Location(name, latitude, longitude)