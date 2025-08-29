package com.example.stockpulse.infra.company

import com.example.stockpulse.domain.company.Company
import com.example.stockpulse.domain.company.CompanyRepositoryPort
import com.example.stockpulse.infra.company.JpaCompany.Companion.toCompany
import com.example.stockpulse.infra.company.JpaCompany.Companion.toJpaCompany
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class CompanyRepository(
    private val companyJpaRepository: CompanyJpaRepository
) : CompanyRepositoryPort {

    // Read operations
    override fun findById(companyId: UUID): Company? {
        return companyJpaRepository.findById(companyId)
            .map { it.toCompany() }
            .orElse(null)
    }

    override fun findByStockCode(stockCode: String): Company? {
        return companyJpaRepository.findByStockCode(stockCode)?.toCompany()
    }

    override fun findByIsActive(isActive: Boolean): List<Company> {
        return companyJpaRepository.findByIsActive(isActive)
            .map { it.toCompany() }
    }

    override fun findActiveCompaniesOrderByName(): List<Company> {
        return companyJpaRepository.findActiveCompaniesOrderByName()
            .map { it.toCompany() }
    }
    
    override fun findAll(): List<Company> {
        return companyJpaRepository.findAll()
            .map { it.toCompany() }
    }

    // Check operations
    override fun existsByStockCode(stockCode: String): Boolean {
        return companyJpaRepository.existsByStockCode(stockCode)
    }
    
    override fun existsById(companyId: UUID): Boolean {
        return companyJpaRepository.existsById(companyId)
    }

    // Write operations
    override fun save(company: Company): Company {
        val savedJpaCompany = companyJpaRepository.save(company.toJpaCompany())
        return savedJpaCompany.toCompany()
    }
    
    override fun saveAll(companies: List<Company>): List<Company> {
        val jpaCompanies = companies.map { it.toJpaCompany() }
        return companyJpaRepository.saveAll(jpaCompanies)
            .map { it.toCompany() }
    }
    
    override fun deleteById(companyId: UUID) {
        companyJpaRepository.deleteById(companyId)
    }
    
    override fun delete(company: Company) {
        companyJpaRepository.delete(company.toJpaCompany())
    }
    
    override fun count(): Long {
        return companyJpaRepository.count()
    }
}