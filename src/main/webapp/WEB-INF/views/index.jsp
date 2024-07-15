<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ include file="chat/chatHandler.jsp" %>
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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.theme.default.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/spinner.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/customOverlay.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/chatbot.css">
</head>
    <style>
    	.owl-item {
    		padding: 0px;
    		margin: 0px;
    	}
    	.owl-stage-outer {
    		padding: 0px;
    		margin: 0px;
    	}
    	.owl-stage {
    		padding: 0px;
    	}
        .owl-carousel .item {
            padding: 0px;
            margin: 5px;
            text-align: center;
        }
        .owl-carousel .item img {
            display: block;
            width: 100%;
            height: auto;
        }
        .owl-carousel owl-theme owl-loaded owl-drag {
        	padding: 0px;
    		margin: 0px;
        }
        #storeCarousel .owl-stage {
        	display: flex;
        	flex-wrap: nowrap; /* Prevent items from wrapping to the next line */
    	}
    </style>
<body>
	<div class="container" style="padding: 60px 40px 0px 40px;">
		
		<!-- KAKAO MAP API -->
		<div class="col-md-12">
			<div id="map" style="width: 100%; height: 500px; border-radius: 20px;"></div>
		</div>
	
		<sec:authorize access="isAnonymous()">
			<div class="col-md-12">
				<span style="display: flex; flex-direction: row; justify-content: center; align-items: center; width: 100%; height: 50px; background-color:#e2f0fe; border-radius:10px;">
			    	우리 점포 무료로 홍보하기!&nbsp;<strong><a href="${pageContext.request.contextPath}/signup/owner">사장님 모드</a></strong>
				</span>
			</div>
		</sec:authorize>
	
		<!-- Store Carousel -->
		<div class="d-flex flex-wrap" style="display: flex; overflow-x: auto; width: 100%;">
			<h4>인기 점포</h4>
			<div id="storeCarousel" class="owl-carousel owl-theme">
			</div>
		</div>

		<!-- Spinner -->
		<div id="spinner-wrapper">
	        <div id="spinner"></div>
	        <div id="spinner-text">현재 위치를 기반으로 주위 상점을 알아보고 있습니다</div>
	    </div> 
	    
	</div>
    <!-- chatbot -->
	<jsp:include page="chatbot.jsp" />
	
	<!-- Menu -->
	<jsp:include page="menu.jsp" />
	
	<!-- Footer -->
   	<jsp:include page="footer.jsp" />
   	
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

	<!-- KakaoMap API key -->
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d42b402c7a6ae8d76807bdcfbc3a1b41&libraries=services,clusterer,drawing"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/kakaoMap.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/mainPage.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js"></script>

</body>
</html>
