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
<!-- JavaScript -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/addStore.js"></script>
<!-- CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/review.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reviewModal.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/like.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/gallery.css">
</head>
<body>
    <div class="col-md-12">
		  <img src="${pageContext.request.contextPath}/img/logo.png" style="width: 200px">
	  </div>
		<span style="display: flex; flex-direction: row; justify-content: space-between; padding: 0 40px;">
              <c:choose>
			    <c:when test="${isOwner eq true }">
			        <span>
			            <i class="fas fa-flag"></i><strong>&nbsp;사장님 인증 상점</strong>
			        </span>
			        <sec:authorize access="hasRole('ROLE_USER')">
			            <span><a href="${pageContext.request.contextPath}/chat" onclick="startChat('${pageContext.request.userPrincipal.name}','${store.storeNo}','${pageContext.request.userPrincipal.name}')">사장님과 채팅하기</a></span>
			        </sec:authorize>
			        <sec:authorize access="isAnonymous()">
			            <span><a href="javascript:void(0)" onclick="showLoginAlert()">사장님과 채팅하기</a></span>
			        </sec:authorize>
			    </c:when>
			    <c:otherwise>
			        <span>
			        	<i class="fas fa-bolt"></i><strong>&nbsp;제보된 상점</strong>
			        </span>
			    </c:otherwise>
			</c:choose>
		</span>
       	
       	<div>
       	<div class="col-md-12">
		    <div class="card" style="width: 100%; height: auto;">
		        <div class="row g-0">
		            <div class="col-md-3" style="padding: 0 40px;">
		           		 <c:choose>
					        <c:when test="${storePhoto.picture != null}">
					            <img id="storePhoto" src="${storePhoto.picture}" class="card-img-top rounded-circle" alt="사진" style="max-width: 120px; height: 120px;">
					        </c:when>
				        <c:otherwise>
					            <img id="storePhoto" src="${pageContext.request.contextPath}/img/00.jpg" class="card-img-top rounded-circle" alt="사진" style="max-width: 120px; height: 120px;">
					        </c:otherwise>
					    </c:choose>
		            </div>
		            <div class="col-md-9 card-body" style="padding: 0 20px;">
	                    <span style="display: flex; flex-direction: row; justify-content: space-between;">
		                    <span><h5 class="card-title">${store.storeName}</h5></span>
		                    	<span>
		                    		<sec:authorize access="hasRole('ROLE_USER')">
		                    			<i class="like-btn far fa-heart" data-store-no="${store.storeNo }"></i>
		                    		</sec:authorize>
		                    	</span>
	                    	</span>
	                    <p class="card-text">${store.introduce}</p>
	                    <p class="card-text">
		                		리뷰 <span id="now-review">${store.totalReview}</span>
		                		평점 <span id="now-rating">${store.totalRating}</span>
		                		찜 <span id="now-like">${store.likes}</span>
		                </p>
	            		<p class="card-text">
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
            		 	<p class="card-text">
	            			<small class="text-muted">
	                    		<sec:authorize access="hasRole('ROLE_USER')">
	                    			<a href="${pageContext.request.contextPath}/store/report/${store.storeNo}">잘못된 정보 신고하기</a>
	                    		</sec:authorize>
	                    		<sec:authorize access="isAnonymous()">	
	                    			<a href="javascript:void(0);" onclick="showLoginAlert()">잘못된 정보 신고하기</a>
	                    		</sec:authorize>
	                    	</small>
                    	</p>
	            	</div>
	        	</div>
	    	</div>
	    </div>
	    	
	    <div>
	    	<span style="display: flex; flex-direction: row; justify-content: space-between; margin-bottom: 20px;">
                 <span><h5>가게 정보</h5></span>
                 <sec:authorize access="isAuthenticated()">
	                 <sec:authentication property="principal.username" var="currentUsername"/>
	                 <c:if test="${isOwner eq false || owner.username eq currentUsername}">
	                 	<span><a href="${pageContext.request.contextPath}/store/${store.storeNo}/update" class="btn btn-primary">정보 수정</a></span>
	                 </c:if>
                 </sec:authorize>
                 <sec:authorize access="isAnonymous()">
                 	<c:if test="${isOwner eq false }">
                 		<span><a href="javascript:void(0);" onclick="showLoginAlert()" class="btn btn-primary">정보 수정</a></span>
                 	</c:if>
				</sec:authorize>
            </span>
            
            
            <!-- KAKAO MAP API -->
			<div class="col-md-12" id="map" style="width: 100%; height: 200px; border-radius: 20px; background-color: "#f6f6f6;"></div>
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
		
		<div class="col-md-12">
			<jsp:include page="gallery.jsp" />
		</div>
			
		<div id="reviewContainer">
	    	<span style="display: flex; flex-direction: row; justify-content: space-between; margin-bottom: 20px;">
                  <span><h5><spring:message code="review.info" /></h5></span>
                  <sec:authorize access="hasRole('ROLE_USER')">
                  	<button id="openModalBtn" class="btn btn-primary"><spring:message code="review.write" /></button>
                  </sec:authorize>
                  <sec:authorize access="isAnonymous()">
                  	<button onclick="showLoginAlert()" class="btn btn-primary"><spring:message code="review.write" /></button>
                  </sec:authorize>
                 </span>
                
			<c:if test="${!empty reviews}">
				<span style="display: flex; flex-direction: column; justify-content: center; align-items: center; width: 100%; height: auto; background-color:#f6f6f6; border-radius:10px; margin-bottom: 20px;">
				    <span style="padding: 20px">
				    	<c:forEach begin="1" end="${store.totalRating}">
				    		<i class="fas fa-star" style="color:#f5b301;"></i>
				    	</c:forEach>
					    <c:if test="${5 - store.totalRating > 0}">
					    	<c:forEach begin="1" end="${store.totalRating % 1 == 0 ? 5 - store.totalRating : 6 - store.totalRating}">
				    			<i class="fas fa-star"></i>
				    		</c:forEach>
					    </c:if>
				    </span>
				</span>
			
				<c:forEach items="${reviews}" var="review">
					<div style="width: 100%; height: auto; background-color:#f6f6f6; border-radius:10px; margin-bottom: 20px;">
				    	<span style="display: flex; flex-direction: row; justify-content: space-between;">
				    		<c:choose>
				    			<c:when test="${not empty review.memberUsername}">
				    				<span><strong>${review.memberUsername}</strong></span>
				    			</c:when>
				    			<c:otherwise>
				    				<span><strong>탈퇴한 회원</strong></span>
				    			</c:otherwise>
				    		</c:choose>
					    	<span>${review.createdAt}</span>
					    </span>
						<sec:authorize access="${not empty review.memberUsername and review.memberUsername eq principal.username}">
			                <a href="<c:url value='/review/${review.reviewNo}'/>">${review.content}</a>
			            </sec:authorize>
			            <c:if test="${empty review.memberUsername or review.memberUsername ne principal.username }">
			                ${review.content}
			            </c:if>
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
			<div>
