# 기여 가이드 (Contributing Guide)

## 개발 환경

### 개발 컨테이너

프로젝트는 일관된 개발 환경을 제공하기 위해 개발 컨테이너를 사용합니다. 이 컨테이너에는 다양한 편의 기능이 포함되어 있으며, `scripts` 폴더에 스크립트를 추가하고 실행 권한을 부여하면 터미널에서 바로 실행할 수 있습니다.

현재 포함된 스크립트는 다음과 같습니다:

- `unlock`: 비밀 정보를 해제
- `dev`: 개발 서버 실행
- `deploy`: 프로젝트 배포
- `build`: 프로젝트 빌드

### 개발 환경 설정

#### 요구 사항

- `x86-64` 또는 `AMD64` 아키텍처
- `Docker` 또는 동등한 컨테이너 런타임
- `Visual Studio Code` 및 `Remote - Containers` 확장

#### 설정 절차

1. 저장소 복제: `git clone https://github.com/username/uos-easy-life.git`
2. VS Code에서 프로젝트 열기
3. 컨테이너에서 프로젝트 열기: `Ctrl+Shift+P` → `Reopen in Container`
4. `unlock` 명령 실행하여 비밀 정보 해제

> **참고**: 비밀 정보를 해제하려면 Unlock Token이 필요합니다. Unlock Token은 프로젝트 관리자에게 문의하세요.

#### 로컬 개발 환경

1. `dev` 명령 실행
2. 브라우저에서 `http://localhost:5173` 열기

### 배포

1. `deploy` 명령 실행

## 기여 가이드라인

### 기여 방법

1. 새로운 브랜치 생성: `git checkout -b feature/feature-name`
2. 변경 사항 커밋: `git commit -am 'Add new feature'`
3. 브랜치 푸시: `git push origin feature/feature-name`
4. Pull Request 생성

> **참고**:
>
> - 새로운 Pull Request를 생성하기 전에 항상 `main` 브랜치에 리베이스하세요.
> - `main` 브랜치에 직접 푸시하지 마세요.

### 브랜치 전략

- 브랜치 네이밍:

  - `feature/feature-name`: 새로운 기능
  - `bugfix/bug-name`: 버그 수정
  - `hotfix/hotfix-name`: 긴급 버그 수정
  - `docs/document-name`: 문서 변경

- 변경 사항 관리:
  - `master` 브랜치는 태그로 `staging`/`production`을 관리합니다.
  - 배포 버전은 Kubernetes manifest를 Git으로 트래킹하고 커밋에 태그를 붙여 관리합니다.

### 커밋 메시지 규칙

- 커밋 메시지 형식:
  ```
  If this commit is applied, it will <commit message>
  ```
- 예시:
- 좋은 예: `Fix session reset issue`, `Add contribution guidelines document`
- 나쁜 예: `Fix`, `Login implementation`

### Pull Request 규칙

1. **리뷰어 지정**: 모든 Pull Request에는 최소 1명의 리뷰어를 지정하여 코드 품질 검토
2. **리뷰 절차**:

- Github Pull Request 기능을 활용하여 리뷰 진행
- 리뷰어 확인 항목:
  - 코드 품질 및 스타일 가이드 준수 여부
  - 변경 사항이 프로젝트 요구사항을 충족하는지 여부
  - 잠재적 오류 및 개선점 피드백

### 코드 스타일

- **네이밍 컨벤션**: CamelCase (Java 표준)
- **백엔드 스타일 가이드**: [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- **프론트엔드 스타일 가이드**: [Prettier Default Style](https://prettier.io/docs/en/options.html)

### 용어 정리

- 프로젝트 전체에서 사용할 Ubiquitous Language는 별도 문서로 관리될 예정입니다.
