<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title></title>
</head>
<body>
		<div>
		    <span style="display: flex; flex-direction: row; justify-content: space-between; margin-bottom: 20px;">
		        <span><h5>사진 갤러리</h5></span>
		        
		    </span>
		    
		    <div class="gallery-wrapper">
		        <c:choose>
		            <c:when test="${not empty gallery}">
		                <button class="scroll-button left" onclick="scrollGallery('left')">&lt;</button>
		                <div class="gallery-container" id="galleryContainer">
		                    <c:forEach items="${gallery}" var="item" varStatus="loop">
		                        <span class="gallery-item">
		                            <img src="${item.picture}" alt="${store.storeName} 가게사진 ${loop.index + 1}" class="gallery-img">
		                        </span>
		                    </c:forEach>
		                </div>
		                <button class="scroll-button right" onclick="scrollGallery('right')">&gt;</button>
		            </c:when>
		            <c:otherwise>
		                <p>등록된 사진이 없습니다.</p>
		            </c:otherwise>
		        </c:choose>
		    </div>
			</div>
</body>
</html>
