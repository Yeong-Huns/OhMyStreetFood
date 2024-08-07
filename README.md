<h1>길거리 음식점 웹사이트, 'Oh my street food!'</h1>
<br>

<h3>팀 소개</h3>
<table>
  <tr>
    <td><img src="https://github.com/iamjaeeuncho/OhMyStreetFood/assets/46369038/0da8ee5f-d656-4b35-8041-4871d5cbd573"></td>
    <td><img src="https://github.com/iamjaeeuncho/OhMyStreetFood/assets/46369038/02ce6a13-c264-4e04-bd4c-ac01073281e5"></td>
    <td><img src="https://github.com/iamjaeeuncho/OhMyStreetFood/assets/46369038/e4820f68-8e09-4270-860f-abb3d79a9a71"></td>
    <td><img src="https://github.com/iamjaeeuncho/OhMyStreetFood/assets/46369038/9810f900-cecc-4828-9d1c-345f68beac3f"></td>
    <td><img src="https://github.com/iamjaeeuncho/OhMyStreetFood/assets/46369038/dc809eb2-dd7f-43bc-8c75-eb374aae91ed"></td>
  </tr>
  <tr>
    <td>김창환</td>
    <td>조재은</td>
    <td>이종섭</td>
    <td>이윤빈</td>
    <td>김영훈</td>
  </tr>
</table>
<br>

<h3>프로젝트 소개</h3>
<ul>
  <li>기간: 2024. 06. 17 - 2024. 07. 05 (총 15일)</li>
  <li>주제: 포장마차나 푸드트럭등의 길거리 음식점 정보들이 모여있는 지도 형식의 웹 서비스</li>
</ul>
<br>

<h3>개발 환경</h3>
<ul>
  <li>환경: Spring Tool Suite 4, SQL Developer 20, SQL Developer Modeler 20</li>
  <li>세부 스택: Spring Framework 5 (Legacy), Spring Security 5, Web Socket</li>
  <li>데이터 베이스: MyBatis, Oracle21C</li>
  <li>언어: Java8, JavaScript, Html, Css, JSP, Bootstrap, jQuery, Ajax</li>
  <li>협업도구: Git, GitHub, Google Workspace</li>
</ul>
<br>

<h3>주요 기능</h3>
<ul>
  <li>메인페이지
    <ul>
      <li>현재 위치 기준 등록된 주변 가게 보기</li>
      <li>인기 점포 리스트</li>
      <li>챗봇</li>
    </ul>
    <br>
    <img width="900" src="https://github.com/user-attachments/assets/70fd91af-a5a2-406e-a93b-a1a4d3a695af">
  </li>
  <br>
  
  <li>검색페이지
    <ul>
      <li>점포명 검색창</li>
      <li>Ip 기준 인기 검색어</li>
    </ul>
    <br>
    <img width="900" src="https://github.com/user-attachments/assets/65377cca-b7be-42ec-abb4-04c455dbe5da">
  </li>
  <br>
  
  <li>점포페이지
    <ul>
      <li>현재 위치 기준 거리 보여주기</li>
      <li>등록순, 인기순, 거리순 정렬</li>
      <li>무한 스크롤</li>
    </ul>
    <br>
    <img width="900" src="https://github.com/user-attachments/assets/6966eaad-9020-4589-8a00-230e279a5fa9">
  </li>
  <br>
  
  <li>점포등록페이지
    <ul>
      <li>마커로 주소 자동 입력</li>
      <li>점포 이름, 운영일, 시간, 대표 사진, 소개, 메뉴 추가</li>
    </ul>
    <br>
    <img width="900" src="https://github.com/user-attachments/assets/82cd3bff-fad3-4c63-9543-303ebb259556">
  </li>
  <br>
  
  <li>점포정보페이지
    <ul>
      <li>점포 정보</li>
      <li>사진 갤러리</li>
      <li>리뷰</li>
    </ul>
    <br>
    <img width="900" src="https://github.com/user-attachments/assets/adf72235-eb41-4d35-9e36-08eb5fd26a58">
  </li>
  <br>
  
  <li>로그인페이지
    <ul>
      <li>일반 회원, 사장 회원, 관리자 계정</li>
    </ul>
    <br>
    <img width="1200" src="https://github.com/user-attachments/assets/7be7ff45-88e5-4124-b4a6-01f5e4b3f371">
  </li>
  <br>
  
  <li>회원가입페이지
    <ul>
      <li>일반 회원은 닉네임</li>
      <li>사장 회원은 사업자 번호</li>
    </ul>
    <br>
    <img width="1200" src="https://github.com/user-attachments/assets/982eb981-9e65-49e4-ad00-591fd5269ded">
    <img width="1200" src="https://github.com/user-attachments/assets/e5179f0d-4e6c-4de4-be9a-3aedbf382acb">
  </li>
  <br>
  
  <li>마이페이지
    <ul>
      <li>내가 등록한 가게, 찜한 가게, 쓴 리뷰</li>
      <li>나의 채팅방 내역</li>
    </ul>
    <br>
    <img width="900" src="https://github.com/user-attachments/assets/b8b42fcf-c3ea-48d7-a05a-09989538c01c">
  </li>
  <br>
  
  <li>관리자페이지
    <ul>
      <li>잘못 등록된 정보 회원들이 신고하면 관리자가 확인 후 대응</li>
      <br>
      <img width="900" alt="10" src="https://github.com/iamjaeeuncho/OhMyStreetFood/assets/46369038/2d7da01f-9b6c-4d86-a12f-3ac6113c4d17">
      <li>가게 업데이트된 로그 내용 테이블에 저장 후 어느 시점으로 복구 가능</li>
      <br>
      <img width="900" alt="11" src="https://github.com/iamjaeeuncho/OhMyStreetFood/assets/46369038/91c3eb06-2d29-4438-a790-4ccfc0857a07">
  </li>
  <br>
      
  <li>채팅페이지<br>
    <ul>
      <li>사장님 인증 상점의 경우 1 : 1 채팅 가능</li>
      <br>
      <img width="900" alt="12" src="https://github.com/iamjaeeuncho/OhMyStreetFood/assets/46369038/603427b8-44c4-492c-abb8-90375a83326c">
      <img width="900" alt="13" src="https://github.com/iamjaeeuncho/OhMyStreetFood/assets/46369038/c23fa408-2b8d-4b01-814a-442b6669a803">
  </li>
  <br>

  <li>에러페이지 및 게임
    <img width="900" alt="14" src="https://github.com/iamjaeeuncho/OhMyStreetFood/assets/46369038/5afee1b7-ce33-45f3-9ccb-85cade4e77dd">
    <img width="900" alt="15" src="https://github.com/iamjaeeuncho/OhMyStreetFood/assets/46369038/25b6356f-ab0b-4556-9b6b-d362be27ef42">
  </li>
  <br>
</ul>
