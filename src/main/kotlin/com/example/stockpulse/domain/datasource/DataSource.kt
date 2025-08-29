package com.example.stockpulse.domain.datasource

import java.util.UUID

class DataSource(
    val sourceId: UUID,
    val sourceName: String,
    val baseUrl: String?
) {
    // 비지니스 로직
}