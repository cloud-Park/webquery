# Webquery

## 모듈 구조

```
webquery
├── webquery-api          # Web 레이어 (Controller, ExceptionHandler)
├── webquery-application  # 도메인 레이어 (Entity, Service, Repository)
└── webquery-batch        # 배치
```

## 환경 설정

| 프로파일 | 설명 |
|----------|------|
| default  | 로컬 개발 (application.yaml) |
| dev      | 개발 서버 |
| stage    | 스테이징 서버 |
| prod     | 운영 서버 |

**실행 방법**

```bash
# 로컬 (기본값)
./gradlew :webquery-api:bootRun

# 환경 지정
./gradlew :webquery-api:bootRun --args='--spring.profiles.active=dev'

# JAR 실행
java -jar webquery-api.jar --spring.profiles.active=prod
```

dev/stage/prod 환경은 아래 환경변수가 필요합니다.

| 환경변수 | 설명 |
|----------|------|
| DB_URL | DB 접속 URL |
| DB_USERNAME | DB 유저명 |
| DB_PASSWORD | DB 비밀번호 |

## Lint

[ktlint](https://github.com/pinterest/ktlint) 를 사용합니다.

| 단계 | 동작 |
|------|------|
| 로컬 빌드 | 검사 없음 |
| 커밋 직전 (pre-commit hook) | `ktlintFormat` → `ktlintCheck` 자동 실행 |
| CI | `ktlintCheck` |

### 개발 환경 최초 설정 (1회)

```bash
git config core.hooksPath .githooks
```

### 수동으로 포맷팅할 때

```bash
./gradlew ktlintFormat
```

## API

API는 호출 주체에 따라 세 가지 prefix로 구분합니다.

| Prefix | 대상 | 설명 |
|--------|------|------|
| `/api/v1` | 외부 클라이언트 (앱, 웹) | 일반 사용자용 API |
| `/api-internal/v1` | 내부 서비스 간 통신 | 서버 to 서버, 외부 노출 안 함 |
| `/api-admin/v1` | 어드민 | 관리자용 API |

### HelloWorld `/api/v1`

| Method | URL | 설명 |
|--------|-----|------|
| GET | `/api/v1/hello` | 전체 조회 |
| GET | `/api/v1/hello/{id}` | 단건 조회 |
| POST | `/api/v1/hello` | 생성 |
| PUT | `/api/v1/hello/{id}` | 수정 |
| DELETE | `/api/v1/hello/{id}` | 삭제 |

## 에러 응답

```json
// 일반 에러
{
  "code": "HELLO_WORLD_NOT_FOUND",
  "message": "HelloWorld를 찾을 수 없습니다: 1"
}

// Validation 에러
{
  "code": "INVALID_REQUEST",
  "message": "잘못된 요청입니다",
  "errors": [
    { "field": "message", "message": "공백일 수 없습니다" }
  ]
}
```