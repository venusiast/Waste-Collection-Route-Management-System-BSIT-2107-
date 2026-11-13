package com.example.linisnasugbu.model

class Collector(
    id: Int,
    name: String,
    email: String,
    val employeeId: String
) : User(id, name, email, role = "Collector") {

    override fun showDashboard() {
        println("Collector Dashboard")
    }

}