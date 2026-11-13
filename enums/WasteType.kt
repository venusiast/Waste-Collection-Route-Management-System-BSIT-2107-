package com.example.linisnasugbu.model.enums

enum class WasteType(val label: String, val description: String) {
    BIODEGRADABLE("Biodegradable", "Food waste, garden waste, paper"),
    NON_BIODEGRADABLE("Non-Biodegradable", "Plastic, metal, glass, bottles"),
    RECYCLABLE("Recyclable", "Paper, cardboard, clean plastics"),
    HAZARDOUS("Hazardous", "Batteries, chemicals, medical waste")
}
