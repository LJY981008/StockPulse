package com.example.stockpulse.infra.news

import com.example.stockpulse.domain.company.Company
import com.example.stockpulse.domain.datasource.DataSource
import com.example.stockpulse.domain.news.NewsArticle
import com.example.stockpulse.domain.news.NewsArticleRepositoryPort
import com.example.stockpulse.infra.company.CompanyJpaRepository
import com.example.stockpulse.infra.company.JpaCompany.Companion.toCompany
import com.example.stockpulse.infra.datasource.DataSourceJpaRepository
import com.example.stockpulse.infra.datasource.JpaDataSource.Companion.toDataSource
import com.example.stockpulse.infra.news.JpaNewsArticle.Companion.toJpaNewsArticle
import com.example.stockpulse.infra.news.JpaNewsArticle.Companion.toNewsArticle
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Repository
class NewsArticleRepository(
    private val newsArticleJpaRepository: NewsArticleJpaRepository,
    private val companyJpaRepository: CompanyJpaRepository,
    private val dataSourceJpaRepository: DataSourceJpaRepository,
) : NewsArticleRepositoryPort {
    override fun findByCompanyOrderByPublishedAtDesc(
        company: Company,
        page: Int,
        size: Int,
    ): List<NewsArticle> {
        val jpaCompany =
            companyJpaRepository.findByStockCode(company.stockCode)
                ?: return emptyList()

        val pageable = PageRequest.of(page, size)
        return newsArticleJpaRepository.findByJpaCompanyOrderByPublishedAtDesc(jpaCompany, pageable)
            .content
            .map { it.toNewsArticle(company, it.jpaDataSource.toDataSource()) }
    }

    override fun findByDataSourceOrderByPublishedAtDesc(
        dataSource: DataSource,
        page: Int,
        size: Int,
    ): List<NewsArticle> {
        val jpaDataSource =
            dataSourceJpaRepository.findBySourceName(dataSource.sourceName)
                ?: return emptyList()

        val pageable = PageRequest.of(page, size)
        return newsArticleJpaRepository.findByJpaDataSourceOrderByPublishedAtDesc(jpaDataSource, pageable)
            .content
            .map { it.toNewsArticle(it.jpaCompany.toCompany(), dataSource) }
    }

    override fun findByPublishedAtBetweenOrderByPublishedAtDesc(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        page: Int,
        size: Int,
    ): List<NewsArticle> {
        val pageable = PageRequest.of(page, size)
        return newsArticleJpaRepository.findByPublishedAtBetweenOrderByPublishedAtDesc(startDate, endDate, pageable)
            .content
            .map { it.toNewsArticle(it.jpaCompany.toCompany(), it.jpaDataSource.toDataSource()) }
    }

    override fun findByCompanyAndPublishedAtBetweenOrderByPublishedAtDesc(
        company: Company,
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        page: Int,
        size: Int,
    ): List<NewsArticle> {
        val jpaCompany =
            companyJpaRepository.findByStockCode(company.stockCode)
                ?: return emptyList()

        val pageable = PageRequest.of(page, size)
        return newsArticleJpaRepository.findByJpaCompanyAndPublishedAtBetweenOrderByPublishedAtDesc(
            jpaCompany,
            startDate,
            endDate,
            pageable,
        ).content
            .map { it.toNewsArticle(company, it.jpaDataSource.toDataSource()) }
    }

    override fun existsByOriginalUrl(originalUrl: String): Boolean {
        return newsArticleJpaRepository.existsByOriginalUrl(originalUrl)
    }

    override fun findByOriginalUrl(originalUrl: String): NewsArticle? {
        val jpaNewsArticle =
            newsArticleJpaRepository.findByOriginalUrl(originalUrl)
                ?: return null

        return jpaNewsArticle.toNewsArticle(
            jpaNewsArticle.jpaCompany.toCompany(),
            jpaNewsArticle.jpaDataSource.toDataSource(),
        )
    }

    override fun countByCompany(company: Company): Long {
        val jpaCompany =
            companyJpaRepository.findByStockCode(company.stockCode)
                ?: return 0

        return newsArticleJpaRepository.countByJpaCompany(jpaCompany)
    }

    override fun save(newsArticle: NewsArticle): NewsArticle {
        val jpaCompany =
            companyJpaRepository.findByStockCode(newsArticle.company.stockCode)
                ?: throw IllegalArgumentException("Company not found: ${newsArticle.company.stockCode}")

        val jpaDataSource =
            dataSourceJpaRepository.findBySourceName(newsArticle.dataSource.sourceName)
                ?: throw IllegalArgumentException("DataSource not found: ${newsArticle.dataSource.sourceName}")

        val savedJpaNewsArticle =
            newsArticleJpaRepository.save(
                newsArticle.toJpaNewsArticle(jpaCompany, jpaDataSource),
            )

        return savedJpaNewsArticle.toNewsArticle(
            newsArticle.company,
            newsArticle.dataSource,
        )
    }

    override fun findById(articleId: UUID): NewsArticle? {
        val jpaNewsArticle =
            newsArticleJpaRepository.findById(articleId)
                .orElse(null) ?: return null

        return jpaNewsArticle.toNewsArticle(
            jpaNewsArticle.jpaCompany.toCompany(),
            jpaNewsArticle.jpaDataSource.toDataSource(),
        )
    }

    override fun findAll(): List<NewsArticle> {
        return newsArticleJpaRepository.findAll()
            .map { it.toNewsArticle(it.jpaCompany.toCompany(), it.jpaDataSource.toDataSource()) }
    }

    override fun existsById(articleId: UUID): Boolean {
        return newsArticleJpaRepository.existsById(articleId)
    }

    override fun count(): Long {
        return newsArticleJpaRepository.count()
    }

    override fun saveAll(newsArticles: List<NewsArticle>): List<NewsArticle> {
        val savedArticles =
            newsArticles.map { newsArticle ->
                val jpaCompany =
                    companyJpaRepository.findByStockCode(newsArticle.company.stockCode)
                        ?: throw IllegalArgumentException("Company not found: ${newsArticle.company.stockCode}")

                val jpaDataSource =
                    dataSourceJpaRepository.findBySourceName(newsArticle.dataSource.sourceName)
                        ?: throw IllegalArgumentException("DataSource not found: ${newsArticle.dataSource.sourceName}")

                newsArticle.toJpaNewsArticle(jpaCompany, jpaDataSource)
            }

        return newsArticleJpaRepository.saveAll(savedArticles)
            .map { it.toNewsArticle(it.jpaCompany.toCompany(), it.jpaDataSource.toDataSource()) }
    }

    override fun deleteById(articleId: UUID) {
        newsArticleJpaRepository.deleteById(articleId)
    }

    override fun delete(newsArticle: NewsArticle) {
        val jpaCompany =
            companyJpaRepository.findByStockCode(newsArticle.company.stockCode)
                ?: throw IllegalArgumentException("Company not found: ${newsArticle.company.stockCode}")

        val jpaDataSource =
            dataSourceJpaRepository.findBySourceName(newsArticle.dataSource.sourceName)
                ?: throw IllegalArgumentException("DataSource not found: ${newsArticle.dataSource.sourceName}")

        newsArticleJpaRepository.delete(newsArticle.toJpaNewsArticle(jpaCompany, jpaDataSource))
    }
}
