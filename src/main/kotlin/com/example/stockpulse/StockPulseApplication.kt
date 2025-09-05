package com.example.stockpulse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StockPulseApplication

fun main(args: Array<String>) {
	runApplication<StockPulseApplication>(*args)
}
