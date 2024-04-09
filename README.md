# 요구사항 분석

## 전체적인 부분

- [ ] RecyclerView, Fragment, Dialog, 알림 활용
- [x] 상단 툴바, 하단바 제거하고 풀스크린 화면으로 설정
- [ ] 당근마켓 디자인 및 화면 구성과 최대한 동일하게 구성(당근 컬러 값 적용)

## 메인 페이지 - MainPageFragment

- [x] 상품 데이터는 dummy data 사용
- [x] RecyclerView를 이용해서 제품을 랜덤한 순서로 표시
- [x] 상품 이미지는 모서리를 라운드로 처리
- [x] 상품 이름은 최대 두 줄이고, 초과하면 ellipsize 적용
- [x] 뒤로가기 버튼 누르면 앱 종료 여부를 물어보는 다이얼로그 띄우기
- [ ] 상단 종모양 아이콘을 누르면 Notification을 생성하기
- [x] 상품 가격은 1000단위로 콤마(,) 처리하기
- [x] 상품 아이템들 사이에 회색 라인을 추가해서 구분하기
- [x] 상품 선택 시 상품 상세 페이지로 이동
- [x] 상품 상세 페이지로 이동시 intent로 객체를 전달(Parcelize 사용)

### 선택과제

- [ ] 스크롤을 최상단으로 이동시키는 플로팅 버튼 기능 추가
- [ ] 플로팅 버튼은 스크롤을 아래로 내릴 때 나타나며, 스크롤이 최상단일 때 사라지도록 구현
- [ ] 플로팅 버튼을 누르면 스크롤을 최상단으로 이동
- [ ] 플로팅 버튼이 나타나고 사라질 때 fade 효과 적용
- [ ] 플로팅 버튼을 클릭하면(pressed) 플로팅 아이콘 색 변경
- [ ] 상품을 길게 클릭하면 삭제 여부를 묻는 다이얼로그 띄우기
- [ ] 확인 선택 시 해당 상품을 삭제하고, 리스트에 반영
- [ ] RecyclerView를 최상단으로 당기고 놓으면 제품 다시 로딩하기

## 상품 상세 페이지 - DetailPageFragment

- [ ] 메인 페이지에서 전달 받은 데이터를 화면에 표시
- [ ] 하단 가격 표시 레이아웃을 제외하고 전체화면은 스크롤이 되도록 구현
- [ ] 상단 < 버튼을 누르면 상품 상세 페이지를 종료하고 메인 페이지로 이동

### 선택과제

- [ ] 상품 상세 페이지에서 좋아요 선택시 아이콘 변경 및 Snackbar 메시지 표시
- [ ] 메인 화면으로 돌아오면 해당 상품에 좋아요 표시 및 좋아요 카운트 + 1
- [ ] 상세 화면에서 좋아요 해제시 이전 상태로 되돌리기
