package com.example.stockpulse.infra.news

import com.example.stockpulse.infra.company.JpaCompany
import com.example.stockpulse.infra.datasource.JpaDataSource
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime
import java.util.UUID

interface NewsArticleJpaRepository : JpaRepository<JpaNewsArticle, UUID> {
    
    fun findByJpaCompanyOrderByPublishedAtDesc(jpaCompany: JpaCompany, pageable: Pageable): Page<JpaNewsArticle>
    
    fun findByJpaDataSourceOrderByPublishedAtDesc(jpaDataSource: JpaDataSource, pageable: Pageable): Page<JpaNewsArticle>
    
    @Query("SELECT n FROM JpaNewsArticle n WHERE n.publishedAt BETWEEN :startDate AND :endDate ORDER BY n.publishedAt DESC")
    fun findByPublishedAtBetweenOrderByPublishedAtDesc(
        @Param("startDate") startDate: LocalDateTime,
        @Param("endDate") endDate: LocalDateTime,
        pageable: Pageable
    ): Page<JpaNewsArticle>
    
    @Query("SELECT n FROM JpaNewsArticle n WHERE n.jpaCompany = :company AND n.publishedAt BETWEEN :startDate AND :endDate ORDER BY n.publishedAt DESC")
    fun findByJpaCompanyAndPublishedAtBetweenOrderByPublishedAtDesc(
        @Param("company") jpaCompany: JpaCompany,
        @Param("startDate") startDate: LocalDateTime,
        @Param("endDate") endDate: LocalDateTime,
        pageable: Pageable
    ): Page<JpaNewsArticle>
    
    fun existsByOriginalUrl(originalUrl: String): Boolean
    
    fun findByOriginalUrl(originalUrl: String): JpaNewsArticle?
    
    @Query("SELECT COUNT(n) FROM JpaNewsArticle n WHERE n.jpaCompany = :company")
    fun countByJpaCompany(@Param("company") jpaCompany: JpaCompany): Long
}