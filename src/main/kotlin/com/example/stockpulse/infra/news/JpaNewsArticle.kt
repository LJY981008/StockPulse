package com.example.stockpulse.infra.news

import com.example.stockpulse.domain.company.Company
import com.example.stockpulse.domain.datasource.DataSource
import com.example.stockpulse.domain.news.NewsArticle
import com.example.stockpulse.infra.company.JpaCompany
import com.example.stockpulse.infra.datasource.JpaDataSource
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "news_article")
data class JpaNewsArticle(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "article_id", columnDefinition = "uuid")
    val articleId: UUID,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    val jpaCompany: JpaCompany,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id", nullable = false)
    val jpaDataSource: JpaDataSource,
    @Column(name = "original_url", unique = true, nullable = false, length = 512)
    val originalUrl: String,
    @Column(name = "title", nullable = false)
    val title: String,
    @Column(name = "content", columnDefinition = "TEXT")
    val content: String? = null,
    @Column(name = "published_at", nullable = false)
    val publishedAt: LocalDateTime,
    @Column(name = "collected_at", nullable = false)
    val collectedAt: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        fun JpaNewsArticle.toNewsArticle(
            company: Company,
            dataSource: DataSource,
        ): NewsArticle {
            return NewsArticle(
                articleId = this.articleId,
                company = company,
                dataSource = dataSource,
                originalUrl = this.originalUrl,
                title = this.title,
                content = this.content,
                publishedAt = this.publishedAt,
                collectedAt = this.collectedAt,
            )
        }

        fun NewsArticle.toJpaNewsArticle(
            jpaCompany: JpaCompany,
            jpaDataSource: JpaDataSource,
        ): JpaNewsArticle {
            return JpaNewsArticle(
                articleId = this.articleId,
                jpaCompany = jpaCompany,
                jpaDataSource = jpaDataSource,
                originalUrl = this.originalUrl,
                title = this.title,
                content = this.content,
                publishedAt = this.publishedAt,
                collectedAt = this.collectedAt,
            )
        }
    }
}
