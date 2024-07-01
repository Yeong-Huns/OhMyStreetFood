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
    
    <script type="text/javascript">
        document.addEventListener("DOMContentLoaded", function() {
            var latitude = sessionStorage.getItem('latitude');
            var longitude = sessionStorage.getItem('longitude');

            if (!latitude || !longitude) {
                console.error('latitude 또는 longitude 값이 sessionStorage에 없습니다.');
                return;
            }

            var searchLinks = document.querySelectorAll('.search-link');
            searchLinks.forEach(function(link) {
                var keyword = link.getAttribute('data-keyword');
                link.href = `/store/list?latitude=${latitude}&longitude=${longitude}&keyword=${keyword}&orderType=modifiedAt`;
            });
        });
    </script>
</head>
<body>
     <!-- Search -->
	<div class="col-md-12">
      <jsp:include page="../search.jsp" />
   	</div>
   	
     <div class="col-md-12">
         인기 검색어
         <p style="padding: 20px 0;">
             <c:forEach items="${searchs}" var="search">
                 <a class="btn btn-outline-primary search-link" style="margin: 5px 0; border-radius: 30px;"
                     data-keyword="${search.KEYWORD}" href="#">
                     &nbsp;${search.KEYWORD}&nbsp;
                 </a>
             </c:forEach>
         </p>
     </div>
    
    <!-- Menu -->
    <div class="row">
        <div class="col-md-12">
            <jsp:include page="../menu.jsp" />
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <script type="text/javascript">
        document.addEventListener("DOMContentLoaded", function() {
            var latitude = sessionStorage.getItem('latitude');
            var longitude = sessionStorage.getItem('longitude');

            if (!latitude || !longitude) {
                console.error('latitude 또는 longitude 값이 sessionStorage에 없습니다.');
                return;
            }

            var searchLinks = document.querySelectorAll('.search-link');
            searchLinks.forEach(function(link) {
                var keyword = link.getAttribute('data-keyword');
                link.href = '/store/list?latitude=' + latitude + '&longitude=' + longitude + '&keyword=' + keyword + '&orderType=modifiedAt';
            });
        });
    </script>
</body>
</html>
