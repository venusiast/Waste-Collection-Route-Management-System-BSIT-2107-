package com.example.linisnasugbu.model

open class User(
    val id: Int,
    val name: String,
    val email: String,
    val role: String //
) {
    open fun showDashboard() {
        println("User Dashboard")
    }

}