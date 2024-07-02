<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>OhMyStreetFood!</title>
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
	<!-- Font Awesome CSS -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
	<!-- Google Fonts -->
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
	<!-- Jquery -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<!-- CSS -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
	
</head>
<body>
		<div class="col-md-12">
			<img src="${pageContext.request.contextPath}/img/logo.png" style="width: 200px">
		</div>
		
		<div class="col-md-12 text-center" id="title">
			<h3>가게 정보 등록</h3>
		</div>
		<form method="post" id="storeForm" enctype="multipart/form-data">
			
			<div>
				<div class="col-md-12" id="map" style="width: 100%; height: 400px; border-radius: 20px"></div>
				<div class="col-md-12" id="result"></div>
			</div>

			<div class="form-group">
				<label for="address">가게 주소<span style="color: red;">&nbsp;*&nbsp;</span></label>
				<input type="text" class="form-control" id="address" name="address" placeholder="가게 주소">
					<input type="hidden" class="form-control" id="latitude" name="latitude" placeholder="위도">
					<input type="hidden" class="form-control" id="longitude" name="longitude" placeholder="경도">
			</div>
			
			<div class="form-group">
				<label for="storeName">가게 이름<span style="color: red;">&nbsp;*&nbsp;</span></label>
				<input type="text" class="form-control" id="storeName" name="storeName" placeholder="가게 이름을 입력하세요">
			</div>

		    <div class="form-group">
		        <label for="operatingDate">가게 운영일자</label>
		        <span class="form-control" id="operatingDate" name="operatingDate" style="padding: 0 0 0 0; border: none;">
		            <input type="checkbox" class="btn-check" id="btn-check-mon" name="days" value="월" autocomplete="off">
		            <label class="btn btn-outline-primary rounded-circle" for="btn-check-mon" data-value="월">월</label>

		            <input type="checkbox" class="btn-check" id="btn-check-tue" name="days" value="화" autocomplete="off">
		            <label class="btn btn-outline-primary rounded-circle" for="btn-check-tue" data-value="화">화</label>
		    
		            <input type="checkbox" class="btn-check" id="btn-check-wed" name="days" value="수" autocomplete="off">
		            <label class="btn btn-outline-primary rounded-circle" for="btn-check-wed" data-value="수">수</label>
		    
		            <input type="checkbox" class="btn-check" id="btn-check-thu" name="days" value="목" autocomplete="off">
		            <label class="btn btn-outline-primary rounded-circle" for="btn-check-thu" data-value="목">목</label>
		    
		            <input type="checkbox" class="btn-check" id="btn-check-fri" name="days" value="금" autocomplete="off">
		            <label class="btn btn-outline-primary rounded-circle" for="btn-check-fri" data-value="금">금</label>
		    
		            <input type="checkbox" class="btn-check" id="btn-check-sat" name="days" value="토" autocomplete="off">
		            <label class="btn btn-outline-primary rounded-circle" for="btn-check-sat" data-value="토">토</label>
		    
		            <input type="checkbox" class="btn-check" id="btn-check-sun" name="days" value="일" autocomplete="off">
		            <label class="btn btn-outline-primary rounded-circle" for="btn-check-sun" data-value="일">일</label>
		        </span>
		    </div>

			<div class="form-group" style="margin-bottom: 15px;">
				<label for="operatingHours">가게 운영시간</label> 
				<span style="display: flex; align-items: center;">
				<input type="time" class="form-control" id="startTime" name="startTime"
						placeholder="시작 시간 선택" style="width: 480px; margin-right: 10px;">
					<span>&nbsp;부터&nbsp;</span>
				<input type="time" class="form-control" id="endTime" name="endTime"
						placeholder="종료 시간 선택" style="width: 480px; margin-right: 10px;">
					<span>&nbsp;까지&nbsp;</span>
				</span>	
			</div>
			
			<div class="form-group">
				<span style="display: flex; flex-direction: row; justify-content: space-between;">
			    <label for="picture">가게 대표 사진</label>
			    <button type="button" class="btn btn-primary" id="photoBtn">추가</button>
			    </span>
			    <span id="preview">
			        <img id="previewImg" src="" alt="이미지 미리보기" class="img-thumbnail" style="display: none; max-width: 200px;">
			    </span>
			    <input class="form-control" id="picture" name="picture" type="file" accept="image/*" style="display: none;">
			</div>

			<div class="form-group">
				<label for="introduce">가게 소개</label>
				<textarea class="form-control" id="introduce" name="introduce" rows="5"></textarea>
			</div>
			
		    <div class="form-group">
		      <label for="menu">메뉴 추가</label>
		      <span style="display: flex; align-items: center;">
		        <span style="width: 500px; margin-right: 10px;">
		        	<input type="text" class="form-control" placeholder="메뉴" id="menuName">
		        </span>
		        <span style="width: 500px; margin-right: 10px;">
			        <input type="number" class="form-control" placeholder="가격" id="menuPrice">
			    </span>
		        <button type="button" class="btn btn-primary" id="addMenuBtn" style="width: 50px;">+</button>
		      </span>

			  <table class="table">
			    <thead>
			      <tr>
			        <th style="width: 410px;">메뉴</th>
			        <th style="width: 410px;">가격</th>
			        <th style="width: 50px;"></th> <!-- 삭제 버튼 열 -->
			      </tr>
			    </thead>
			    <tbody id="menuList">
			      <!-- 동적 추가 -->
			    </tbody>
			  </table>
		    </div>
			
			<div class="col-md-12">
				<button type="submit" class="btn btn-primary" style="height: 50px; width: 100%; margin-bottom: 10px;">등록하기</button>
			</div>
		</form>
	
	<!-- Menu -->
    <div class="row">
        <div class="col-md-12">
            <jsp:include page="../menu.jsp" />
        </div>
    </div>
	
	<script type="module" src="${pageContext.request.contextPath}/js/addStore.js"></script>
	<!-- kakaoMap API key -->
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d42b402c7a6ae8d76807bdcfbc3a1b41&libraries=services,clusterer,drawing"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/kakaoMapInput.js"></script>
</body>
</html>
