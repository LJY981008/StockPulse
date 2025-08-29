# Docker 사용 가이드

## PostgreSQL 데이터베이스 실행

### 1. 기본 운영 환경
```bash
# PostgreSQL 시작
docker-compose up -d

# 로그 확인
docker-compose logs -f postgres

# 중지
docker-compose down

# 데이터까지 완전 삭제
docker-compose down -v
```

### 2. 개발 환경 (권장)
```bash
# docker 폴더에서 실행하거나 프로젝트 루트에서 아래 명령어 사용

# 개발 환경용 PostgreSQL + pgAdmin 시작
docker-compose -f docker/docker-compose.dev.yml --env-file properties.env up -d

# 또는 docker 폴더에서
cd docker
docker-compose -f docker-compose.dev.yml --env-file ../properties.env up -d

# 로그 확인
docker-compose -f docker-compose.dev.yml logs -f

# 중지
docker-compose -f docker-compose.dev.yml down
```

## 데이터베이스 접속 정보

### 기본 환경
- **Host**: localhost
- **Port**: 5432
- **Database**: stockpulse
- **Username**: stockpulse
- **Password**: stockpulse123

### 개발 환경
- **Host**: localhost
- **Port**: 5433
- **Database**: stockpulse_dev
- **Username**: stockpulse_dev
- **Password**: dev123

### pgAdmin 접속 (개발 환경)
- **URL**: http://localhost:8080
- **Email**: admin@stockpulse.com
- **Password**: admin123

## 환경변수 설정

### properties.env 파일 사용 (기본)
기존에 생성된 `properties.env` 파일을 사용합니다:
```bash
# properties.env 파일 내용 확인
cat properties.env

# 필요에 따라 값 수정
vi properties.env
```

### 환경변수 직접 설정
```bash
export DB_HOST=localhost
export DB_PORT=5432
export DB_SCHEME=stockpulse
export DB_USERNAME=stockpulse
export DB_PASSWORD=stockpulse123
```

## 데이터베이스 관리

### 직접 접속
```bash
# Docker 컨테이너 내부로 접속 (개발 환경)
docker exec -it stockpulse-postgres-dev psql -U stockpulse_dev -d stockpulse_dev

# 또는 로컬에서 직접 접속 (properties.env 설정 사용)
psql -h localhost -p 5433 -U stockpulse_dev -d stockpulse_dev
```

### 자주 사용하는 SQL 명령어
```sql
-- 테이블 목록 확인
\dt

-- 테이블 구조 확인
\d company
\d news_article

-- 데이터 확인
SELECT * FROM company LIMIT 5;
SELECT * FROM data_source;
SELECT COUNT(*) FROM news_article;
```

## 트러블슈팅

### 포트 충돌 시
로컬에 PostgreSQL이 이미 설치되어 있다면 포트 충돌이 발생할 수 있습니다.
- docker-compose.yml에서 포트를 변경하세요 (예: "5433:5432")
- 환경변수 DB_PORT도 함께 변경하세요

### 데이터 초기화
```bash
# 모든 데이터 삭제 후 재시작
docker-compose down -v
docker-compose up -d
```

### 스키마 재생성
```bash
# 컨테이너 재시작 (schema.sql, data.sql 재실행)
docker-compose restart postgres
```