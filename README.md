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
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white" style="margin-right: 10px;"/>
  <img src="https://img.shields.io/badge/ApachetTomcat-F8DC75?style=flat&logo=apachetomcat&logoColor=white"/>
</div>

<div style="display: flex; justify-content: center;">
  <img src="https://img.shields.io/badge/Git-F05032?style=flat&logo=git&logoColor=white" style="margin-right: 10px;">
  <img src="https://img.shields.io/badge/Github-181717?style=flat&logo=github&logoColor=white" style="margin-right: 10px;">
  <img src="https://img.shields.io/badge/Intellijidea-000000?style=flat&logo=intellijidea&logoColor=white" style="margin-right: 10px;">
  <img src="https://img.shields.io/badge/Postman-FF6C37?style=flat&logo=postman&logoColor=white" style="margin-right: 10px;">
</div>

<div style="display: flex; justify-content: center;">
  <img src="https://github.com/burger-drop/burger-drop-repo/assets/133616029/e031df72-a0ec-415d-af54-fe0e0d377e6b" style="margin-right: 10px; width: 110px; height: 25px;">
  <img src="https://github.com/burger-drop/burger-drop-repo/assets/133616029/9d67190f-119e-496c-9f5b-8407c1f617e7" style="margin-right: 10px; width: 170px; height: 25px;">
</div>

<h2>서비스 아키텍처</h2>
![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/d00db562-2625-4cce-9cbc-a0f0008aa8e5/bf82cfac-f728-4807-9207-b8f2631fb509/Untitled.png)

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

### 알림

- 사이트를 이용 중인 유저에게 SSE(Server Sent Event)를 이용하여 실시간 알람을 기능 제공하고 있다.
- 회원 수정, 주문 완료시 알림이 표시된다.

### 백오피스

- Interceptor를 통해 홈페이지 방문자들의 ip주소, 방문한 경로, 방문 시각을 저장한다.
- 관리자는 방문자 목록 조회, ip주소로 방문자 검색, 총 방문자 수 확인을 할 수 있다.
- 관리자는 총 주문 수를 확인할 수 있다.
- 관리자는 전체 회원가입한 유저 목록을 조회할 수 있고, 일반 유저의 정보를 수정하거나, 탈퇴시킬 수 있다.
- 관리자는 username과 nickname을 키워드로 검색할 수 있다.

![footer](https://capsule-render.vercel.app/api?section=footer&type=egg&color=FF834E)
