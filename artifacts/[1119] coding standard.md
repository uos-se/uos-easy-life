# Coding Standard
__Project information__  
- Team name: Graduos
- Project name: 졸업해줘
- Date of doc: 2024/11/19
- Version: Final
- Team Members
    - 권준호 (2019920003)
    - 김원빈 (2019920016)
    - 정민혁 (2019920048)
    - 채민관 (2019920055)
    - 박정익 (2020920025) 
    - 이현제 (2020920051)  
  
__Table of Contents:__  
1. Branch Strategy
2. PR Rule
3. Coding Style

## 1. Branch Strategy
Git은 master 에 tag로 staging / production, feat/fix/refactor/docs 등으로 변경사항 관리
- feat: 새로운 기능 개발 작업.
- fix: 버그 수정 작업.
- refactor: 코드 구조 개선 작업.
- docs: 문서 작성 및 업데이트 작업.
  
배포 버전은 Kubernetes manifest를 git으로 track하고 commit에 tagging하여 관리  

## 2. PR Rule
__리뷰어 지정__  
모든 Pull Request에는 최소 1명의 리뷰어를 지정하여 코드 품질을 검토  
  
__리뷰 절차__  
리뷰 절차는 Github Pull Request 기능을 이용하여 진행함  
리뷰어는 다음을 확인:  
- 코드 품질 및 스타일 가이드 준수 여부.
- 변경 사항이 프로젝트의 요구사항을 만족하는지 확인.
- 잠재적인 오류나 개선점을 피드백
  
## 3. Coding Style
__네이밍 컨벤션__   
Camel case, Java standard  
__백엔드__  
Google Java Style Guide (https://google.github.io/styleguide/javaguide.html)  
__프론트엔드__  
Prettier default style ( https://prettier.io/docs/en/options.html)
