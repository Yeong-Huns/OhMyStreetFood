<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
	<div style="display: flex; flex-direction: row; justify-content: space-between; align-items: center;">
	    <span class="dropdown mt-4">
	        <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">정렬기준</button>
	        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
	            <li><a class="dropdown-item" id="modifiedAtLink" href="">업데이트순</a></li>
	            <li><a class="dropdown-item" id="createdAtLink" href="">오래된순</a></li>
	            <li><a class="dropdown-item" id="distanceLink" href="">가까운순</a></li>
	            <li><a class="dropdown-item" id="likesLink" href="">인기순</a></li>
	        </ul>
	    </span>
	    <span class="mt-4">
	    	<button class="btn btn-outline-primary" type="button" onclick="window.location.href='${pageContext.request.contextPath}/store/createstore'">점포등록</button>
	    </span>
	</div>
</body>
</html>