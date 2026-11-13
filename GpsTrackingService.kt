package com.example.linisnasugbu.service

import com.example.linisnasugbu.model.Location

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GpsTrackingService {
    private val _currentLocation = MutableStateFlow<Location?>(null)
    val currentLocation: StateFlow<Location?> = _currentLocation

    private val _isTracking = MutableStateFlow(false)
    val isTracking: StateFlow<Boolean> = _isTracking

    fun startTracking() {
        _isTracking.value = true
    }

    fun stopTracking() {
        _isTracking.value = false
    }

    fun updateLocation(lat: Double, lng: Double, locationName: String = "") {
        _currentLocation.value = Location("brgy cogunan", 14.1, 120.0)
    }
}