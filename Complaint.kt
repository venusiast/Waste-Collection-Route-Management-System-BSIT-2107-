package com.example.linisnasugbu.model

class Complaint(
    val complaintId: Int,
    val resident: Resident,
    val description: String,
    var status: String = "Pending"
)