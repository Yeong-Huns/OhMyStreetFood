<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>OhMyStreetFood!</title>
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<!-- Font Awesome CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<!-- Google Fonts -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap"
	rel="stylesheet">
<!-- Jquery -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<!-- CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/styles.css">
<!-- JavaScript -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/addStoreOwner.js"></script>
</head>
<body>
	<div class="main">
		<div class="row">
			<div>
				<a href="javascript:history.go(-1);"
					style="text-decoration: none; color: inherit;"> <i
					class="fas fa-arrow-left"></i>
				</a>
			</div>
			<div class="col-md-12 text-center" id="logo">
				<h3>가게 정보 등록</h3>
			</div>
			<form method="post"
				action="${pageContext.request.contextPath}/store/addbyowner">
				
				<div>
					<div class="col-md-12" id="map" style="width: 100%; height: 500px; border-radius: 20px"></div>
				</div>
				
				<div class="form-group">
					<label for="storeName">가게 이름</label> <input type="text"
						class="form-control" id="storeName" name="storeName"
						placeholder="가게 이름을 입력하세요">
				</div>

				<div class="form-group">
					<label for="address">가게 주소</label> <input type="text"
						class="form-control" id="address" name="address"
						placeholder="가게 주소">
				</div>
				<div class="form-group">
					<label for="latitude">위도</label> <input type="text"
						class="form-control" id="latitude" name="latitude"
						placeholder="위도">
				</div>
				<div class="form-group">
					<label for="longitude">경도</label> <input type="text"
						class="form-control" id="longitude" name="longitude"
						placeholder="경도">
				</div>
				
				<div class="form-group">
					<label for="picture">가게 대표 사진</label> <input type="text"
						class="form-control" id="picture" name="picture"
						placeholder="가게 대표 사진">
				</div>
				
			    <div class="form-group">
			        <label for="operatingDate">가게 운영일자</label>
			        <div class="form-control" id="operatingDate" name="operatingDate" style="padding: 20px 0 0 0; border: none;">
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
			        </div>
			    </div>


				<div class="form-group" style="margin-bottom: 15px;">
					<label for="operatingHours">가게 운영시간</label> 
					<span style="display: flex; align-items: center;">
					<input type="time" class="form-control" id="startTime" name="startTime"
							placeholder="시작 시간 선택" style="width: 280px; margin-right: 10px;">
						<span>&nbsp;부터&nbsp;</span> 
					<input type="time" class="form-control" id="endTime" name="endTime"
							placeholder="종료 시간 선택" style="width: 280px; margin-right: 10px;">
						<span>&nbsp;까지&nbsp;</span>
					</span>	
				</div>

				<div class="form-group">
					<label for="introduce">가게 소개</label>
					<textarea class="form-control" id="introduce" name="introduce" rows="5"></textarea>
				</div>

				<div class="col-md-12">
					<button type="submit" class="btn btn-primary"
						style="height: 50px; width: 100%; margin-bottom: 10px;">등록하기</button>
				</div>
			</form>
		</div>
	</div>
	
	<!-- kakaoMap API key -->
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d42b402c7a6ae8d76807bdcfbc3a1b41&libraries=services,clusterer,drawing"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/kakaoMapInput.js"></script>
</body>
</html>
