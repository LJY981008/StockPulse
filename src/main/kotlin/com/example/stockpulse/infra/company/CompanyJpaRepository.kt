package com.example.stockpulse.infra.company

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface CompanyJpaRepository : JpaRepository<JpaCompany, UUID> {

    fun findByStockCode(stockCode: String): JpaCompany?

    fun findByIsActive(isActive: Boolean): List<JpaCompany>

    @Query("SELECT c FROM JpaCompany c WHERE c.isActive = true ORDER BY c.companyName")
    fun findActiveCompaniesOrderByName(): List<JpaCompany>

    fun existsByStockCode(stockCode: String): Boolean
}