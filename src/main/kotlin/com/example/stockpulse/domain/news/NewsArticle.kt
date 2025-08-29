package com.example.stockpulse.domain.news

import com.example.stockpulse.domain.company.Company
import com.example.stockpulse.domain.datasource.DataSource
import java.time.LocalDateTime
import java.util.UUID

class NewsArticle(
    val articleId: UUID,
    val company: Company,
    val dataSource: DataSource,
    val originalUrl: String,
    val title: String,
    val content: String?,
    val publishedAt: LocalDateTime,
    val collectedAt: LocalDateTime
) {
    // 비지니스 로직
}