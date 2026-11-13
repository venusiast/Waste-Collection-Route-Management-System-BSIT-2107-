package com.example.linisnasugbu.model

import com.example.linisnasugbu.model.enums.PickupStatus
import com.example.linisnasugbu.model.enums.WasteType
import java.time.LocalDateTime

data class BulkPickupRequest(
    val requestId: String,
    val residentId: String,
    val location: Location,
    val wasteType: WasteType,
    val estimatedWeight: Double,
    val description: String = "",
    val status: PickupStatus = PickupStatus.PENDING,
    val dateRequested: LocalDateTime = LocalDateTime.now(),
    val scheduledDate: LocalDateTime? = null,
    val collectorNotes: String = ""
)