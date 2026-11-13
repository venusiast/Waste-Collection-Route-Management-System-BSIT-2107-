package com.example.linisnasugbu.service

import com.example.linisnasugbu.model.Schedule
import com.example.linisnasugbu.model.Complaint

class WasteCollectionService {

    private val schedules = mutableListOf<Schedule>()

    private val complaints = mutableListOf<Complaint>()

    fun addSchedule(schedule: Schedule) {
        schedules.add(schedule)
    }

    fun addComplaint(complaint: Complaint) {
        complaints.add(complaint)
    }

    fun viewSchedules() {

        schedules.forEach {

            println(
                "${it.date} - ${it.wasteType} - ${it.collectionPoint.name}"
            )

        }

    }

    fun viewComplaints() {

        complaints.forEach {

            println(
                "${it.resident.name}: ${it.description}"
            )

        }

    }

}