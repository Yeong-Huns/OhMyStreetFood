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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/spinner.css">
</head>
<body>
	<div class="col-md-12 text-center" id="logo">
		<h3>Oh My Street Food!</h3>
	</div>

	<!-- Search -->
	<div class="col-md-12">
      <jsp:include page="search.jsp" />
   	</div>

	<!-- KAKAO MAP API -->
	<div class="col-md-12">
		<div id="map" style="width: 100%; height: 400px; border-radius: 20px"></div>
	</div>

	<sec:authorize access="isAnonymous()">
		<div class="col-md-12">
			<span style="display: flex; flex-direction: row; justify-content: center; align-items: center; width: 100%; height: 50px; background-color:#e2f0fe; border-radius:10px;">
		    	우리 점포 무료로 홍보하기!&nbsp;<strong><a href="${pageContext.request.contextPath}/signup/owner">사장님 모드</a></strong>
			</span>
		</div>
	</sec:authorize>

	<div class="d-flex flex-wrap" style="display: flex; overflow-x: auto; width: 100%;">
         <h5>인기 점포</h5>
         <span class="d-flex flex-wrap" style="display: flex; overflow-x: auto; width: 100%;">
          <c:forEach items="${stores}" var="store">
		    <div class="card" style="width:180px; cursor: pointer; margin: 5px; border: none;" onclick="location.href='${pageContext.request.contextPath}/store/${store.storeNo}'">
		        <c:if test="${store.storeNo eq picture.storeNo}">
				    <c:choose>
				        <c:when test="${picture.picture != null}">
				            <img class="card-img-top" src="${picture.picture}">
				        </c:when>
				        <c:otherwise>
				            <img class="card-img-top" src="${pageContext.request.contextPath}/resources/img/00.jpg">
				        </c:otherwise>
				    </c:choose>
				</c:if>
		        <div class="card-body">
		            <p class="card-title">${store.storeName}</p>
		            <c:set var="addressWords" value="${fn:split(store.address, ' ')}" />
		            <p class="card-title">${addressWords[0]} ${addressWords[1]}</p>
		            <p class="card-text">
		                <small class="text-muted">
		                    리뷰 ${store.totalReview}<br>
		                    평점 ${store.totalRating}<br>
		                    찜 ${store.likes}<br>
		                </small>
		            </p>
		        </div>
		    </div>
		</c:forEach>
         </span>
     </div>
    
    <!-- Menu -->
    <div class="row">
        <div class="col-md-12">
            <jsp:include page="menu.jsp" />
        </div>
    </div>

	<!-- Spinner -->
	<div id="spinner-wrapper">
        <div id="spinner"></div>
        <div id="spinner-text">현재 위치를 기반으로 주위 상점을 알아보고 있습니다</div>
    </div> 
	
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

	<!-- KakaoMap API key -->
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d42b402c7a6ae8d76807bdcfbc3a1b41&libraries=services,clusterer,drawing"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/kakaoMap.js"></script>

</body>
</html>
