<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ include file="../chat/chatHandler.jsp" %>

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
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/review.css">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/like.css">
<!-- JavaScript -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/addStoreOwner.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/gallery.css">


</head>
<body>
	<div class="main">

		<div id="back" style="padding: 20px 0;">
			<a href="javascript:history.go(-1);" style="text-decoration: none; color: inherit;"> 
				<i class="fas fa-arrow-left"></i>
			</a>
		</div>
		
		<span style="display: flex; flex-direction: row; justify-content: space-between;">
               <span><i class="fas fa-flag"></i><strong>&nbsp;사장님 인증 상점</strong></span>
      			<span><a href="#">사장님과 채팅하기</a></span>
       	</span>
       	
	    <div class="card" style="width: 100%; height: auto; border: none;">
	        <div class="row g-0">
	            <div class="col-md-3" style="padding: 0 20px;">
	           		 <c:choose>
				        <c:when test="${storePhoto.picture != null}">
				            <img id="storePhoto" src="${storePhoto.picture}" class="card-img-top rounded-circle" alt="사진" style="max-width: 120px; height: auto;">
				        </c:when>
				        <c:otherwise>
				            <img id="storePhoto" src="${pageContext.request.contextPath}/img/00.jpg" class="card-img-top rounded-circle" alt="사진" style="max-width: 120px; height: auto;">
				        </c:otherwise>
				    </c:choose>
	            </div>
	            <div class="col-md-9 card-body" style="padding: 0 20px;">
	                    <span style="display: flex; flex-direction: row; justify-content: space-between;">
		                    <span><h5 class="card-title">${store.storeName}</h5></span>
		                    <span><i id="like-btn" class="far fa-heart"></i></span>
	                    </span>
	                    <p class="card-text">${store.introduce}</p>
	                    <p class="card-text">
	                		리뷰 <span id="now-review">${store.totalReview}</span>
	                		평점 <span id="now-rating">${store.totalRating}</span>
	                		찜 <span id="now-like">${store.likes}</span>
		            		<p>
		            		<small class="text-muted">
		            			업데이트
		            		 	<c:choose>
							        <c:when test="${store.modifiedAt != null}">
							            ${store.modifiedAt}
							        </c:when>
							        <c:otherwise>
							            ${store.createdAt}
							        </c:otherwise>
							    </c:choose>
		            		 </small>
		            		 </p>
		                </p>
	            </div>
	        </div>
	    </div>
	    
	    <div>
	    	<span style="display: flex; flex-direction: row; justify-content: space-between; margin-bottom: 20px;">
                 <span><h5>가게 정보</h5></span>
                 <span><a href="${pageContext.request.contextPath}/store/${store.storeNo}/update">정보 수정</a></span>
            </span>
            
            <!-- KAKAO MAP API -->
			<div class="col-md-12" id="map" style="width: 100%; height: 200px; border-radius: 20px"></div>
			<div class="col-md-12" id="result"></div>
				
            <span style="display: flex; flex-direction: row; justify-content: space-between; margin-bottom: 10px;">
		        <span>주&nbsp;&nbsp;&nbsp;&nbsp;소</span>
		        <span>${store.address}</span>
		    </span>
		    
               <span style="display: flex; flex-direction: row; justify-content: space-between; margin-bottom: 10px;">
		        <span>운영일자</span>
		        <span>${store.operatingDate}</span>
		    </span>
			
			<span style="display: flex; flex-direction: row; justify-content: space-between; margin-bottom: 10px;">
		        <span>운영시간</span>
		        <span>${store.operatingHours}</span>
		    </span>
		    
		    <span style="display: flex; flex-direction: row; justify-content: space-between; margin-bottom: 10px;">
		        <span>메&nbsp;&nbsp;&nbsp;&nbsp;뉴</span>
		        <span>&nbsp;</span>
		    </span>
		    
		    <c:forEach items="${menus}" var="menu"> 
			    <span style="display: flex; flex-direction: row; justify-content: space-between; margin-bottom: 10px;">
			        <span>- ${menu.menuName}</span>
			        <span>${menu.price}원</span>
			    </span>
			 </c:forEach>
	    </div>

		<jsp:include page="gallery.jsp" />
		
		<div>
	    	<span style="display: flex; flex-direction: row; justify-content: space-between; margin-bottom: 20px;">
                 <span><h5><spring:message code="review.info" /></h5></span>
                 <button id="openModalBtn"><spring:message code="review.write" /></button>
                </span>
                
			<c:if test="${!empty reviews}">
				<span style="display: flex; flex-direction: column; justify-content: center; align-items: center; width: 100%; height: auto; background-color:#f6f6f6; border-radius:10px; margin-bottom: 20px;">
				    <span style="padding: 20px">
				    	<c:forEach begin="1" end="${store.totalRating}">
				    		<i class="fas fa-star" style="color:#f5b301;"></i>
				    	</c:forEach>
					    <c:if test="${5 - store.totalRating >= 1}">
					    	<c:forEach begin="1" end="${6 - store.totalRating >= 5 ? 5 : 6 - store.totalRating}">
				    			<i class="fas fa-star"></i>
				    		</c:forEach>
					    </c:if>
				    </span>
				</span>
			
				<c:forEach items="${reviews}" var="review">
					<div style="width: 100%; height: auto; background-color:#f6f6f6; border-radius:10px; margin-bottom: 20px;">
				    	<span style="display: flex; flex-direction: row; justify-content: space-between;">
					    	<span>${review.memberUsername}</span>
					    	<span>${review.createdAt}</span>
					    </span>
						<span>${review.content}</span>
					</div>			    
				</c:forEach>
			</c:if>
			
			<c:if test="${empty reviews}">
				<div style="width: 100%; height: auto; background-color:#f6f6f6; border-radius:10px; margin-bottom: 20px;">
			    	<span style="display: flex; flex-direction: row; justify-content: space-between;">
				    	<span><spring:message code="review.nothing" /></span>
				    </span>
				</div>	
			</c:if>

			<div class="col-md-12 text-center">
				<c:if test="${store.totalReview > 5}">
					<a href="${pageContext.request.contextPath}/review/list/${store.storeNo}">
						<spring:message code="review.more" />
					</a>
				</c:if>
				<a href="${pageContext.request.contextPath}/store/report/${store.storeNo}">신고하기</a>
			</div>
	    </div>
	    
	    <!-- 찜 목록 효과 -->
	    <div id="notification-insert" class="notification"><spring:message code="like.insert" /></div>
	    <div id="notification-delete" class="notification"><spring:message code="like.delete" /></div>
	</div>
	
	<!-- 모달 화면 -->
	<div id="modal" class="modal" <c:if test="${modalOn}"> style="display: block;"</c:if>>
        <div class="modal-content">
			<div class="review-container">
				<span class="close-button">&times;</span>
				<h1><spring:message code="review.write" /></h1>
				<form:form id="reviewForm" method="post" modelAttribute="requestReview" action="${pageContext.request.contextPath}/review/insert">
					<p>
						<form:hidden path="storeStoreNo" />
						<form:errors path="storeStoreNo" id="error"/>
					</p>
					<label for="username"><spring:message code="user" /></label>
					<form:input path="memberUsername" disabled="true"/>
					<form:hidden path="memberUsername" />
					
					<label for="rating"><spring:message code="rating" /></label>
					<div class="rating">
						<form:radiobutton path="rating" value="1" id="star1"/>
						<label for="star1" title="1 stars"><i class="fas fa-star"></i></label>
						<form:radiobutton path="rating" value="2" id="star2"/>
						<label for="star2" title="2 stars"><i class="fas fa-star"></i></label>
						<form:radiobutton path="rating" value="3" id="star3"/>
						<label for="star3" title="3 stars"><i class="fas fa-star"></i></label>
						<form:radiobutton path="rating" value="4" id="star4"/>
						<label for="star4" title="4 stars"><i class="fas fa-star"></i></label>
						<form:radiobutton path="rating" value="5" id="star5"/>
						<label for="star5" title="5 stars"><i class="fas fa-star"></i></label>
					</div>
					<label for="content"><spring:message code="review.content" /></label>
					<form:textarea path="content" rows="5" />
					<c:if test="${not empty errors}">
				        <ul>
				            <c:forEach var="error" items="${errors}">
				                <div style="text-align: center;">
									<span id="error">${error.defaultMessage}</span>
								</div>
				            </c:forEach>
				        </ul>
				    </c:if>
					<input id="review-btn" type="submit" value="<spring:message code="write.btn" />" />
				</form:form>
			</div>
        </div>
    </div>

	<!-- kakaoMap API key -->
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d42b402c7a6ae8d76807bdcfbc3a1b41&libraries=services,clusterer,drawing"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/kakaoMapInput.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/storeDetail.js"></script>

	<!-- 리뷰 모달 -->
	<script src="${pageContext.request.contextPath}/js/modal.js"></script>

	<!-- like 요청 -->
	<script src="${pageContext.request.contextPath}/js/likeRequest.js"></script>
	
	<script>
    document.addEventListener('DOMContentLoaded', function() {
        var backElement = document.getElementById('back');

        // Handle the close button click event
        var closeButton = document.querySelector('.close-button');
        if (closeButton) {
            closeButton.addEventListener('click', function() {
                updateBackLink();
            });
        }

        // Handle the form submit event
        var reviewForm = document.getElementById('reviewForm');
        if (reviewForm) {
            reviewForm.addEventListener('submit', function() {
                updateBackLink();
            });
        }

        function updateBackLink() {
            backElement.innerHTML = `
                <a href="${pageContext.request.contextPath}/store/list" style="text-decoration: none; color: inherit;"> 
                    <i class="fas fa-arrow-left"></i>
                </a>`;
        }
    });
	</script>

	<sec:authorize access="isAuthenticated()">
		<!-- like 요청 -->
		<script src="${pageContext.request.contextPath}/js/likeRequest.js"></script>
	</sec:authorize>

</body>
</html>
