## 한국산업기술대학교 시간표 API 관리 어플리케이션
한국산업기술대학교 강의 API 관리 어플리케이션 만들기

[old project go](https://github.com/doorisopen/kpu-schedule/tree/64eab9c9a2c1f03a27407228bf0ed4361a17d07f)

* start date: 2019.11.30 
* renew date: 2020.04.16 ~

## 프로젝트 환경
* Spring Boot 2.1.9
* Gradle
* JPA
* H2
* lombok
* thymeleaf

## TODO
* [x] 강의 데이터 웹 스크래핑
* [x] 프로젝트 환경설정
  + [x] View 환경
  + [x] H2 데이터베이스
  + [x] JPA, DB 설정, 동작확인
* [x] 도메인 분석 설계
  + [x] 도메인 모델과 테이블 설계
  + [x] 엔티티 클래스 개발
* [x] 애플리케이션 구현
  + [x] 교수 기능
    - [x] 교수 등록
    - [x] 교수 조회
    - [x] 기능 테스트 코드
  + [x] 강의 기능 
    - [x] 강의 등록 
    - [x] 강의 수정
    - [x] 강의 조회
    - [x] 기능 테스트 코드
* [x] 웹 계층 개발
  + [x] 교수 기능
  + [x] 강의 기능
  + [x] 전공 기능
* [ ] API 개발
  + [ ] 강의 조회 API


## Info
애플리케이션 아키텍처

패키지 구조