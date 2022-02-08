package com.poc.drools

data class DroolsVariables(
    val score: Int,
    val vehicleValue: Double,
    val requestedAmount: Double,
    var result: String? = null
) {
    fun defineResult(result: String) {
        this.result = result
    }
}