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

				<h3>가게 정보</h3>
			</div>
			<form>
				<div class="form-group">
					<label for="exampleInputEmail1">가게 이름</label> <input type="email"
						class="form-control" id="exampleInputEmail1"
						aria-describedby="emailHelp" placeholder="Email">
				</div>
				<div class="form-group">
					<label for="exampleInputEmail1">가게 주소</label> <input type="email"
						class="form-control" id="exampleInputEmail1"
						aria-describedby="emailHelp" placeholder="Email"> 카카오 API
				</div>
				<div class="form-group">
					<label for="exampleInputEmail1">가게 대표 사진</label> <input
						type="email" class="form-control" id="exampleInputEmail1"
						aria-describedby="emailHelp" placeholder="Email">
				</div>
				<div class="form-group">
					<label for="exampleInputEmail1">가게 운영일자</label>
					<div class="buttons" style="padding: 20px 0 0 0">
						<button type="button"
							class="btn btn-outline-primary rounded-circle">월</button>
						<button type="button"
							class="btn btn-outline-primary rounded-circle">화</button>
						<button type="button"
							class="btn btn-outline-primary rounded-circle">수</button>
						<button type="button"
							class="btn btn-outline-primary rounded-circle">목</button>
						<button type="button"
							class="btn btn-outline-primary rounded-circle">금</button>
						<button type="button"
							class="btn btn-outline-primary rounded-circle">토</button>
						<button type="button"
							class="btn btn-outline-primary rounded-circle">일</button>
					</div>
				</div>
				<div class="form-group" style="margin-bottom: 15px;">
					<label for="exampleInputEmail1">가게 운영시간</label> <span
						style="display: flex; align-items: center;"> <input
						type="time" class="form-control" id="inputStartTime"
						placeholder="시작 시간 선택" style="width: 280px; margin-right: 10px;">
						<span>&nbsp;부터&nbsp;</span> <input type="time"
						class="form-control" id="inputEndTime" placeholder="종료 시간 선택"
						style="width: 280px; margin-right: 10px;"> <span>&nbsp;까지&nbsp;</span>
					</span>
				</div>
				<div class="form-group">
					<label for="exampleTextarea">가게 소개</label>
					<textarea class="form-control" id="exampleTextarea" rows="5"></textarea>
				</div>
				<div class="form-group">
					<label for="buttonText">메뉴 추가</label> <span
						style="display: flex; align-items: center;"> <input
						type="text" id="menuInput" class="form-control"
						style="width: 300px; margin-right: 10px;" placeholder="메뉴를 입력하세요">
						<input type="number" id="priceInput" class="form-control"
						style="width: 300px; margin-right: 10px;" placeholder="가격을 입력하세요">
						<button type="button" class="btn btn-primary" onclick="addMenu()">등록</button>
					</span>

				</div>
				<div id="menuContainer" style="width: 100%;">
					<!-- 동적 생성 -->
				</div>

				<div class="col-md-12">
					<button type="button" class="btn btn-primary"
=======
				<h3>가게 정보 등록</h3>
			</div>
			<form method="post"
				action="${pageContext.request.contextPath}/store/addbyowner">
				
				<div>
					<div class="col-md-12" id="map" style="width: 100%; height: 500px; border-radius: 20px"></div>
					<div class="col-md-12" id="result"></div>
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

	<script>
		document.getElementById('inputEndTime')
				.addEventListener(
						'change',
						function() {
							var startTime = document
									.getElementById('inputStartTime').value;
							var endTime = this.value;

							// 시간을 Date 객체로 변환하여 비교
							var startDate = new Date('2000-01-01T' + startTime
									+ ':00');
							var endDate = new Date('2000-01-01T' + endTime
									+ ':00');

							if (endDate <= startDate) {
								alert('종료 시간은 시작 시간보다 커야 합니다.');
								// 종료 시간을 시작 시간으로 설정
								this.value = startTime;
							}
						});

		function addMenu() {
            var menuInput = document.getElementById('menuInput').value;
            var priceInput = document.getElementById('priceInput').value;

            var menuContainer = document.getElementById('menuContainer');

            var card = document.createElement('span');
            card.classList.add('card');
            card.style.height = '50px';
            card.style.margin = "20px 0 0 0";
//             card.style.border = 'none';

            var cardBody = document.createElement('span');
            cardBody.classList.add('card-body', 'd-flex', 'justify-content-between', 'align-items-center');
            cardBody.style.padding = '5px';
//             cardBody.style.border = 'none';

            var menuName = document.createElement('span');
            menuName.textContent = menuInput;
            menuName.style.whiteSpace = 'nowrap';
            menuName.style.overflow = 'hidden';
            menuName.style.maxWidth = '40%';

            var menuPrice = document.createElement('span');
            menuPrice.textContent = priceInput;
            menuPrice.style.whiteSpace = 'nowrap';
            menuPrice.style.overflow = 'hidden';
            menuPrice.style.maxWidth = '40%';

            var deleteButton = document.createElement('button');
            deleteButton.textContent = '―';
            deleteButton.classList.add('btn', 'btn-danger', 'btn-sm', 'rounded-circle');
            deleteButton.onclick = function() {
                menuContainer.removeChild(card);
            };

            cardBody.appendChild(menuName);
            cardBody.appendChild(menuPrice);
            cardBody.appendChild(deleteButton);
            card.appendChild(cardBody);
            menuContainer.appendChild(card);

            document.getElementById('menuInput').value = "";
            document.getElementById('priceInput').value = "";
        }
	</script>

	<!-- kakaoMap API key -->
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d42b402c7a6ae8d76807bdcfbc3a1b41&libraries=services,clusterer,drawing"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/kakaoMapInput.js"></script>
	<script>
        window.onload = function() {
            var success = '${success}';
            if (success === true) {
                alert('회원가입이 완료되었습니다.');
            }
        }
	</script>
</body>
</html>
