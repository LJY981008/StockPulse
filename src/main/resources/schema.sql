-- StockPulse 주식 뉴스 수집 시스템 데이터베이스 스키마

-- 1. 수집 대상 기업 테이블
-- 역할: 뉴스를 수집할 기업들의 기준 정보(Master Data)를 관리
CREATE TABLE company (
    company_id UUID PRIMARY KEY,
    stock_code VARCHAR(10) UNIQUE NOT NULL,
    company_name VARCHAR(100) NOT NULL,
    search_keyword VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT true NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 2. 데이터 출처 테이블
-- 역할: 뉴스 수집 출처(네이버, 구글 뉴스 등)를 관리
CREATE TABLE data_source (
    source_id UUID PRIMARY KEY,
    source_name VARCHAR(50) UNIQUE NOT NULL,
    base_url VARCHAR(255)
);

-- 3. 수집된 뉴스 원본 테이블
-- 역할: 크롤링한 뉴스 기사의 원본 데이터를 저장
CREATE TABLE news_article (
    article_id UUID PRIMARY KEY,
    company_id UUID NOT NULL,
    source_id UUID NOT NULL,
    original_url VARCHAR(512) UNIQUE NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    published_at TIMESTAMP NOT NULL,
    collected_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    -- 외래키 제약조건
    CONSTRAINT fk_news_article_company 
        FOREIGN KEY (company_id) REFERENCES company(company_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_news_article_source 
        FOREIGN KEY (source_id) REFERENCES data_source(source_id)
        ON DELETE CASCADE
);

-- -- 인덱스 생성 (성능 최적화)
-- -- 기업별 뉴스 조회 최적화
-- CREATE INDEX idx_news_article_company_id ON news_article(company_id);
-- -- 출처별 뉴스 조회 최적화
-- CREATE INDEX idx_news_article_source_id ON news_article(source_id);
-- -- 발행일자별 뉴스 조회 최적화 (최신 뉴스 조회 시 자주 사용)
-- CREATE INDEX idx_news_article_published_at ON news_article(published_at DESC);
-- -- 수집일자별 조회 최적화
-- CREATE INDEX idx_news_article_collected_at ON news_article(collected_at DESC);
-- -- 복합 인덱스: 기업별 + 발행일자 조회 최적화
-- CREATE INDEX idx_news_article_company_published ON news_article(company_id, published_at DESC);

-- 주석 추가 (PostgreSQL)
COMMENT ON TABLE company IS '뉴스 수집 대상 기업 정보';
COMMENT ON COLUMN company.company_id IS '기업 고유 식별자';
COMMENT ON COLUMN company.stock_code IS '주식 종목 코드 (예: 005930)';
COMMENT ON COLUMN company.company_name IS '정식 회사명 (예: 삼성전자)';
COMMENT ON COLUMN company.search_keyword IS '뉴스 검색 시 사용할 키워드';
COMMENT ON COLUMN company.is_active IS '현재 이 기업의 뉴스를 수집할지 여부';

COMMENT ON TABLE data_source IS '뉴스 데이터 출처 정보';
COMMENT ON COLUMN data_source.source_id IS '데이터 출처 고유 식별자';
COMMENT ON COLUMN data_source.source_name IS '출처 이름 (예: Naver News)';
COMMENT ON COLUMN data_source.base_url IS '출처 대표 URL';

COMMENT ON TABLE news_article IS '수집된 뉴스 기사 원본 데이터';
COMMENT ON COLUMN news_article.article_id IS '뉴스 기사 고유 식별자';
COMMENT ON COLUMN news_article.company_id IS '관련 기업 참조';
COMMENT ON COLUMN news_article.source_id IS '뉴스 출처 참조';
COMMENT ON COLUMN news_article.original_url IS '기사 원문 URL (중복 수집 방지용)';
COMMENT ON COLUMN news_article.title IS '기사 제목';
COMMENT ON COLUMN news_article.content IS '기사 본문 또는 요약';
COMMENT ON COLUMN news_article.published_at IS '기사 발행 시각';
COMMENT ON COLUMN news_article.collected_at IS '시스템 수집 시각';