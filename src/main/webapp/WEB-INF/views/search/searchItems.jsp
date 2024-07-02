<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${stores}" var="store">               
    <div class="card" style="width: 100%; height: 220px; cursor: pointer; margin-bottom: 20px;" onclick="location.href='${pageContext.request.contextPath}/store/${store.storeNo}'">
        <div class="row g-0">
            <div class="col-md-3" style="padding: 0 20px;">
                <c:set var="foundMatch" value="false" />
					<c:forEach items="${pictures}" var="picture">
					    <c:if test="${store.storeNo eq picture.storeNo}">
					        <img src="${picture.picture}" class="card-img-top rounded-circle" alt="사진" style="max-width: 120px; height: 120px;">
					        <c:set var="foundMatch" value="true" />
					    </c:if>
					</c:forEach>
					<c:if test="${not foundMatch}">
					    <img src="${pageContext.request.contextPath}/img/00.jpg" class="card-img-top rounded-circle" alt="사진" style="max-width: 120px; height: 120px;">
					</c:if>
            </div>
            <div class="col-md-9 card-body" style="padding: 0 20px;">
            	<span style="display: flex; flex-direction: row; justify-content: space-between;">
	                <h5 class="card-title">${store.storeName}</h5>
	                <small class="text-muted"><i class="fas fa-location-arrow"></i><strong>&nbsp;${store.distance}km</strong></small>
	            </span>
                <p class="card-text">${store.introduce}</p>
                <p class="card-text">
                    리뷰 ${store.totalReview}
                    평점 ${store.totalRating}
                    찜 ${store.likes}
                </p>
                <p class="card-text"><small class="text-muted">${store.modifiedAt}</small></p>
            </div>
        </div>
    </div>
</c:forEach>

