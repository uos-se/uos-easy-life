# UOS Easy Life (가칭)

UOS Easy Life는 복잡한 학교 학사 정보 시스템의 접근성을 개선하여 학생들이 필요한 정보를 더 쉽게 찾고 이용할 수 있도록 돕는 통합 시스템입니다. 이 프로젝트는 백엔드로 Spring Boot, 프론트엔드로 React를 사용하여 개발되었으며, 학생들에게 직관적인 사용자 경험을 제공하고 신속한 데이터 접근이 가능하도록 설계되었습니다.

## 팀 구성

- **팀명**: Graduos
- **팀원**:
  - 2019920003 권준호
  - 2019920016 김원빈
  - 2019920048 정민혁
  - 2019920055 채민관
  - 2020920025 박정익
  - 2020920051 이현제

## 프로젝트 범위

프로젝트는 **1차**, **2차**, **3차** 목표로 나뉘어 있으며, 각 단계에서의 목표는 아래와 같습니다:

1. **1차 목표**: 유저의 현재까지의 대학행정정보를 기반으로 졸업을 위해 남은 요건을 표시
   - 남은 이수 학점, 공학 소양, 설계 학점 등 표시
2. **2차 목표**: 선/후수 과목을 고려하여 다음 학기에 수강할 수 있는 과목을 추천하고 강조
   - 졸업을 위해 해당 학기에 수강해야 하는 과목을 표시
3. **3차 목표**: 졸업학기까지의 시간표를 설계할 수 있는 유틸리티 제공

본 프로젝트는 최소 **2차 목표**를 달성하는 것을 목표로 하고 있으며, 여건이 된다면 **3차 목표**까지 구현할 계획입니다.

## 프로젝트 기간

2024학년도 2학기동안 진행합니다.

## 주요 기능

- **수강신청 시스템 개선**: 사용자가 더 쉽게 강의 정보를 조회하고 수강 신청을 할 수 있도록 직관적인 UI 제공
- **성적 확인**: 사용자가 자신의 성적을 빠르게 확인하고 성적표를 조회할 수 있는 기능
- **시간표 관리**: 사용자가 자신의 시간표를 자동으로 생성하고 관리할 수 있는 기능
- **공지사항 확인**: 학교에서 제공하는 공지사항을 한눈에 확인 가능
- **캠퍼스 지도**: 학교 내 주요 시설의 위치 및 길찾기 기능 제공
- **커뮤니티 기능**: 학생 간의 소통을 위한 게시판 및 Q&A 시스템
- **졸업 요건 확인**: 사용자의 대학행정정보를 바탕으로 남은 졸업 요건을 표시
  - 남은 이수 학점, 공학 소양, 설계 학점 등 세부 요건 확인 가능
- **다음 학기 추천 과목 표시**: 졸업 요건을 충족하기 위해 다음 학기에 수강할 수 있는 과목 중에서 추천 과목을 표시
- **졸업까지 시간표 설계**: 사용자가 졸업할 때까지의 전체 시간표를 설계할 수 있는 유틸리티 제공

- Demo Video
<img src="https://github.com/user-attachments/assets/79bfe99a-1d83-434e-be98-5b54bdb7b3d7">



## 프로젝트 제약사항

### 기술적 제약 사항

- 소프트웨어는 Windows, Linux, Mac 운영체제에서 모두 실행 가능해야 한다.
- 개발 환경은 VSCode DevContainer로 통일하여 모두 같은 환경에서 작업 가능해야 한다.
- 프론트엔드 개발은 React.js에 Typescript를 사용한다.
- 백엔드 개발은 Spring프레임워크를 사용한다.

### 환경적 제약 사항

- 개발된 소프트웨어는 Kubernetes와 OnPromise Server에서 배포된다.
- 코드의 버전 관리는 Git과 연동된다.

### 정책적 제약 사항

- 이루넷 시스템을 이용하기 때문에 유저가 사용하고 있는 도중에는 데이터를 가지고 올 수 없다.

### 신뢰성 요구사항

- 유저 이루넷 ID/PW 정보는 Database에 저장되지 않아야 한다.
- 유저의 승인 없이 이루넷의 데이터에 접근하지 않아야 한다.
- 모든 데이터 접근과 변경 작업은 기록돼야 한다.

### 비용적 제약사항

- OnPromise Server는 비용적 한계로 인해 기존에 보유한 리소스를 최대한 활용한다.
- 신규 하드웨어 구매 없이 현재 사용 중인 서버 인프라를 활용한다.

### 시간 제약사항

- 프로젝트는 2024년 2학기 이내에 완료되어야 한다.

## 아키텍쳐

<img width="470" alt="image" src="https://github.com/user-attachments/assets/a59fa746-a907-4332-a607-6693a65caff6">

- **모델**: 도메인 모델 (연산의 대상)을 정의하는 클래스
- **서비스**: 비즈니스 로직을 처리하는 서비스 클래스
- **컨트롤러**: 애플리케이션의 진입점, 컨트롤러
- **레포지토리**: 데이터 저장소 접근

### 서비스 -> 모델

서비스 계층이 모델을 조작

### 서비스 -> 레포지토리, 인터페이스

구체 레이어의 접근은 인터페이스를 통해 의존성 역전

### 구현체 -> 레포지토리, 인터페이스

구현체는 코어 레이어에서 정의된 인터페이스를 상속

## 기술 스택

### 프론트엔드

- **React**: 사용자 인터페이스를 빠르고 효율적으로 구축하기 위한 라이브러리
- **React Router**: 페이지 간 네비게이션을 담당하는 라우팅 라이브러리

