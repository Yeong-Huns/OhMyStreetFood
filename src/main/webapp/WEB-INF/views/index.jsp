<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/spinner.css">
</head>
<body>
    <div class="main">
        <div class="row">
			<div class="col-md-12 text-center" style="font-family: 'Chakra Petch', sans-serif; font-weight: 700; letter-spacing: 10px; text-decoration: underline; text-decoration-color: #007bff;" id="logo">
				<h3>Oh My Street Food!</h3>
			</div>

			<!-- Search -->
            <jsp:include page="search.jsp" />
		
			<!-- KAKAO MAP API -->
			<div class="col-md-12">
				<div id="map" style="width: 100%; height: 400px; border-radius: 20px"></div>
			</div>
			
			<sec:authorize access="isAnonymous()">
				<div class="col-md-12">
					<span style="display: flex; flex-direction: row; justify-content: center; align-items: center; width: 100%; height: 50px; background-color:#e2f0fe; border-radius:10px;">
				    	우리 점포 무료로 홍보하기!&nbsp;<strong><a href="${pageContext.request.contextPath}/signin/owner">사장님 모드</a></strong>
					</span>
				</div>
			</sec:authorize>

			<div class="col-md-12">
                <h5>인기 점포</h5>
                <span class="d-flex flex-wrap" style="display: flex; overflow-x: auto; width: 100%;">
                    <div class="card" style="width:180px; border: none;">
                        <img class="card-img-top" src="${pageContext.request.contextPath}/img/00.jpg" alt="Card image">
                        <div class="card-body">
                            <p class="card-title">Card title</p>
                            <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
                        </div>
                    </div>
                    <div class="card" style="width:180px; border: none;">
                        <img class="card-img-top" src="${pageContext.request.contextPath}/img/00.jpg" alt="Card image">
                        <div class="card-body">
                            <p class="card-title">Card title</p>
                            <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
                        </div>
                    </div>
                    <div class="card" style="width:180px; border: none;">
                        <img class="card-img-top" src="${pageContext.request.contextPath}/img/00.jpg" alt="Card image">
                        <div class="card-body">
                            <p class="card-title">Card title</p>
                            <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
                        </div>
                    </div>
                    <div class="card" style="width:180px; border: none;">
                        <img class="card-img-top" src="${pageContext.request.contextPath}/img/00.jpg" alt="Card image">
                        <div class="card-body">
                            <p class="card-title">Card title</p>
                            <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
                        </div>
                    </div>
                    <div class="card" style="width:180px; border: none;">
                        <img class="card-img-top" src="${pageContext.request.contextPath}/img/00.jpg" alt="Card image">
                        <div class="card-body">
                            <p class="card-title">Card title</p>
                            <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
                        </div>
                    </div>
                </span>
            </div>
		</div>
    </div>
    
    <!-- Menu -->
    <div class="row">
        <div class="col-md-12">
            <jsp:include page="menu.jsp" />
        </div>
    </div>

	<!-- spinner -->
	<div id="spinner-wrapper">
        <div id="spinner"></div>
        <div id="spinner-text">현재 위치를 기반으로 주위 상점을 알아보고 있습니다</div>
    </div> 
	
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

	<!-- kakaoMap API key -->
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d42b402c7a6ae8d76807bdcfbc3a1b41&libraries=services,clusterer,drawing"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/kakaoMap.js"></script>

</body>
</html>