<!-- 		    	<span style="display: flex; flex-direction: row; justify-content: space-between; margin-bottom: 20px;"> -->
<%--                   <span><h5><spring:message code="review.info" /></h5></span> --%>
<%--                   <sec:authorize access="isAnonymous() or hasRole('ROLE_USER')"> --%>
<%--                   	<button id="openModalBtn"><spring:message code="review.write" /></button> --%>
<%--                   </sec:authorize> --%>
<!--                  </span> -->
                 
<%-- 				<c:if test="${!empty reviews}"> --%>
<!-- 					<span style="display: flex; flex-direction: column; justify-content: center; align-items: center; width: 100%; height: auto; background-color:#f6f6f6; border-radius:10px; margin-bottom: 20px;"> -->
<!-- 					    <span style="padding: 20px"> -->
<%-- 					    	<c:forEach begin="1" end="${store.totalRating}"> --%>
<!-- 					    		<i class="fas fa-star" style="color:#f5b301;"></i> -->
<%-- 					    	</c:forEach> --%>
<%-- 						    <c:if test="${5 - store.totalRating >= 1}"> --%>
<%-- 						    	<c:forEach begin="1" end="${6 - store.totalRating >= 5 ? 5 : 6 - store.totalRating}"> --%>
<!-- 					    			<i class="fas fa-star"></i> -->
<%-- 					    		</c:forEach> --%>
<%-- 						    </c:if> --%>
<!-- 					    </span> -->
<!-- 					</span> -->
				
<%-- 					<c:forEach items="${reviews}" var="review"> --%>
<!-- 						<div style="width: 100%; height: auto; background-color:#f6f6f6; border-radius:10px; margin-bottom: 20px;"> -->
<!-- 					    	<span style="display: flex; flex-direction: row; justify-content: space-between;"> -->
<%-- 						    	<span>${review.memberUsername}</span> --%>
<%-- 						    	<span>${review.createdAt}</span> --%>
<!-- 						    </span> -->
<%-- 							<span>${review.content}</span> --%>
<!-- 						</div>			     -->
<%-- 					</c:forEach> --%>
<%-- 				</c:if> --%>
				
<%-- 				<c:if test="${empty reviews}"> --%>

<%-- 				<c:forEach items="${reviews}" var="review"> --%>

