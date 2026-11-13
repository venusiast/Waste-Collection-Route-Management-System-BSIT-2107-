package com.example.linisnasugbu.model

class Resident(
    id: Int,
    name: String,
    email: String,
    val address: String
) : User(id, name, email, role = "Resident") {

    override fun showDashboard() {
        println("Resident Dashboard")
    }

}