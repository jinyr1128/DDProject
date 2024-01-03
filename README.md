# 📻 DDProject
![header](https://capsule-render.vercel.app/api?type=waving&color=timeGradient&text=DDProject!!&animation=twinkling&fontSize=45&fontAlignY=40&fontAlign=50&height=250)
---

## 📇개요
DDProject 는 'Development Diary'의 약자로, 개발자들이진행하면서 필요한 협업 도구를 제공하는 것이 주 목적입니다.<br> 이 프로젝트는 개발 프로세스의 투명성을 높이고, 팀원 간의 소통을 촉진하며, 업무 흐름을 체계화하는 데 중점을 두고 있습니다.<br>

특히, 이 프로젝트에서는 칸반보드를 활용하여 웹 페이지를 구현하였습니다.<br> 칸반보드는 일의 진행 상황을 한눈에 파악할 수 있게 해주며, 각 팀원의 업무 분배와 진행 상황, 일정 관리 등을 효율적으로 할 수 있게 돕습니다.<br>

이 웹 페이지의 구현에는 Spring과 Java 프로그래밍 언어를 사용하였습니다.<br>

DDProject는 개발자들이 프로젝트를 더 효율적으로 진행하고, 협업을 강화하며, 프로젝트의 성공 가능성을 높이는 데 크게 기여하게 되길 바라며 만들었습니다!

## 📔기술스택
- ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) 17
- ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
- ![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
- ![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
- ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white) 8.5
- Spring Boot 3.2.1
- JPA/Hibernate
- Spring Security

## ⚙구현기능
- **사용자 관리 기능**
    - [ ]  로그인 / 회원가입 기능
    - [ ]  사용자 정보 수정 및 삭제 기능
- **알람기능**
    - [ ]  내 카드에 달린 코멘트 알람
    - [ ]  초대 알람
    - [ ]  멤버 추가 알람
- **보드 관리 기능**
    - [ ]  보드 생성
    - [ ]  보드 수정
        - 보드 이름
        - 배경 색상
        - 설명
    - [ ]  보드 삭제
        - 생성한 사용자만 삭제를 할 수 있습니다.
    - [ ]  보드 초대
        - 특정 사용자들을 해당 보드에 초대시켜 협업을 할 수 있어야 합니다.
- **컬럼 관리 기능**
    - [ ]  컬럼 생성
        - 보드 내부에 컬럼을 생성할 수 있어야 합니다.
        - 컬럼이란 위 사진에서 Backlog, In Progress와 같은 것을 의미해요.
    - [ ]  컬럼 이름 수정
    - [ ]  컬럼 삭제
    - [ ]  컬럼 순서 이동
        - 컬럼 순서는 자유롭게 변경될 수 있어야 합니다.
            - e.g. Backlog, In Progress, Done → Backlog, Done, In Progress
- **카드 관리 기능**
    - [ ]  카드 생성
        - 컬럼 내부에 카드를 생성할 수 있어야 합니다.
    - [ ]  카드 수정
        - 카드 이름
        - 카드 설명
        - 카드 색상
        - 작업자 할당
        - 작업자 변경
    - [ ]  카드 삭제
    - [ ]  카드 이동
        - 같은 컬럼 내에서 카드의 위치를 변경할 수 있어야 합니다.
        - 카드를 다른 컬럼으로 이동할 수 있어야 합니다.
- **카드 상세 기능**
    - [ ]  댓글 달기
        - 협업하는 사람들끼리 카드에 대한 토론이 이루어질 수 있어야 합니다.
    - [ ]  날짜 지정
        - 카드에 마감일을 설정하고 관리할 수 있어야 합니다.
- **클라우드에 배포하기!**
    - AWS, Azure와 같은 클라우드 서비스를 선택하여 여러분들의 웹 서비스 배포를 진행합니다!
    - 여러분들의 서비스를 세상 밖으로 소개해봐요!
- **프론트엔드 개발도 해보기!**

## 팀원별 역할

- 진유록 : Column, Card, Comment
- 권준혁 : User, Alarm, Security
- 최혁 : Board, Member
- 송지헌 : Comment

## 💾디렉토리 구조 
```agsl
├── DdProjectApplication.java
 ├── alarm
 ├── board
 ├── card
 ├── column
 ├── comment
 ├── common
 │   ├── security
 ├── global
 ├── invite
 ├── member
 └── user
 └── resources
 ├── static
 │   ├── css
 │   ├── images
 │   ├── js
 └── templates
```

## 📊Swagger-Ui
![스크린샷 2024-01-03 오후 7.49.02.png](img%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202024-01-03%20%EC%98%A4%ED%9B%84%207.49.02.png)
![스크린샷 2024-01-03 오후 7.49.12.png](img%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202024-01-03%20%EC%98%A4%ED%9B%84%207.49.12.png)
![스크린샷 2024-01-03 오후 7.49.21.png](img%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202024-01-03%20%EC%98%A4%ED%9B%84%207.49.21.png)
![스크린샷 2024-01-03 오후 7.49.31.png](img%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202024-01-03%20%EC%98%A4%ED%9B%84%207.49.31.png)
![스크린샷 2024-01-03 오후 7.49.43.png](img%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202024-01-03%20%EC%98%A4%ED%9B%84%207.49.43.png)
![스크린샷 2024-01-03 오후 7.49.53.png](img%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202024-01-03%20%EC%98%A4%ED%9B%84%207.49.53.png)
![스크린샷 2024-01-03 오후 7.50.03.png](img%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202024-01-03%20%EC%98%A4%ED%9B%84%207.50.03.png)
## 📔Documents
![스크린샷 2023-12-26 오후 3.11.18.png](img%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-12-26%20%EC%98%A4%ED%9B%84%203.11.18.png)
## 📸Screenshot
![스크린샷 2024-01-03 오전 10.21.59.png](img%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202024-01-03%20%EC%98%A4%EC%A0%84%2010.21.59.png)
## :octocat: 협업전략

![스크린샷 2024-01-01 오후 9.14.10.png](img%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202024-01-01%20%EC%98%A4%ED%9B%84%209.14.10.png)

| Tag Name | Description |
| --- | --- |
| feat | 새로운 기능을 추가 |
| fix | 버그 수정 |
| design | CSS 등 사용자 UI 디자인 변경 |
| !BREAKING CHANGE | 커다란 API 변경의 경우 |
| !HOTFIX | 급하게 치명적인 버그를 고쳐야하는 경우 |
| style | 코드 포맷 변경, 세미 콜론 누락, 코드 수정이 없는 경우 |
| refactor | 프로덕션 코드 리팩토링 |
| comment | 필요한 주석 추가 및 변경 |
| docs | 문서 수정 |
| test | 테스트 코드, 리펙토링 테스트 코드 추가, Production Code(실제로 사용하는 코드) 변경 없음 |
| chore | 빌드 업무 수정, 패키지 매니저 수정, 패키지 관리자 구성 등 업데이트, Production Code 변경 없음 |
| rename | 파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우 |
| remove | 파일을 삭제하는 작업만 수행한 경우 |
| feat(Domain): | description (#issue) |
| ex) feat(User): | user api 생성 (#2) |

