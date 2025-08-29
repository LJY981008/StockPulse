package com.example.stockpulse.domain.news

import com.example.stockpulse.domain.company.Company
import com.example.stockpulse.domain.datasource.DataSource
import java.time.LocalDateTime
import java.util.UUID

interface NewsArticleRepositoryPort {
    
    // Read operations
    fun findById(articleId: UUID): NewsArticle?
    
    fun findByOriginalUrl(originalUrl: String): NewsArticle?
    
    fun findByCompanyOrderByPublishedAtDesc(company: Company, page: Int, size: Int): List<NewsArticle>
    
    fun findByDataSourceOrderByPublishedAtDesc(dataSource: DataSource, page: Int, size: Int): List<NewsArticle>
    
    fun findByPublishedAtBetweenOrderByPublishedAtDesc(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        page: Int,
        size: Int
    ): List<NewsArticle>
    
    fun findByCompanyAndPublishedAtBetweenOrderByPublishedAtDesc(
        company: Company,
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        page: Int,
        size: Int
    ): List<NewsArticle>
    
    fun findAll(): List<NewsArticle>
    
    // Check operations
    fun existsByOriginalUrl(originalUrl: String): Boolean
    
    fun existsById(articleId: UUID): Boolean
    
    // Count operations
    fun countByCompany(company: Company): Long
    
    fun count(): Long
    
    // Write operations
    fun save(newsArticle: NewsArticle): NewsArticle
    
    fun saveAll(newsArticles: List<NewsArticle>): List<NewsArticle>
    
    fun deleteById(articleId: UUID)
    
    fun delete(newsArticle: NewsArticle)
}