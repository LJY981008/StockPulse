package com.example.stockpulse.infra.datasource

import com.example.stockpulse.domain.datasource.DataSource
import com.example.stockpulse.domain.datasource.DataSourceRepositoryPort
import com.example.stockpulse.infra.datasource.JpaDataSource.Companion.toDataSource
import com.example.stockpulse.infra.datasource.JpaDataSource.Companion.toJpaDataSource
import org.springframework.stereotype.Repository

@Repository
class DataSourceRepository(
    private val dataSourceJpaRepository: DataSourceJpaRepository
) : DataSourceRepositoryPort {

    // Read operations
    override fun findById(sourceId: Int): DataSource? {
        return dataSourceJpaRepository.findById(sourceId)
            .map { it.toDataSource() }
            .orElse(null)
    }

    override fun findBySourceName(sourceName: String): DataSource? {
        return dataSourceJpaRepository.findBySourceName(sourceName)?.toDataSource()
    }

    override fun findAll(): List<DataSource> {
        return dataSourceJpaRepository.findAll().map { it.toDataSource() }
    }

    // Check operations
    override fun existsBySourceName(sourceName: String): Boolean {
        return dataSourceJpaRepository.existsBySourceName(sourceName)
    }
    
    override fun existsById(sourceId: Int): Boolean {
        return dataSourceJpaRepository.existsById(sourceId)
    }

    // Write operations
    override fun save(dataSource: DataSource): DataSource {
        val savedJpaDataSource = dataSourceJpaRepository.save(dataSource.toJpaDataSource())
        return savedJpaDataSource.toDataSource()
    }
    
    override fun saveAll(dataSources: List<DataSource>): List<DataSource> {
        val jpaDataSources = dataSources.map { it.toJpaDataSource() }
        return dataSourceJpaRepository.saveAll(jpaDataSources)
            .map { it.toDataSource() }
    }
    
    override fun deleteById(sourceId: Int) {
        dataSourceJpaRepository.deleteById(sourceId)
    }
    
    override fun delete(dataSource: DataSource) {
        dataSourceJpaRepository.delete(dataSource.toJpaDataSource())
    }
    
    override fun count(): Long {
        return dataSourceJpaRepository.count()
    }
}