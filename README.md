# ClearCall

상담원 통화 악성민원 AI 프로젝트의 메인 백엔드(Spring Boot)입니다.

## 로컬 실행
1) MariaDB 실행
```bash
docker compose up -d
```

2) 애플리케이션 실행
```bash
./gradlew bootRun
```

## 환경변수
JWT 시크릿은 예시 값이므로 반드시 환경변수로 교체하세요.
- `JWT_ACCESS_SECRET`
- `JWT_CALL_SECRET`

예시:
```bash
export JWT_ACCESS_SECRET="your-access-secret-32bytes-minimum"
export JWT_CALL_SECRET="your-call-secret-32bytes-minimum"
```
