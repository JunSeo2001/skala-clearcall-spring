# Skala ClearCall Spring

ClearCall 서비스의 백엔드 애플리케이션입니다. Spring Boot를 기반으로 작성되었으며, 통화 데이터 관리 및 사용자 인증을 담당하고 심층 분석을 위해 외부 FastAPI 서버와 연동됩니다.

## 🛠 기술 스택

- **Java**: 21
- **Framework**: Spring Boot 3.x
- **Database**: MariaDB
- **ORM**: Spring Data JPA
- **Migration**: Flyway
- **Documentation**: SpringDoc OpenAPI (Swagger)

## ⚙️ 환경 설정 (Configuration)

`src/main/resources/application.yml` 파일에서 주요 설정을 관리합니다.

### 데이터베이스
기본적으로 로컬 MariaDB 3379 포트를 사용하도록 설정되어 있습니다.
**주의: 애플리케이션 실행 전 `callcenter` 스키마가 반드시 존재해야 합니다.**
```yaml
datasource:
  url: jdbc:mariadb://localhost:3379/callcenter
  username: calluser
  password: callpass
```

### 🔐 JWT 및 FastAPI 인증 연동 (중요)

이 프로젝트는 **FastAPI 서버와 통신할 때 보안을 위해 JWT(JSON Web Token) 서명 키를 공유**합니다.
Spring Boot 애플리케이션과 FastAPI 서버는 서로 동일한 `access-secret`을 가지고 있어야 토큰을 올바르게 검증할 수 있습니다.

`application.yml` 내의 다음 설정이 인증의 핵심입니다:

```yaml
jwt:
  access-secret: ${JWT_ACCESS_SECRET:change-this-access-secret-please-32bytes-minimum}
```

*   **인증 메커니즘**: Spring Boot에서 발급한 Access Token을 사용하여 FastAPI 엔드포인트를 호출하면, FastAPI 서버는 위 `access-secret` 값을 키로 사용하여 토큰의 서명을 검증하고 요청을 승인합니다.
*   **배포 시 주의사항**: 배포 환경에서는 `JWT_ACCESS_SECRET` 환경 변수를 통해 예측 불가능한 긴 문자열로 키를 변경해야 하며, **FastAPI 서버의 환경 변수에도 반드시 동일한 값을 설정**해야 합니다.

## 🚀 실행 방법

### 1. 사전 요구 사항
*   JDK 21 이상
*   MariaDB 실행 (Port: 3379, Schema: `callcenter`)
    *   **필수**: `CREATE DATABASE callcenter;` 명령어로 스키마를 미리 생성해주세요.
*   FastAPI 서버 실행 (기본 URL: `http://localhost:8000`)

### 2. 애플리케이션 실행
```bash
./gradlew bootRun
```

### 3. API 문서 확인
서버가 정상적으로 실행되면 아래 주소에서 Swagger UI를 통해 API 명세를 확인할 수 있습니다.
*   http://localhost:8080/swagger-ui/index.html

## 📂 주요 폴더 구조

*   `client`: FastAPI 등 외부 서비스와의 통신을 담당하는 클라이언트 코드
*   `security`: JWT 인증 필터, 핸들러 등 보안 관련 설정
*   `entity`: DB 테이블과 매핑되는 JPA 엔티티
*   `config`: Spring 설정 클래스