<!-- 					<div style="width: 100%; height: auto; background-color:#f6f6f6; border-radius:10px; margin-bottom: 20px;"> -->
<!-- 				    	<span style="display: flex; flex-direction: row; justify-content: space-between;"> -->
<%-- 					    	<span>${review.memberUsername}</span> --%>
<%-- 					    	<span>${review.createdAt}</span> --%>
<!-- 					    </span> -->
<%-- 						<span><a href="<c:url value="/review/${review.reviewNo}" />">${review.content}</a></span> --%>
<!-- 					</div>			     -->
<%-- 				</c:forEach> --%>
<%-- 			</c:if> --%>
			
<%-- 			<c:if test="${empty reviews}"> --%>
<!-- 				<div style="width: 100%; height: auto; background-color:#f6f6f6; border-radius:10px; margin-bottom: 20px;"> -->
<!-- 			    	<span style="display: flex; flex-direction: row; justify-content: space-between;"> -->
<%-- 				    	<span><spring:message code="review.nothing" /></span> --%>
<!-- 				    </span> -->
<!-- 				</div>	 -->
<%-- 			</c:if> --%>

<!-- 			<div class="col-md-12 text-center"> -->
<%-- 				<c:if test="${store.totalReview > 5}"> --%>
<%-- 					<a href="${pageContext.request.contextPath}/review/list/${store.storeNo}"> --%>
<%-- 						<spring:message code="review.more" /> --%>
<!-- 					</a> -->
<%-- 				</c:if> --%>
<!-- 			</div> -->
			
			<div id="spinner" class="spinner"></div>
	    </div>
	   
	    <!-- 찜 목록 효과 -->
	    <div id="notification-insert" class="notification"><spring:message code="like.insert" /></div>
	    <div id="notification-delete" class="notification"><spring:message code="like.delete" /></div>
	    
	</div>
	
	<!-- 리뷰 모달 화면 -->
	<div id="review-modal" class="review-modal" <c:if test="${modalOn}"> style="display: block;"</c:if>>
        <div class="review-modal-content">
			<div class="review-container">
				<span class="review-close-button">&times;</span>
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
	
	<!-- 신고 모달 화면 -->
	<div class="modal fade" id="reportStoreModal" tabindex="-1" aria-labelledby="reportStoreLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="reportStoreLabel">가게 신고</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                	<form id="reportStoreForm" action="${pageContext.request.contextPath}/store/report/${storeNo}" method="post">
					    <div class="form-group">
					        <label for="title" class="form-label">신고 제목</label>
					        <input type="text" id="title" name="title" placeholder="title" class="form-control"/>
					        <span id="error-title" class="text-danger"></span>
					    </div>
					    <div class="form-group">
					        <label for="content" class="form-label">신고 내용</label>
					        <textarea id="content" name="content" placeholder="content" class="form-control"></textarea>
					        <span id="error-content" class="text-danger"></span>
					    </div>
					    <input type="hidden" name="storeNo" value="${storeNo}"/>
					</form>
                </div>
                <div class="modal-footer">
                	<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                	<button type="submit" class="btn btn-primary" id="reportStoreModalBtn" form="reportStoreForm">신고</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Menu -->
    <div class="row">
        <div class="col-md-12">
            <jsp:include page="../menu.jsp" />
        </div>
    </div>
    
    <!-- Bootstrap JS -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	
	<!-- kakaoMap API key -->
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d42b402c7a6ae8d76807bdcfbc3a1b41&libraries=services,clusterer,drawing"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/storeDetail.js"></script>

	<!-- 리뷰 모달 -->
	<script src="${pageContext.request.contextPath}/js/modal.js"></script>
	<script src="${pageContext.request.contextPath}/js/reviewModal.js"></script>

	<!-- like 요청 -->
