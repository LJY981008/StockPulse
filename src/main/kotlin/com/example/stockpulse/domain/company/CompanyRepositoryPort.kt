package com.example.stockpulse.domain.company

import java.util.UUID

interface CompanyRepositoryPort {
    // Read operations
    fun findById(companyId: UUID): Company?

    fun findByStockCode(stockCode: String): Company?

    fun findByIsActive(isActive: Boolean): List<Company>

    fun findActiveCompaniesOrderByName(): List<Company>

    fun findAll(): List<Company>

    // Check operations
    fun existsByStockCode(stockCode: String): Boolean

    fun existsById(companyId: UUID): Boolean

    // Write operations
    fun save(company: Company): Company

    fun saveAll(companies: List<Company>): List<Company>

    fun deleteById(companyId: UUID)

    fun delete(company: Company)

    fun count(): Long
}
