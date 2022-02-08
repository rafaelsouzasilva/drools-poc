package com.poc.drools

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DroolsApplication

fun main(args: Array<String>) {
	runApplication<DroolsApplication>(*args)
}
