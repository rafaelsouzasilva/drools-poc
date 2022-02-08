package com.poc.drools

import org.kie.api.runtime.KieContainer
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/drools")
class DroolsController(
    val kieContainer: KieContainer
) {

    @GetMapping
    fun get(
        @RequestParam("score") score: Int,
        @RequestParam("vehicleValue") vehicleValue: Double,
        @RequestParam("requestedAmount") requestedAmount: Double
    ): ResponseEntity<DroolsVariables> {
        val kieSession = kieContainer.newKieSession()
        val droolsVariables = DroolsVariables(score, vehicleValue, requestedAmount)
        kieSession.insert(droolsVariables)
        kieSession.fireAllRules()
        kieSession.dispose()
        return ResponseEntity.ok(droolsVariables)
    }
}