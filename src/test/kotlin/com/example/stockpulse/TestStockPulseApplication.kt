package com.example.stockpulse

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<StockPulseApplication>().with(TestcontainersConfiguration::class).run(*args)
}
