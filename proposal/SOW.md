# Statement of Work

## 목차
1. [Project Title](#1-project-title)
2. [Project Scope](#2-project-scope)
3. [Project Values/Motivations](#3-project-valuesmotivation)
4. [Project Duration](#4-project-duration)
5. [Project Description](#5-project-description)
6. [Expected Deliverables](#6-expected-deliverables)  
    6.1. [기능 요구사항](#61-기능-요구사항)  
    6.2. [유즈케이스](#62-유즈케이스)  
    6.3. [비기능 요구사항](#63-비기능-요구사항)  
    6.4. [아키텍쳐 및 설계 - High Level View](#64-아키텍쳐-및-설계---high-level-view)  
    6.5. [아키텍처 및 설계 - UML Diagramㄴ](#65-아키텍처-및-설계---uml-diagrams)  
        6.5.1. [Class Diagram for Static View](#651-class-diagram-for-static-view)  
        6.5.2. [Sequence Diagrams](#652-sequence-diagrams)  
    6.6. [UI Design](#66-ui-design-66-ui-design)  
    6.7. [Branch Strategy](#67-branch-strategy)  
    6.8. [PR Rule](#68-pr-rule)  
    6.9. [Coding Standard](#69-coding-standard)  
    6.10. [Source Code](#610-source-code)  
    6.11. [테스트 및 결과](#611-테스트-및-결과)  
7. [Project Constraints](#7-project-constraints)	
8. [Project Team Members](#8-project-team-members)  

## 1. Project Title

졸업해줘

## 2. Project Scope

서울시립대학교 컴퓨터과학부 2024년 소프트웨어공학 프로젝트로 Software Development Life-Cycle을 기반으로 객체지향 소프트웨어공학 방법론을 적용하여 서울시립대 학생이 학위 수료를 위해 알아야 하는 정보를 쉽게 제공하는 웹 기반 어플리케이션 개발

## 3. Project Values/Motivation

- 서울시립대의 학위 수료조건은 무척 복잡하다. 예시로 전공 필수와 전공 선택, 교양 필수와 선택, 공학인증, 설계학점, 선, 후수 과목 등 다양한 조건을 고려해야만 올바른 교육과정을 설계 가능하다.  
- 서울시립대 학사정보시스템은 학위 수료조건을 확인하기에 복잡한 UI를 제공한다.   
- 위 두가지 점을 고려하여, 우리는 서울시립대의 학위 수료조건과 관련된 정보를 자동으로 조합하여 이해하기 쉬운 UI로 제공하는 것을 목표로 한다.

## 4. Project Duration

2024년 2학기

## 5. Project Description

‘졸업해줘’는 서울시립대의 학위 수료조건 정보를 자동으로 조합하고, 이를 보다 이해하기 쉬운 UI로 시각화 하여 학생들이 자신만의 학업 계획을 효율적으로 세울 수 있도록 지원한다. 이를 통해 학생들이 학위 과정을 체계적으로 관리하고 졸업 요건 충족 여부를 쉽게 확인할 수 있는 환경을 제공한다.

## 6. Expected Deliverables

### 6.1 기능 요구사항

| ID | 구분 | 상세 | 비고 |
| ----- | ----- | ----- | ----- |
| FR001 | 졸업 정보 확인 | 시스템은 유저가 졸업까지 남은 학점을 공학소양, 설계, 전공 필수를 포함하여 카테고리별로,  이해하기 쉬운 형태로 보여줘야 한다. 시스템은 유저가 봉사 활동, 어학 성적을 포함한 졸업 시 요구되는 조건의 달성 정도 (시간/횟수/점수) 및 달성 여부를 제공해야 한다. |  |
| FR002 | 수강 계획 설계 | 시스템은 유저가 남은 학기동안 수강 가능한 과목들을 확인할 수 있게 해야한다. 시스템은 유저가 수강 가능한 과목들을 선/후수 과목 제약조건 아래 자유롭게 조합해 남은 학기동안 졸업에 필요한 학점들을 채울 수 있도록 하는 수강 계획 작성 기능을 제공해야 한다. |  |

### 6.2. 유즈케이스

| ID | 유스케이스 명 | 설명 | 우선 순위  |
| :---- | :---- | :---- | :---- |
| UC001 | 로그인 | 유저는 서울시립대학교 포털 ID/PW를 통해 본 시스템에 로그인할 수 있다.  | 1 |
| UC002 | 이루넷 데이터 동기화 | 유저는 시스템에서 데이터들을 활용할 수 있도록 이루넷에서 데이터를 가져오도록 시스템에게 지시한다. | 1 |
| UC003 | 졸업 요구 학점 요구 확인 | 유저는 본인이 졸업을 위해 필요한 학점을 카테고리별(공학인증학점, 설계학점, 교양/전공, 봉사활동, 어학)로 확인할 수 있다. | 2 |
| UC004 | 다음 학기 수강 가능 과목 리스트 확인 | 유저는 선후수과목, 공학인증, 설계학점, 교양/전공을 고려하여 다음 학기에 들어야하는 과목들의 리스트를 확인할 수 있다. | 3 |
| UC005 | 수강 계획시간표 작성 | 유저는 본인이 수강 가능한 과목들로 졸업 시까지의 수강 계획을시간표를 작성할 수 있다. 이때, 선후수과목 및 과목별 제약사항(수강할 수 없는 과, 설계과목 제약사항)의 이유로 불가능한 구조의 시간표를 작성하게 되면 그러한 수강 계획이 불가능함이 시각적으로 표시된다.변경토록 한다. | 4 |

### 6.3. 비기능 요구사항

| ID | 요구 항목 | 설명 |
| ----- | ----- | ----- |
| NF001 | Performance  | 서울시립대학교 재학생 약 8,700명이 1시간동안 집중적으로 서비스를 사용하는 상황을 고려, 서비스는 1초에 최대 3건 이상의 요청(로그인, 학점 조회를 포함)을 처리할 수 있어야 한다. |
| NF002 | Performance | 이루넷에 접속하여 정보를 가져오는 모든 기능에 대하여, 이루넷 서버에 과부하가 걸리지 않도록 데이터 동기화를 유저당 3시간 당 1회로 제한한다. |
| NF003 | Performance | 빠른 응답속도를 위해 강좌 정보, 유저의 수강 정보를 데이터베이스에 저장한다. |
| NF004 | Performance | 본인의 정보를 가져오는 기능에 대하여, 외부 시스템(이루넷)에서 정보를 가져오는 것을 감안하여, 최대 3초 이내에 처리해야한다. |
| NF005 | Security | 서버-클라이언트간 통신은 SSL을 통해 암호화되어야 한다. |
| NF006 | Security | 유저의 개인정보(수강 정보, 플랜)는 암호화되어야 한다. |
| NF007 | Security | 유저의 이루넷 ID/PW를 서버에 저장하면 안 된다. |
| NF008 | Compatibility | 유저는 디바이스의 운영 체제(Windows, Linux, Mac) 및 아키텍쳐(x86, ARM)에 무관하게 서비스를 사용할 수 있어야 한다. |
| NF009 | Compatibility | 서비스는 웹 브라우저 Chrome에서 사용 가능해야 한다. |
| NF010 | Availability | 연간 1% 미만의 downtime만이 허용된다. |
| NF011 | Maintainability | 졸업 정책의 추가(e.g. 학과 추가)와 변경을 쉽게 할 수 있어야 한다. |
| NF012 | Usability | 데스크톱 환경, 모바일 환경 모두에서 가독성이 좋아야 한다. |
| NF013 | Legal Compliance | 사법적인 절차에 위촉되지 않는 선에서 데이터를 가져온다. |

### 6.4. 아키텍쳐 및 설계 - High Level View

<img width="700" alt="highlevel_architecture" src="https://github.com/user-attachments/assets/e80b36cc-fe3f-4b43-b95f-ff80c9de3d2d" />

### 6.5. 아키텍처 및 설계 - UML Diagrams

#### 6.5.1 Class Diagram for Static View
  
<img width="700" alt="Class diagram" src="https://github.com/user-attachments/assets/30f9cde9-f0bf-4d72-8913-2f4861a36afa" />
  
#### 6.5.2 Sequence Diagrams

**Use case: UC001**

<img width="700" alt="seq diagram" src="https://github.com/user-attachments/assets/c90e4e4d-066b-4c80-b3c2-d19cdb6992d8" />

**Use case: UC002**

<img width="700" alt="seq diagram" src="https://github.com/user-attachments/assets/1cb1cd77-38d7-4eef-b17b-4b8764932b96" />

**Use case: UC003**

<img width="700" alt="seq diagram" src="https://github.com/user-attachments/assets/2025d940-c91c-4660-bb30-21e12f30e6e8" />

**Use case: UC004**

<img width="700" alt="seq diagram" src="https://github.com/user-attachments/assets/d7e90029-f442-4cfa-9de6-ff9f0a0af631" />

**Use case: UC005**

<img width="700" alt="seq diagram" src="https://github.com/user-attachments/assets/0cf2ce4e-476f-4949-8c22-1ea3b1795eb3" />

### 6.6. UI Design {#6.6.-ui-design}

**로그인 UI**  
<img width="700" alt="login ui" src="https://github.com/user-attachments/assets/4e5e092d-ffe1-4a20-95ca-bb8e92ea5fd4" />  

**유저 대시보드 UI**  
<img width="700" alt="dashboard1" src="https://github.com/user-attachments/assets/bc50c3de-0b75-4abd-b6f4-54bee334ee79" />  
  
<img width="700" alt="dashboard1" src="https://github.com/user-attachments/assets/d846b161-1f60-46e6-88ef-ef07848d247a" />

**Sketch: Design Concept**  
<img width="700" alt="design concept" src="https://github.com/user-attachments/assets/3009ca95-f690-49b1-a895-b59a03279749" />

### 6.7. Branch Strategy

- Git은 master 에 tag로 staging / production, feat/fix/refactor/docs 등으로 변경사항 관리  
  - feat: 새로운 기능 개발 작업.  
  - fix: 버그 수정 작업.  
  - refactor: 코드 구조 개선 작업.  
  - docs: 문서 작성 및 업데이트 작업.  
- 배포 버전은 Kubernetes manifest를 git으로 track하고 commit에 tagging하여 관리

### 6.8. PR Rule

- 리뷰어 지정  
  - 모든 Pull Request에는 최소 1명의 리뷰어를 지정하여 코드 품질을 검토  
- 리뷰 절차  
  - 리뷰 절차는 Github Pull Request 기능을 이용하여 진행함  
  - 리뷰어는 다음을 확인:  
    - 코드 품질 및 스타일 가이드 준수 여부.  
    - 변경 사항이 프로젝트의 요구사항을 만족하는지 확인.  
    - 잠재적인 오류나 개선점을 피드백

### 6.9. Coding Standard

**네이밍 컨벤션**   
Camel case, Java standard  
**백엔드**  
Google Java Style Guide ([https://google.github.io/styleguide/javaguide.html](https://google.github.io/styleguide/javaguide.html))  
**프론트엔드**  
Prettier default style ( [https://prettier.io/docs/en/options.html](https://prettier.io/docs/en/options.html))

### 6.10. Source Code
프로젝트 ‘졸업해줘’의 소스코드는 해당 [Github Repository](https://github.com/uos-se/uos-easy-life) 에서 관리한다. Github Repository는 다음 사항들을 포함한다.  
- ‘졸업해줘’의 기능을 구성하는 모든 코드 및 버전관리 정보  
- 팀원간의 공통 개발환경 설정 (Dev Container)  
- CI/CD 설정 (K3S) 및 자동화 스크립트  
- 설계, 요구사항, 기여방법 등 문서

Repository의 폴더구조는 다음과 같다.  
- `.devcontainer` - Dev container 설정  
- `artifacts` - 설계, 요구사항, 기여방법 등 문서  
- `backend` - 백엔드 모듈  
- `courseutil` - 서울시립대 수업 정보 스크래핑, 통합등 유틸리티  
- `frontend` - 프론트엔드 모듈  
- `kubernetes` - 쿠버네티스 설정 파일  
- `scripts` - CI/CD 자동화 스크립트

### 6.11. 테스트 및 결과

| Test Case ID | Test Case Scenario | Test Case | Preconditions | Test Steps | Input Data | Expected Results | Notes | Test Environment | Actual Results |
| ----- | ----- | ----- | ----- | ----- | ----- | ----- | ----- | ----- | ----- |
| **UC001-TC1** | 로그인 성공 테스트 | 유효한 ID와 PW로 로그인 | 유효한 서울시립대학교 포털 ID/PW를 소지해야 함 | 1. ID 입력 2. PW 입력 3. 로그인 버튼 클릭 | \<유효한 ID\>, \<유효한 PW\> | 로그인 성공 및 메인 페이지로 이동 | 유스케이스 \#UC001 | local | Expected와 일치 |
| **UC001-TC2** | 로그인 실패 테스트 | 유효한 ID와 유효하지 않은 PW로 로그인 | 유효한 서울시립대학교 포털 ID/PW를 소지해야 함 | 1. ID 입력 2. PW 입력 3. 로그인 버튼 클릭 | \<유효한 ID\>, \<유효하지 않은 PW\> | 오류 메시지 'ID 또는 PW가 올바르지 않습니다' 출력 | 유스케이스 \#UC001 | local | Expected와 일치 |
| **UC001-TC3** | 로그인 실패 테스트 | 유효하지 않은 ID로 로그인 | 유효한 서울시립대학교 포털 ID/PW를 소지해야 함 | 1. ID 입력 2. PW 입력 3. 로그인 버튼 클릭 | \<유효하지 않은 ID\>, \<PW\> | 오류 메시지 'ID 또는 PW가 올바르지 않습니다' 출력 | 유스케이스 \#UC001 | local | Expected와 일치 |
| **UC001-TC4** | 정보 조회 실패 테스트 | 로그인되지 않은 상태로 메인 화면에 접근 | 유효한 서울시립대학교 포털 ID/PW를 소지해야 함 | 로그인 없이 메인 화면 URL로 접근 |  | 로그인 화면을 redirection | 유스케이스 \#UC001 | local | Expected와 일치 |
| **UC002-TC1** | 이루넷 데이터 동기화 테스트 | 이루넷에서 데이터를 동기화 요청 | 이루넷 계정이 연결되어 있어야 함 | 1. 동기화 버튼 클릭 |  | 이루넷에서 데이터 가져와서 UI에 표시 \- 학생 정보 업데이트 확인 \- academic status 업데이트 확인 | 유스케이스 \#UC002 | local | Expected와 일치 |
| **UC002-TC2** | 세션이 없을 때 데이터 동기화를 요청 | 세션이 없을 때 데이터 동기화를 요청 | 유효하지 않은 세션을 가지고있어야 함 | 1. 동기화 버튼 클릭 |  | 세션을 다시 획득하여 이루넷에서 데이터 가져와서 UI에 표시 \- 학생 정보 업데이트 확인 \- academic status 업데이트 확인 | 유스케이스 \#UC002 | local | Expected와 일치 |
| **UC003-TC1** | 졸업 요구 학점 확인 테스트 | 학점 요구사항을 확인 | 유저 데이터가 정상적으로 입력되어 있어야 함 | 1. 학점 확인 메뉴 선택 2. 학점 요구사항 확인 |  | 졸업 요구 학점이 카테고리별로 정확히 표시 | 유스케이스 \#UC003 | local | Expected와 일치 |
| **UC003-TC2** | 졸업 요구 학점 차트 클릭 시 다음 학기에 들을 과목 리스트 변경 | 학점을 차트를 클릭하면 클릭한 차트의 기준에 따라서 리스트가 상태를 변경하여 유효한 과목 리스트를 받을 수 있는 지 검증 | 유저 데이터가 정상적으로 입력되어 있어야 함 | 1. 메인 페이지에 접속 2. 바차트의 각 지점을 선택함 |  | 다음 학기에 수강 가능한 과목들이 변경됨 | 유스케이스 \#UC003 | local | Expected와 일치 |
| **UC003-TC3** | 데이터 정합성 확인 | 이루넷에서 가져온 데이터와 UI에 표시되는 데이터가 정합성이 맞는 지 확인 | 유저 데이터가 정상적으로 입력되어 있어야 함 | 1. 메인 페이지에 접속 2. 모든 데이터(전체, 전공, 전공 필수, 교양, 교양 필수, 공학 소양, 일반)를 UI와 이루넷의 데이터와 비교함 |  | 졸업 요구 학점의 각각의 값이 이루넷과 다르지 않음을 확인 | 유스케이스 \#UC003 | local | Expected와 일치 |
| **UC004-TC1** | 다음 학기 수강 과목 리스트 확인 테스트 | 다음 학기 수강 가능 과목 리스트 확인 | 현재 학기 성적과 선후수 과목 데이터가 정상적으로 입력되어 있어야 함 | 1. 다음 학기 수강 과목 리스트 확인 메뉴 선택 |  | 다음 학기에 수강 가능한 과목들이 정확히 표시 | 유스케이스 \#UC004 | local | Expected와 일치 |
| **UC005-TC1** | 수강 계획 시간표 작성 테스트 | 수강 계획 시간표 작성 및 제약사항 검증 | 모든 수강 가능 과목 데이터, 수강한 과목 데이터가 준비되어 있어야 함 | 1. 수강 계획 시간표 작성 메뉴 선택 2. 수강 계획 작성 |  | 올바르지 않은 시간표 구조에 대해 경고 메시지 출력, 각 학기마다 카테고리별로 채워지는지 표시 | 유스케이스 \#UC005 | local | 일부 상황에 예외 발생(E.g. 엇학기 복학) |
| **UC005-TC2** | 수강 계획 시간표 저장 테스트 | 수강 계획 시간표 저장 및 제약사항 검증 | 모든 수강 가능 과목 데이터, 수강한 과목 데이터가 준비되어 있어야 함 | 1. 수강 계획 시간표 저장 버튼 클릭 |  | 올바르지 않은 시간표 구조에 대해 경고 메시지 출력 | 유스케이스 \#UC005 | local | 일부 상황에 예외 발생(E.g. 엇학기 복학) |

## 7. Project Constraints

| 구분 | 제약사항 |
| :---- | :---- |
| 기술적 제약 사항 | 소프트웨어는 Windows, Linux, Mac 운영체제에서 모두 실행 가능해야 한다. 개발 환경은 VSCode DevContainer로 통일하여 모두 같은 환경에서 작업 가능해야 한다. 프론트엔드 개발은 React.js에 Typescript를 사용한다. 백엔드 개발은 Spring프레임워크를 사용한다.  |
| 환경적 제약 사항 | 개발된 소프트웨어는 Kubernetes와  OnPromise Server에서 배포된다. 코드의 버전 관리는 Git과 연동된다.  |
| 정책적 제약사항 | 이루넷 시스템을 이용하기 때문에 유저가 사용하고 있는 도중에는 데이터를 가지고 올 수 없다. |
| 신뢰성 요구사항 | 유저 이루넷 ID/PW 정보는 Database에 저장되지 않아야 한다. 유저의 승인 없이 이루넷의 데이터에 접근하지 않아야 한다. 모든 데이터 접근과 변경 작업은 기록돼야 한다.  |
| 비용적 제약사항 | OnPromise Server는 비용적 한계로 인해 기존에 보유한 리소스를 최대한 활용한다. 신규 하드웨어 구매 없이 현재 사용 중인 서버 인프라를 활용한다.  |
| 시간 제약사항 | 프로젝트는 2024년 2학기 이내에 완료되어야 한다. |

## 8. Project Team Members

- 권준호 (2019920003) \- 팀장, 백엔드, DevOps 및 시스템 배포  
- 김원빈 (2019920016) \- 백엔드 개발  
- 정민혁 (2019920048) \- UI/UX 디자인 및 프론트엔드 개발  
- 채민관 (2019920055) \- 프론트엔드 개발  
- 박정익 (2020920025) \- 백엔드 및 API 통합  
- 이현제 (2020920051) \- 백엔드 및 API 통합