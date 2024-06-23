<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
			    
			    <span style="display: flex; flex-direction: row; justify-content: space-between; margin-bottom: 10px;">
			        <span>메뉴</span>
			        <span>&nbsp;</span>
			    </span>
			    
			    <c:forEach items="${menus}" var="menu"> 
				    <span style="display: flex; flex-direction: row; justify-content: space-between; margin-bottom: 10px;">
				        <span>- ${menu.menuName}</span>
				        <span>${menu.price}</span>
				    </span>
				 </c:forEach>
		    </div>

			<!-- KAKAO MAP API -->
			<div>
				<div class="col-md-12" id="map" style="width: 100%; height: 200px; border-radius: 20px"></div>
				<div class="col-md-12" id="result"></div>
			</div>
			
			<div>
		    	<span style="display: flex; flex-direction: row; justify-content: space-between; margin-bottom: 20px;">
                  <span><h5>사진 갤러리</h5></span>
                  <span>더보기</span>
                 </span>
                 
               <span class="gallery-container">
		  	    <span class="gallery-item"><img src="https://via.placeholder.com/140" alt="사진 1" class="gallery-img"></span>
			    <span class="gallery-item"><img src="https://via.placeholder.com/140" alt="사진 2" class="gallery-img"></span>
			    <span class="gallery-item"><img src="https://via.placeholder.com/140" alt="사진 3" class="gallery-img"></span>
			    <span class="gallery-item"><img src="https://via.placeholder.com/140" alt="사진 4" class="gallery-img"></span>
			    <span class="gallery-item"><img src="https://via.placeholder.com/140" alt="사진 5" class="gallery-img"></span>
			  </span>
			</div>

			<div>
		    	<span style="display: flex; flex-direction: row; justify-content: space-between; margin-bottom: 20px;">
                  <span><h5>리뷰 정보</h5></span>
                  <span>리뷰작성</span>
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
				
				<div style="width: 100%; height: auto; background-color:#f6f6f6; border-radius:10px; margin-bottom: 20px;">
			    	<span style="display: flex; flex-direction: row; justify-content: space-between;">
				    	<span>닉네임</span>
				    	<span>작성일자</span>
				    </span>
					<span>내가 먹었던 붕어빵 중에서 가장 맛있었음다. 겉바속촉.. 존맛탱.. 슈크림붕어빵이 진리임니다리.. 사장님이 친절하고 붕어빵이 맛있어요 냠냠</span>
				</div>			    
				
				<div style="width: 100%; height: auto; background-color:#f6f6f6; border-radius:10px; margin-bottom: 20px;">
			    	<span style="display: flex; flex-direction: row; justify-content: space-between;">
				    	<span>닉네임</span>
				    	<span>작성일자</span>
				    </span>
					<span>내가 먹었던 붕어빵 중에서 가장 맛있었음다. 겉바속촉.. 존맛탱.. 슈크림붕어빵이 진리임니다리.. 사장님이 친절하고 붕어빵이 맛있어요 냠냠</span>
				</div>
				
				<div class="col-md-12 text-center">
					리뷰 더보기
				</div>
		    </div>
		    
		</div>
	</div>

	<!-- kakaoMap API key -->
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d42b402c7a6ae8d76807bdcfbc3a1b41&libraries=services,clusterer,drawing"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/kakaoMapInput.js"></script>
</body>
</html>
