package com.example.stockpulse.infra.company

import com.example.stockpulse.domain.company.Company
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "company")
data class JpaCompany(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "company_id", columnDefinition = "uuid")
    val companyId: UUID,

    @Column(name = "stock_code", unique = true, nullable = false, length = 10)
    val stockCode: String,

    @Column(name = "company_name", nullable = false, length = 100)
    val companyName: String,

    @Column(name = "search_keyword", nullable = false)
    val searchKeyword: String,

    @Column(name = "is_active", nullable = false)
    val isActive: Boolean = true,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) {

    companion object {
        fun JpaCompany.toCompany(): Company {
            return Company(
                companyId = this.companyId,
                stockCode = this.stockCode,
                companyName = this.companyName,
                searchKeyword = this.searchKeyword,
                isActive = this.isActive,
                createdAt = this.createdAt
            )
        }
        
        fun Company.toJpaCompany(): JpaCompany {
            return JpaCompany(
                companyId = this.companyId,
                stockCode = this.stockCode,
                companyName = this.companyName,
                searchKeyword = this.searchKeyword,
                isActive = this.isActive,
                createdAt = this.createdAt
            )
        }
    }
}