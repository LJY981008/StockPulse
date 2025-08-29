package com.example.stockpulse.infra.datasource

import org.springframework.data.jpa.repository.JpaRepository

interface DataSourceJpaRepository : JpaRepository<JpaDataSource, Int> {
    
    fun findBySourceName(sourceName: String): JpaDataSource?
    
    fun existsBySourceName(sourceName: String): Boolean
}