![header](https://capsule-render.vercel.app/api?type=egg&color=FF834E)

<p align="center">
  <h1>Burger Drop</h1>
</p>
-------------------------------------

<h2>서비스 소개🍔</h2>
    - 집에서도 햄버거의 맛을 놓치지 마세요! 햄버거 포장&메뉴 웹사이트로 원하는 햄버거를 주문하고, 배달 받고, 즐기세요! 
    **BD**는 당신의 입맛에 맞는 햄버거를 쉽고 빠르게 제공합니다. 
    다양한 종류의 햄버거, 사이드 메뉴, 음료수 등을 원하는 조합으로 선택하세요. 
    햄버거 포장&메뉴 웹사이트와 함께 라면 언제 어디 서나 맛있는 햄버거를 즐길 수 있습니다.


## ❓기획 의도
------------------------------
    **버거킹을 모토로 한 인터넷 주문 배달 사이트**


## 개발 환경
    - Spring Version : 3.1.2
    - JDK Version : 17


## 🔧 사용한 Tool
------------------------
<div style="display: flex; justify-content: center;">
  <img src="https://img.shields.io/badge/Java-007396?&style=flat&logo=Java&logoColor=white" style="margin-right: 10px;">
  <img src="https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=HTML5&logoColor=white" style="margin-right: 10px;"/>
	<img src="https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=CSS3&logoColor=white" style="margin-right: 10px;" />
  <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=javascript&logoColor=white" />
</div>

<div style="display: flex; justify-content: center;">
  <img src="https://img.shields.io/badge/Spring-6DB33F?&style=flat&logo=spring&logoColor=white" style="margin-right: 10px;">
  <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat&logo=Spring Boot&logoColor=white" style="margin-right: 10px;">
  <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat&logo=Spring Security&logoColor=white" style="margin-right: 10px;">
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white" style="margin-right: 10px;"/>
</div>

<div style="display: flex; justify-content: center;">
  <img src="https://img.shields.io/badge/Git-F05032?style=flat&logo=git&logoColor=white" style="margin-right: 10px;">
  <img src="https://img.shields.io/badge/Github-181717?style=flat&logo=github&logoColor=white" style="margin-right: 10px;">
  <img src="https://img.shields.io/badge/Intellijidea-000000?style=flat&logo=intellijidea&logoColor=white" style="margin-right: 10px;">
  <img src="https://img.shields.io/badge/Postman-FF6C37?style=flat&logo=postman&logoColor=white" style="margin-right: 10px;">
</div>

<div style="display: flex; justify-content: center;">

  <img src="https://img.shields.io/badge/Redis-DC382D?style=flat&logo=Redis&logoColor=white" style="margin-right: 10px;">
  <img src="https://img.shields.io/badge/Bootstrap-7952B3?style=flat&logo=Bootstrap&logoColor=white" style="margin-right: 10px;">
  <img src="https://img.shields.io/badge/Slack-4A154B?style=flat&logo=Slack&logoColor=white" style="margin-right: 10px;">
  <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=flat&logo=Thymeleaf&logoColor=white" style="margin-right: 10px;">
</div>

<div style="display: flex; justify-content: center;">
  <img src="https://img.shields.io/badge/WebSocket-F56640?style=flat&logo=WebSocket&logoColor=white" style="margin-right: 10px;">
  <img src="https://img.shields.io/badge/Amazon S3-569A31?style=flat&logo=Amazon S3&logoColor=white" style="margin-right: 10px;">
  <img src="https://img.shields.io/badge/ApachetTomcat-F8DC75?style=flat&logo=apachetomcat&logoColor=white"/>
</div>

<br>
<h2>⚙️서비스 아키텍처</h2>
<p align="center">
  <img src="https://github.com/burger-drop/burger-drop-repo/assets/94231335/6d9e5d0d-3196-4ba6-8464-83a8caf078fa" style="margin-right: 10px;">
</p>


# ✨ 핵심기능
-----------------
`회원 가입 / 로그인`

- 유저는 회원가입 시 이메일 인증을 해야한다.
- 소셜 로그인 가능 (구글, 카카오, 네이버)
    - 소셜 로그인 버튼 클릭 → 기존 회원이면 로그인 → 기존 회원 아니면 회원가입

`유저`

- 유저 페이지에서 정보 조회, 수정, 탈퇴, 로그아웃 가능하다.
- 주문내역과 리뷰 작성 내역을 볼 수 있다.

`상품`

- 회원 가입이나 로그인 하지 않아도 전체 상품 조회가 가능하다.
- 회원 가입이나 로그인 하지 않아도 상품 키워드 검색 기능을 사용할 수 있다.
- 관리자는 상품 생성, 조회, 수정, 삭제가 가능하다.
- 관리자는 옵션 생성, 조회, 수정, 삭제가 가능하다.
- 카테고리 별로 상품을 확인할 수 있다. [햄버거, 음료, 사이드]

`리뷰`

- 유저는 리뷰 생성, 조회, 수정, 삭제가 가능하다
- 리뷰를 쓸 때 별점을 부여할 수 있다.
- 음식 이미지를 첨부할 수 있다.

`장바구니`

- 유저는 장바구니에 상품을 추가, 조회 및 삭제가 가능하다.
- 장바구니에 상품을 담을 때, 해당 상품의 카테고리 별로 정해진 옵션을 추가할 수 있다.
- 옵션을 추가할 경우 옵션 별로 추가 가격이 청구된다.
- 장바구니에 담긴 상품을 주문할 수 있다.
- 주문할 때 포장 or 배달을 선택할 수 있다.

`배달 / 포장`

- 포장을 선택한다면 조리 시간을 더한 시간이 포장 시간으로 표시된다.
- 배달을 선택한다면 원래 금액에서 2000원이 추가된다.
- 주문 완료 시간에 배달 시간을 추가하여 총 시간이 표시가 되는데 여기서 배달 시간이란 매장과 사용자 간의 거리를 위도와 경도를 통해서 1km 당 1분 씩 추가한 시간을 말한다.
- 사용자는 자신의 모든 주문 목록 중 최신의 10개까지 조회할 수 있다.
- 사용자는 자신의 준비 중인 주문을 취소할 수 있다.
- 관리자는 최대 100개의 최신의 완료된 주문 목록과 모든 준비 중인 주문 목록을 조회할 수 있다.
- 5분마다 전체 준비 중인 주문 목록을 조회하여 완료 시간을 넘은 주문을 완료 상태로 바꾼다.

`채팅`

- 유저는 관리자에게 문의를 할 수 있다.

`알림`

- 사이트를 이용 중인 유저에게 SSE(Server Sent Event)를 이용하여 실시간 알람을 기능 제공하고 있다.
- 회원 수정, 주문 완료시 알림이 표시된다.

`백오피스`

- Interceptor를 통해 홈페이지 방문자들의 ip주소, 방문한 경로, 방문 시각을 저장한다.
- 관리자는 방문자 목록 조회, ip주소로 방문자 검색, 총 방문자 수 확인을 할 수 있다.
- 관리자는 총 주문 수를 확인할 수 있다.
- 관리자는 전체 회원가입한 유저 목록을 조회할 수 있고, 일반 유저의 정보를 수정하거나, 탈퇴시킬 수 있다.
- 관리자는 username과 nickname을 키워드로 검색할 수 있다.


# 🎯기술적 의사결정

<details>
<summary>검색 기능에 QueryDsl 적용 여부</summary>
<div markdown="1">
<br>
- 검색할 때 사용자가 바라는 특징에 부합하는 상품을 검색 결과로 보여주어 더 나은 서비스를 제공하기 위해서이다.<br><br>
- JPQL과 비교하였을 때 가독성이 높고 확장 가능한 동적 쿼리를 작성할 수 있어 Querydsl을 사용했다.
</div>
</details>
<br>

<details>
<summary>채팅 저장소 Redis , MySQL</summary>
<div markdown="1">
<br>
- 문의하기 특성상 대부분 채팅은 오래 이어가지 않기 때문에 데이터를 장기적으로 저장하지 않고, 읽기 및 쓰기를 처리할 수 있는 속도가 빠른 Redis가 실시간 채팅 기능에 적합하고 판단
</div>
</details>
<br>

<details>
<summary>Swagger 연동</summary>
<div markdown="1">
<br>
- API 문서를 생성할 때, 따로 개발자가 문서를 작성하지 않아도 되므로 개발 시간을 단축할 수 있고, 코드에서 수정한 부분이 즉시 자동으로 Swagger UI에도 적용되기 때문이다.
</div>
</details>
<br>


<details>
<summary>Redis와 Interceptor를 사용하여 방문자 정보 저장</summary>
<div markdown="1">
<br>
- Interceptor를 사용하여 홈페이지에 방문한 방문자의 ip주소, 입장 경로, 방문 시각을 가져와 Redis에 저장한다. 1시간마다 Redis에 저장된 방문자들의 정보를 한꺼번에 DB에 옮겨 저장하고 Redis에 있는 정보들은 삭제하고 다시 방문자 정보를 수집한다. <br><br>
	Redis에 저장했다가 한 번에 옮기는 이유는 방문자가 들어올 때 마다 DB에 연결하여 저장하는 것은 DB I/O가 너무 많아 DB에 대한 부담이 크게 증가한다고 생각했기 때문이다.
</div>
</details>
<br>

<details>
<summary>위치기반 API를 사용한 거리계산 및 배달소요시간 계산</summary>
<div markdown="1">
<br>
- 카카오는 예전에 다음(한메일)을 인수하며 DAUM 지도 시스템을 함께 가져왔고, 현재 카카오 택시나 카카오 지도와 같은 서비스를 실제로 운영하고 있다. 또한 개발자를 위한 가이드라인도 상세하게 잘 작성되어 있어서 처음 위치기반 API를 사용하고 학습함에 있어 가장 진입장벽이 낮을 것이라고 생각했다.<br><br>
- 찾아보니 구글맵, 네이버지도, 카카오맵을 비교, 정리한 자료가 있었다. 버거드롭 프로젝트는 국내 고객에게 서비스를 제공하는 것을 상정하고 있기에 제공하는 정보량보다는 속도에 중점을 두어 선택하였다.<br><br>
- 속도 테스트에서 첫 로딩은 구글맵이 가장 빠르고 네이버맵 이 가장 느렸지만, 검색시간은 구글맵이 압도적으로 느리고 카카오맵이 가장 빨랐다. 그렇기에 속도와 사용성 측면에서 가장 이점이 있다고 생각되는 카카오맵의 위치기반 API를 사용하기로 결정하였다.
<p align="center">
  	<img src="https://github.com/burger-drop/burger-drop-repo/assets/94231335/30622e04-7f55-4ac2-ace4-47cf32009f38" width="1200px" height="750px"style="margin-right: 10px;">
	<img src="https://github.com/burger-drop/burger-drop-repo/assets/94231335/dc54364b-494d-494d-bdab-33ceb9f73b5a" width="1200px" height="750px"style="margin-right: 10px;">
</p>
</div>
</details>
<br>






# 🚀트러블 슈팅

<details>
<summary>JWT의 보안적 취약성</summary>
<div markdown="1">
<br>
- 만료 기한이 짧은 엑세스 토큰과 만료 기한이 긴 리프레시 토큰을 사용<br><br>
- 리프레시 토큰은 영구적으로 보관할 필요가 없으니 속도가 빠른 레디스를 저장소로 사용<br><br>
- 리프레시 토큰까지 탈취 되었을 때를 대비하여 강제종료 API 마련
</div>
</details>
<br>

<details>
<summary>Swagger 인증버튼 문제</summary>
<div markdown="1">
<br>
- 현재 진행 중인 프로젝트의 인증, 인가 방식은 토큰값을 헤더가 아닌 쿠키에 저장하는 방식을 가지고 있다. 그래서 기본적으로 헤더에서 토큰 값을 가져오는 Swagger의 인증버튼이 제대로 동작하지 않는 문제가 있었다.<br>
<p align="center">
  	<img src="https://github.com/burger-drop/burger-drop-repo/assets/94231335/ff6eb650-8d94-4d58-969c-65cdcf9ab5be" width="1000px" height="550px"style="margin-right: 10px;">
</p>
- Swagger 공식 문서에 Cookie Authentication이라는 기능도 있었지만 이 기능을 사용할 경우 CSRF 공격에 취약해 지므로 CSRF 토큰까지 사용할 필요가 있기에, 이 기능을 사용하기보다는 프로젝트 인증 방식에서 헤더에도 토큰값을 추가해주는 걸로 문제를 해결하였다.
</div>
</details>
<br>

<details>
<summary>Redis 키 조회 시 병목현상 발생</summary>
<div markdown="1">
<br>
- Redis는 단일 쓰레드 아키텍쳐이기에 처리가 오래 걸리는 명령을 요청할 경우 그 동작이 마무리 될 때가지 다른 요청을 멈춰두게 되어 병목현상이 발생하게 된다. 특히나 keys, flushall 등의 명령어는 테스트나 소량의 데이터 환경에서는 괜찮지만 점차 데이터를 쌓아가는 환경에서는 운영에 차질을 빚을 정도로 속도가 느려지는 문제가 있다. <br><br>
- keys 명령어를 scan으로 대체하여 병목 현상을 최소화.
</div>
</details>
<br>
<details>
<summary>Redis 캐시를 적용하여 성능 향상</summary>
<div markdown="1">
<br>
- 버거드롭 주문 사이트에서 가장 중요도가 높으면서 조회 빈도도 높은 메인 페이지 음식 목록 조회에 Redis Cache를 적용하여 성능을 향상시켰다. <br><br>처음 조회할 때는 650ms 이상이 소요되었지만, 캐시를 적용하고 나서는 평균적으로 60ms 대를 기록하여 약 10배에 가까운 성능 향상이 있었다.<br><br>
<p align="center">
  	<img src="https://github.com/burger-drop/burger-drop-repo/assets/94231335/b19e2c80-d7da-4eb7-85eb-253a6fa84934" width="1000px" height="550px"style="margin-right: 10px;">
  	<img src="https://github.com/burger-drop/burger-drop-repo/assets/94231335/a44fb656-83e8-4a39-b7e7-756a235fe6c0" width="1000px" height="550px"style="margin-right: 10px;">
</p>
- 상품 단건 조회에서도 처음 조회할 때는 150ms 걸렸지만, 캐시를 적용하고 나서는 50ms 를 기록하여 약 3배의 성능 향상이 있었다.
</div>
</details>
<br>

<details>
<summary>Interceptor를 사용하여 방문자 정보 저장 시 DB 부담 증가</summary>
<div markdown="1">
<br>
- 방문자가 들어올 때마다 해당 정보를 DB에 입력하는 것은 DB와 서버 간의 I/O 빈도를 너무 높여 부담을 높이는 문제가 있다.<br><br>
- Redis에 방문자 정보들을 모아두었다가 일정 시간마다 벌크 데이터를 한 번에 입력하여 DB에 저장하고 Redis의 정보는 삭제한다. 이러한 방식으로 쓰기 작업의 빈도를 줄여 DB와 서버의 부담을 줄였다.<br>
<p align="center">
  	<img src="https://github.com/burger-drop/burger-drop-repo/assets/94231335/26bc65d7-243f-4bf8-be55-b38be546943f" width="1000px" height="550px"style="margin-right: 10px;">
</p>
</div>
</details>
<br>

<details>
<summary>Docker를 사용하던 도중 해킹 발생</summary>
<div markdown="1">
<br>
- Docker로 MySQL, Spring, Redis 이렇게 3개의 컨테이너를 돌리는데 Redis에 비밀번호 설정을 안 했더니 바로 해킹을 당하였다. 알아본 바에 의하면 해커들이 뿌려놓은 크롤러 봇에 의해 Redis의 일부가 코인 채굴 사이트에 할당되도록 수정되어 있었다.<br><br>
- docker-compose의 Redis와 MySQL에 비밀번호를 설정하여 문제를 해결하였다.
</div>
</details>
<br>

<details>
<summary>ubuntu환경에 Redis 설정하는 도중 오류 발생</summary>
<div markdown="1">
<br>
- ubuntu 원격으로 돌리면서 Redis를 redis-erver 명령어로 실행하고 git bash를 종료하면 Redis 서버가 실행되는 상태이지만 기능을 사용할 수 없는 현상이 발생했다.<br><br>

      먼저 ubuntu@ubuntu:~$ sudo passwd root 명령어로 아래와 같이 비밀번호를 설정하고<br><br>

       [sudo] password for ubuntu: 현재 접속중인 계정의 비밀번호 입력<br><br>

       Enter new UNIX password: 설정할 root 계정의 비밀번호 입력<br><br>

       Retype new UNIX password: 한번 더 재입력 <br><br>

       password updated successfully <br><br>

       su - 명령어로 설정한 비밀번호로 root 권한으로 접속한 다음 <br><br>

 service redis stop 명령어로실행중이던 Redis 서버를 중지 시킨다.<br><br>

- ubuntu를 원격으로 돌리면서 동시에 Redis도 원격으로 실행하기 위해서 윈도우 기준 git bash에서 sudo vim /etc/redis/redis.conf 명령어로 vim 편집기에서 bind 0.0.0.0, port 6379, daemonize yes, maxmemory 1g(Redis 최대 메모리 1G로 설정), maxmemory-policy allkeys-lru(최대 사용 메모리를 초과할 시 가장 오래된 데이터를 지워서 메모리를 확보하며 가장 최근에 저장된 데이터를 사용하는 것으로 설정) 설정을 하고 저장 후 종료를 하였다.<br><br>
- 종료 후, redis-server --daemonize yes를 하면 Redis가 원격으로 실행되어 문제를 해결하였다.<br>
</div>
</details>
<br>


![footer](https://capsule-render.vercel.app/api?section=footer&type=egg&color=FF834E)