<%-- 	<script src="${pageContext.request.contextPath}/js/likeRequest.js"></script> --%>

	<sec:authorize access="isAuthenticated()">
		<!-- like 요청 -->
		<script src="${pageContext.request.contextPath}/js/likeRequest.js"></script>
	</sec:authorize>
	
	<script>
	// 중심좌표 마커 이미지 주소
	var centerMarkerImg = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
	
    console.log('위도(gps_lat) : ' + '${store.latitude}' + ', 경도(gps_lng) : ' + '${store.longitude}');
	
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
        mapOption = { 
            center: new kakao.maps.LatLng('${store.latitude}', '${store.longitude}'), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨
        };

    // 지도를 표시할 div와 지도 옵션으로 지도를 생성
    var map = new kakao.maps.Map(mapContainer, mapOption); 

    // 지도 중심좌표에 마커를 생성
    var marker = new kakao.maps.Marker({ 
        position: map.getCenter(),
        image: new kakao.maps.MarkerImage(centerMarkerImg, new kakao.maps.Size(24, 35))
    });

    marker.setMap(map);


	</script>
	<script>
	document.addEventListener('DOMContentLoaded', () => {
	    let page = 2; // 페이지 번호
	    const reviewContainer = document.getElementById('reviewContainer');

	    // 더미 데이터를 추가하는 함수
	    function addReviews(reviews) {
	        reviews.forEach(review => {
	            const reviewDiv = document.createElement('div');
	            reviewDiv.className = "review";
	            reviewDiv.style.width = '100%';
	            reviewDiv.style.height = 'auto';
	            reviewDiv.style.backgroundColor = '#f6f6f6';
	            reviewDiv.style.borderRadius = '10px';
	            reviewDiv.style.marginBottom = '20px';

	         	// URL을 직접 생성
	            const reviewUrl = `${pageContext.request.contextPath}/review/` + review.reviewNo;
	            
	            // 날짜 포맷
	            const createdAt = new Date(review.createdAt).toLocaleDateString('ko-KR', {
	                year: 'numeric',
	                month: '2-digit',
	                day: '2-digit'
	            }).replace(/\./g, '-').replace(/ /g, '').replace('년', '').replace('월', '').replace('일', '').slice(0,-1);
	            
	            reviewDiv.innerHTML = `
	                <span style="display: flex; flex-direction: row; justify-content: space-between;">
	                    <span> ` + review.memberUsername + `</span>
	                    <span> ` + createdAt + `</span>
	                </span>
	                <span>
	                    <a href= ` + reviewUrl + `>` + review.content + `</a>
	                </span>
	            `;
	            
	            reviewContainer.appendChild(reviewDiv);
	        });
	    }

		 // 실제 서버에 요청할 때 사용할 함수
		 function fetchReviews(page) {
		     return fetch(`${pageContext.request.contextPath}/review/api/${store.storeNo}?page=` + page)
		         .then(response => response.json())
		         .then(reviews => reviews)
		         .catch(error => console.error('Error fetching reviews:', error));
		 }

	    // 스크롤 이벤트를 감지하는 함수
	    function handleScroll() {
	        if (window.innerHeight + window.scrollY >= document.documentElement.scrollHeight) {
	            window.removeEventListener('scroll', handleScroll);

	            showSpinner(); // 스피너 표시

	            console.log("스크롤 이벤트 발생");
	            setTimeout(async () => { // 3초 지연
	            fetchReviews(page).then(reviews => {
	                addReviews(reviews);
	                page++;
	                hideSpinner(); // 스피너 숨김
	                window.addEventListener('scroll', handleScroll);
	            }).catch(error => {
	                console.error('Error fetching reviews:', error);
	                hideSpinner(); // 스피너 숨김
	                window.addEventListener('scroll', handleScroll);
	            })
	            }, 2000);
	        }
	    }
	    
	 	// 스피너 표시 함수
	    function showSpinner() {
	    	document.getElementById('spinner').style.display = 'block';
	    }

	    // 스피너 숨김 함수
	    function hideSpinner() {
	    	document.getElementById('spinner').style.display = 'none';
	    }

	    // 스크롤 이벤트 리스너 추가
	    window.addEventListener('scroll', handleScroll);
	});
	</script>
	<script>
    function showLoginAlert() {
	    alert('로그인이 필요합니다.');
	}
	</script>
	<script>
		$(document).ready(function() {
		    $('#reportStoreModalBtn').click(function(event) {
		        event.preventDefault(); // 기본 폼 제출을 방지
	
		        var form = $('#reportStoreForm')[0];
		        var formData = new FormData(form);
	
		        $.ajax({
		            url: $('#reportStoreForm').attr('action'), // 폼의 action 속성 값 사용
		            type: 'POST',
		            data: formData,
		            processData: false,
		            contentType: false,
		            success: function(data) {
		                if (data.errors) {
		                    // 유효성 검사 오류가 있는 경우
		                    $('#error-title').text(data.errors.title || "");
		                    $('#error-content').text(data.errors.content || "");
		                } else {
		                    // 성공적으로 신고가 접수된 경우 모달 닫기
		                    var myModal = bootstrap.Modal.getInstance(document.getElementById('reportStoreModal'));
		                    myModal.hide();
		                    // 성공 시 리디렉션을 원하는 경로로 수행
		                    window.location.href = `${pageContext.request.contextPath}/store/${data.storeNo}`;
		                }
		            },
		            error: function(xhr) {
		                var response = JSON.parse(xhr.responseText);
		                $('#error-title').text(response.errors.title || "");
		                $('#error-content').text(response.errors.content || "");
		            }
		        });
		    });
		});
	</script>
</body>
</html>
