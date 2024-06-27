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
    <!-- CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <div class="main">
        <div class="row">
 	       	<div style="display: flex; flex-direction: row; justify-content: space-between; align-items: center;">
			    <span class="dropdown mt-4">
			        <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">정렬기준</button>
			        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
			            <li><a class="dropdown-item" href="?orderType=createdAt">최신순</a></li>
			            <li><a class="dropdown-item" href="?orderType=likes">인기순</a></li>
			            <li><a class="dropdown-item" href="?orderType=">거리순</a></li>
			        </ul>
			    </span>
			    <span class="mt-4">
			    	<button class="btn btn-outline-primary" type="button" onclick="window.location.href='${pageContext.request.contextPath}/store/addbygeneral'">점포등록</button>
			    </span>
			</div>

			<div class="col-md-12" id="storeList">
				<jsp:include page="store/listStore.jsp">
                    <jsp:param name="stores" value="${stores}" />
                    <jsp:param name="pictures" value="${pictures}" />
                </jsp:include>
            </div>
            
		</div>
    </div>
    
    <!-- Menu -->
    <div class="row">
        <div class="col-md-12">
            <jsp:include page="menu.jsp" />
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
