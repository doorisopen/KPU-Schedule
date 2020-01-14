## 한국산업기술대학교 시간표 만들기 웹 사이트
한국산업기술대학교 시간표 만들기 웹 사이트를 RESTful 하게 만들어보자

2019-11-30 ~ ing...


</br>

## Site Preview
<a href="http://13.125.253.127:8080/kpu-schedule/home"> Preview : Home Go - Click Me </a>|
<a href="http://13.125.253.127:8080/kpu-schedule/lectureLoading/A"> A: 대학 강의 리스트 </a>|
<a href="http://13.125.253.127:8080/kpu-schedule/lectureLoading/G"> G: 대학원 강의 리스트 </a>

## Dev Env
* Spring4
* jackson

## Dev Stage
* [x] Lecture Data Scrapy
* [ ] Anonymous Board


## Dev Issue
* No ‘Access-Control-Allow-Origin’ header is present on the requested resource ( 크로스 도메인 문제 발생 )

```
@CrossOrigin(origins = "*") // <--- 추가 
```

* https -> http 호출x AWS SSL 등록 방법