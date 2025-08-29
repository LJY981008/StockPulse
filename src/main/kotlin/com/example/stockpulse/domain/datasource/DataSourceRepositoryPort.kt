package com.example.stockpulse.domain.datasource

interface DataSourceRepositoryPort {
    
    // Read operations
    fun findById(sourceId: Int): DataSource?
    
    fun findBySourceName(sourceName: String): DataSource?
    
    fun findAll(): List<DataSource>

    // Check operations
    fun existsBySourceName(sourceName: String): Boolean
    
    fun existsById(sourceId: Int): Boolean

    // Write operations
    fun save(dataSource: DataSource): DataSource
    
    fun saveAll(dataSources: List<DataSource>): List<DataSource>
    
    fun deleteById(sourceId: Int)
    
    fun delete(dataSource: DataSource)
    
    fun count(): Long
}