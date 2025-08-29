package com.example.stockpulse.domain.company

import java.time.LocalDateTime
import java.util.UUID

class Company(
    val companyId: UUID,
    val stockCode: String,
    val companyName: String,
    val searchKeyword: String,
    val isActive: Boolean,
    val createdAt: LocalDateTime,
) {
    // 비지니스 로직
}
