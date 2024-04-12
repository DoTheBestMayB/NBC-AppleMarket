# 기능 소개

### 메인 페이지 - 알림


|알림 권한 허용|알림 권한 거부|알림 세부 채널 거부|
|:-----:|:-----:|:-----:|
|<img width="200" src="https://github.com/DoTheBestMayB/NBC-AppleMarket/assets/48354989/e03a948a-aead-4d8a-8783-7ca93e1d951d">|<img width="200" src="https://github.com/DoTheBestMayB/NBC-AppleMarket/assets/48354989/f5bc7728-118e-4aca-bb98-f5b2bd68b6db">|<img width="200" src="https://github.com/DoTheBestMayB/NBC-AppleMarket/assets/48354989/71bf47bb-0a35-4d6a-b6be-0c984d31d150">|

### 메인 페이지 - 그 외

|동네 설정|상품 삭제|플로팅 버튼|
|:-----:|:-----:|:-----:|
|<img width="200" src="https://github.com/DoTheBestMayB/NBC-AppleMarket/assets/48354989/80604908-d0aa-49e5-9cc4-24b787ea745f">|<img width="200" src="https://github.com/DoTheBestMayB/NBC-AppleMarket/assets/48354989/1abf5d81-3de0-42f5-965e-848b200e206b">|<img width="200" src="https://github.com/DoTheBestMayB/NBC-AppleMarket/assets/48354989/9a38da7f-efe1-4687-a39e-636e98532056">|

### 상품 상세 페이지

|상품 좋아요|매너 온도 설명 창|채팅하기 미지원 알림|
|:-----:|:-----:|:-----:|
|<img width="200" src="https://github.com/DoTheBestMayB/NBC-AppleMarket/assets/48354989/3d966b02-0d18-4079-bcff-b04aa0fdf106">|<img width="200" src="https://github.com/DoTheBestMayB/NBC-AppleMarket/assets/48354989/ec3aa00f-288b-4201-8e1e-c242e5aa22cd"> |<img width="200" src="https://github.com/DoTheBestMayB/NBC-AppleMarket/assets/48354989/94fc52d2-3187-4420-b1c9-9a95c4c05b41">|

# 요구사항 분석

## 전체적인 부분

- [x] RecyclerView, Fragment, Dialog, 알림 활용
- [x] 상단 툴바, 하단바 제거하고 풀스크린 화면으로 설정
- [x] 당근마켓 디자인 및 화면 구성과 최대한 동일하게 구성(당근 컬러 값 적용)

## 메인 페이지 - MainPageFragment

- [x] 상품 데이터는 dummy data 사용
- [x] RecyclerView를 이용해서 제품을 랜덤한 순서로 표시
- [x] 상품 이미지는 모서리를 라운드로 처리
- [x] 상품 이름은 최대 두 줄이고, 초과하면 ellipsize 적용
- [x] 뒤로가기 버튼 누르면 앱 종료 여부를 물어보는 다이얼로그 띄우기
- [x] 상단 종모양 아이콘을 누르면 상세 페이지 게시물로 이동할 수 있는 랜덤한 Notification을 생성하기
- [x] 상품 가격은 1000단위로 콤마(,) 처리하기
- [x] 상품 아이템들 사이에 회색 라인을 추가해서 구분하기
- [x] 상품 선택 시 상품 상세 페이지로 이동
- [x] 상품 상세 페이지로 이동시 intent로 객체를 전달(Parcelize 사용)

### 선택과제

- [x] 스크롤을 최상단으로 이동시키는 플로팅 버튼 기능 추가
- [x] 플로팅 버튼은 스크롤을 아래로 내릴 때 나타나며, 스크롤이 최상단일 때 사라지도록 구현
- [x] 플로팅 버튼을 누르면 스크롤을 최상단으로 이동
- [x] 플로팅 버튼이 나타나고 사라질 때 fade 효과 적용
- [x] 플로팅 버튼을 클릭하면(pressed) 플로팅 아이콘 색 변경
- [x] 상품을 길게 클릭하면 삭제 여부를 묻는 다이얼로그 띄우기
- [x] 확인 선택 시 해당 상품을 삭제하고, 리스트에 반영
- [x] 동 누르면 당근마켓처럼 선택할 수 있는 동 나오도록 UI 구현하기

## 상품 상세 페이지 - DetailPageFragment

- [x] 메인 페이지에서 전달 받은 데이터를 화면에 표시
- [x] 하단 가격 표시 레이아웃을 제외하고 전체화면은 스크롤이 되도록 구현
- [x] 상단 < 버튼을 누르면 상품 상세 페이지를 종료하고 메인 페이지로 이동

### 선택과제

- [x] 상품 상세 페이지에서 좋아요 선택시 아이콘 변경 및 Snackbar 메시지 표시
- [x] 메인 화면으로 돌아오면 해당 상품에 좋아요 표시 및 좋아요 카운트 + 1
- [x] 상세 화면에서 좋아요 해제시 이전 상태로 되돌리기
- [x] 매너온도 누르면 안내 정보 나오도록 구현하기