### 백엔드

- **Spring Boot**: 백엔드 서버 구축을 위한 경량화된 자바 프레임워크

### 기타 도구

- **Gradle**: 프로젝트 빌드 및 라이브러리 관리를 위한 도구
- **Docker**: 어플리케이션 컨테이너화를 통해 배포와 관리를 쉽게 하기 위한 도구
- **Nginx**: 리버스 프록시 및 정적 파일 서빙을 위한 웹 서버
- **Git**: 버전 관리를 위한 분산형 VCS (Version Control System)
- **Swagger**: REST API 문서화를 위한 오픈소스 툴

## 설치 가이드

### 개발 컨테이너

프로젝트는 일관된 개발 환경을 제공하기 위해 개발 컨테이너를 사용합니다. 이 컨테이너에는 다양한 편의 기능이 포함되어 있으며, `scripts` 폴더에 스크립트를 추가하고 실행 권한을 부여하면 터미널에서 바로 실행할 수 있습니다.

현재 포함된 스크립트는 다음과 같습니다:

- `unlock`: 비밀 정보를 해제
- `dev`: 개발 서버 실행
- `deploy`: 프로젝트 배포
- `build`: 프로젝트 빌드

### 로컬 개발 환경

1. `dev` 명령 실행
2. 브라우저에서 `http://localhost:5173` 열기

### 배포

1. `deploy` 명령 실행

## 개발 문서

- 설계 문서: [docs/design.md](artifacts/[1010]design.md)
- 기여 방법: [docs/contributing.md](artifacts/[1123]contribution%20guide.md)

## Project Deliverables

### 요구사항 분석 명세서

- [Pdf file로 확인](https://github.com/uos-se/uos-easy-life/blob/aec3bec7dc19907d24b8e14652272ab137567734/artifacts/%5B1013%5D%E1%84%89%E1%85%A9%E1%84%91%E1%85%B3%E1%84%90%E1%85%B3%E1%84%8B%E1%85%B0%E1%84%8B%E1%85%A5%20%E1%84%8B%E1%85%AD%E1%84%80%E1%85%AE%E1%84%89%E1%85%A1%E1%84%92%E1%85%A1%E1%86%BC%20%E1%84%87%E1%85%AE%E1%86%AB%E1%84%89%E1%85%A5%E1%86%A8%20%E1%84%86%E1%85%A7%E1%86%BC%E1%84%89%E1%85%A6%E1%84%89%E1%85%A5.pdf?raw=true)

### Architecture 및 Design Documents

- Software architeture: [pptx file](https://github.com/uos-se/uos-easy-life/blob/aec3bec7dc19907d24b8e14652272ab137567734/artifacts/%5B1117%5Ddesign/UML%20Diagrams.pptx?raw=true)
- Software Design: [pptx file](https://github.com/uos-se/uos-easy-life/blob/aec3bec7dc19907d24b8e14652272ab137567734/artifacts/%5B1117%5Ddesign/UML%20Diagrams.pptx?raw=true)
- UI Design: [pptx file](https://github.com/uos-se/uos-easy-life/blob/aec3bec7dc19907d24b8e14652272ab137567734/artifacts/%5B1117%5Ddesign/UI%20Design.pptx?raw=true)

### Coding Standard

- **Naming Convention**: Camel case, Java standard
- **백엔드**: Google Java Style Guide [링크](https://google.github.io/styleguide/javaguide.html)
- **프론트엔드**: Prettier default style [링크](https://prettier.io/docs/en/options.html)

### Code

자세한 내용: [contribution guide 확인](https://github.com/uos-se/uos-easy-life/blob/aec3bec7dc19907d24b8e14652272ab137567734/artifacts/%5B1123%5Dcontribution%20guide.md)

- Branch Strategy
  - Git은 master 에 tag로 staging / production, feat/fix/refactor/docs 등으로 변경사항 관리
    - feat: 새로운 기능 개발 작업.
    - fix: 버그 수정 작업.
    - refactor: 코드 구조 개선 작업.
    - docs: 문서 작성 및 업데이트 작업.
  - 배포 버전은 Kubernetes manifest를 git으로 track하고 commit에 tagging하여 관리
- PR Rule
  - 리뷰어 지정
    - 모든 Pull Request에는 최소 1명의 리뷰어를 지정하여 코드 품질을 검토
  - 리뷰 절차
    - 리뷰 절차는 Github Pull Request 기능을 이용하여 진행함
    - 리뷰어는 다음을 확인:
      - 코드 품질 및 스타일 가이드 준수 여부.
      - 변경 사항이 프로젝트의 요구사항을 만족하는지 확인.
      - 잠재적인 오류나 개선점을 피드백

### 테스트 케이스 및 결과

<img width="829" alt="image" src="https://github.com/user-attachments/assets/99801aaf-849e-49c5-b2cd-1e37a95a6e21">

<img width="829" alt="image" src="https://github.com/user-attachments/assets/6a2cd530-0661-4407-bcee-bab6f35f1f51">

## Repository Structure

- `/.devcontainer` - Dev container 설정
- `/artifacts` - 설계, 요구사항, 기여방법 등 문서
- `/backend` - 백엔드 모듈
- `/courseutil` - 서울시립대 수업 정보 스크래핑, 통합등 유틸리티
- `/frontend` - 프론트엔드 모듈
- `/kubernetes` - 쿠버네티스 설정 파일
- `/scripts` - CI/CD 자동화 스크립트
