# Skala Voice Project

Spring Boot 기반의 음성 관련 실험/프로젝트입니다.

## 빌드 및 실행
- 로컬 실행: `./gradlew bootRun`
- 빌드: `./gradlew build`

## 요구사항
- JDK 21 (또는 프로젝트에서 사용 중인 버전; Gradle toolchain으로 21 사용)
- Gradle wrapper 포함

> 팁: 로컬에 JDK 21이 설치되어 있지 않으면 `sdk use java 21-open`(SDKMAN) 또는 `jenv` 등을 사용해 설정하세요.

## GitHub에 올리기
1. 로컬 깃 초기화 및 커밋
```
git init
git checkout -b main
git add .
git commit -m "Initial commit"
```
2. GitHub에 새 원격 레포 생성 및 푸시 (GitHub CLI 사용 예)
```
gh repo create skala-voice-project --public --source=. --push
```

## 라이선스
원하시면 `LICENSE` 파일(MIT 등)을 추가해 드립니다.
