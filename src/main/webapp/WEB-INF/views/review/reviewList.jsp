<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
<!-- JavaScript -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/addStoreOwner.js"></script>
</head>
<body>
	<div class="main">
		<div class="row">
			<div>
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
		                <img src="${pageContext.request.contextPath}/img/00.jpg" class="card-img-top rounded-circle" alt="사진" style="max-width: 120px; height: auto;">
		            </div>
		            <div class="col-md-9 card-body" style="padding: 0 20px;">
		                    <span style="display: flex; flex-direction: row; justify-content: space-between;">
			                    <span><h5 class="card-title">${store.storeName}</h5></span>
			                    <span><i class="far fa-heart"></i></span>
								<!-- <i class="fas fa-heart"></i> -->
		                    </span>
		                    <p class="card-text">${store.introduce}</p>
		                    <p class="card-text">
			                		리뷰 ${store.totalReview}
			                		평점 ${store.totalRating}
			                		찜 ${store.likes}
			                </p>
		            		<span><small class="text-muted">업데이트 ${store.modifiedAt}</small></span>
		            </div>
		        </div>
		    </div>
		    
		    <div>
		    	<span style="display: flex; flex-direction: row; justify-content: space-between; margin-bottom: 20px;">
                  <span><h5>가게 정보</h5></span>
                  <span>정보 수정</span>
                 </span>
                 
                <span style="display: flex; flex-direction: row; justify-content: space-between; margin-bottom: 10px;">
			        <span>운영일자</span>
			        <span>${store.operatingDate}</span>
			    </span>
				
				<span style="display: flex; flex-direction: row; justify-content: space-between; margin-bottom: 10px;">
			        <span>운영시간</span>
			        <span>${store.operatingHours}</span>
			    </span>
			    
		    </div>

			<div id="reviewContainer">
		    	<span style="display: flex; flex-direction: row; justify-content: space-between; margin-bottom: 20px;">
                  <span><h5>리뷰 정보</h5></span>
                 </span>
                 
                <span style="display: flex; flex-direction: column; justify-content: center; align-items: center; width: 100%; height: auto; background-color:#f6f6f6; border-radius:10px; margin-bottom: 20px;">
				    <span style="padding: 20px">
					    <i class="fas fa-star"></i>
					    <i class="fas fa-star"></i>
					    <i class="fas fa-star"></i>
					    <i class="far fa-star"></i>
					    <i class="far fa-star"></i>
				    </span>
				</span>
				<c:if test="${empty reviews }">
					<div style="width: 100%; height: auto; background-color:#f6f6f6; border-radius:10px; margin-bottom: 20px;">
			    	<span style="display: flex; flex-direction: row; justify-content: space-between;"></span>
					<span>등록된 리뷰가 없습니다.</span>
				</div>
				</c:if>
				<c:if test="${!empty reviews }">
					<c:forEach var="review" items="${reviews}" >
						<div style="width: 100%; height: auto; background-color:#f6f6f6; border-radius:10px; margin-bottom: 20px;">
					    	<span style="display: flex; flex-direction: row; justify-content: space-between;">
						    	<span>${review.memberUsername}</span>
						    	<span>${review.createdAt}</span>
						    </span>
							<span><a href="<c:url value="/review/${review.reviewNo}" />">${review.content}</a></span>
						</div>			    
					</c:forEach>
				</c:if>
				
				<div id="spinner" class="spinner"></div>
		    </div>
		</div>
	</div>
	
<%-- 	<script src="${pageContext.request.contextPath}/js/review.js"></script> --%>
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

	            spinner.style.display = 'block'; // 스피너 표시

	            console.log("스크롤 이벤트 발생");
	            fetchReviews(page).then(reviews => {
	                addReviews(reviews);
	                page++;
	                spinner.style.display = 'none'; // 스피너 숨김
	                window.addEventListener('scroll', handleScroll);
	            }).catch(error => {
	                console.error('Error fetching reviews:', error);
	                spinner.style.display = 'none'; // 스피너 숨김
	                window.addEventListener('scroll', handleScroll);
	            });
	        }
	    }

	    // 스크롤 이벤트 리스너 추가
	    window.addEventListener('scroll', handleScroll);

	});
	</script>
</body>
</html>
