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
	<link href="https://fonts.googleapis.com/css2?family=Chakra+Petch:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
    <!-- CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <div class="main">
        <div class="row">
			<div class="col-md-12 text-center" style="font-family: 'Chakra Petch', sans-serif; font-weight: 700; letter-spacing: 10px;" id="logo">
				<h3>Oh My Street Food!</h3>
			</div>
		
			<div class="col-md-12">
				<form class="input-group">
					<input type="text" class="form-control" placeholder="Search" aria-label="Search" aria-describedby="button-addon2">
					<button class="btn" type="button" id="button-addon2" style="background-color: #FFA500;">
						<i class="fas fa-search" style="color: white;"></i>
					</button>
				</form>
			</div>
		
			<!-- KAKAO MAP API -->
			<div class="col-md-12" id="map" style="height: 500px;"></div>
			
			<div class="col-md-12" >
				인기 점포
				<div id="result">
				
				</div>
			</div>
		</div>
    </div>
    
    <jsp:include page="menu.jsp" />

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

	<!-- kakaoMap API key -->
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d42b402c7a6ae8d76807bdcfbc3a1b41&libraries=services,clusterer,drawing"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/kakaoMap.js"></script>
</body>
</html>
