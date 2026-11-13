package com.example.linisnasugbu.model

class Manager(
    id: Int,
    name: String,
    email: String
) : User(id, name, email, role = "Manager") {

    override fun showDashboard() {
        println("Manager Dashboard")
    }

}