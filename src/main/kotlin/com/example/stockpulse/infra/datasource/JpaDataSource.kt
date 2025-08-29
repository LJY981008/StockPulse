package com.example.stockpulse.infra.datasource

import com.example.stockpulse.domain.datasource.DataSource
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "data_source")
data class JpaDataSource(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "source_id", columnDefinition = "uuid")
    val sourceId: UUID,
    @Column(name = "source_name", unique = true, nullable = false, length = 50)
    val sourceName: String,
    @Column(name = "base_url")
    val baseUrl: String? = null,
) {
    companion object {
        fun JpaDataSource.toDataSource(): DataSource {
            return DataSource(
                sourceId = this.sourceId,
                sourceName = this.sourceName,
                baseUrl = this.baseUrl,
            )
        }

        fun DataSource.toJpaDataSource(): JpaDataSource {
            return JpaDataSource(
                sourceId = this.sourceId,
                sourceName = this.sourceName,
                baseUrl = this.baseUrl,
            )
        }
    }
}
