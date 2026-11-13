package com.example.linisnasugbu.model

class Route(
    val routeId: Int,
    val collector: Collector,
    val stops: MutableList<RouteStop>

) {

    fun completeStop(stopId: Int) {

        stops.find {
            it.stopId == stopId
        }?.completed = true

    }

}