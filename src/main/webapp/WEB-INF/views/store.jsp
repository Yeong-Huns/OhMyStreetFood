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
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

    <div style="display: flex; flex-direction: row; justify-content: space-between; align-items: center;">
    <span class="mt-4">
    	<button class="btn btn-outline-primary" type="button" onclick="window.location.href='${pageContext.request.contextPath}/store/createstore'">점포등록</button>
    </span>
    <span class="dropdown mt-4">
        <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">정렬기준</button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
            <li><a class="dropdown-item" href="?orderType=createdAt">최신순</a></li>
            <li><a class="dropdown-item" href="?orderType=likes">인기순</a></li>
            <li><a class="dropdown-item" id="distance" href="?orderType=distance">거리순</a></li>
        </ul>
    </span>
	</div>

    <div class="col-md-12" id="storeList">
        <jsp:include page="search/searchItems.jsp">
            <jsp:param name="stores" value="${stores}" />
            <jsp:param name="pictures" value="${pictures}" />
        </jsp:include>
    </div>

    <!-- Menu -->
    <div class="row">
        <div class="col-md-12">
            <jsp:include page="menu.jsp" />
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <script>
	    $(document).ready(function() {
	        var offset = ${stores.size()};
	        var isLoading = false;
	        var endOfData = false; 
	
	        $(window).scroll(function() {
	            if ($(window).scrollTop() + $(window).height() >= $(document).height() - 10 && !isLoading && !endOfData) {
	                isLoading = true;
	                loadMoreStores(offset);
	                offset += 5;
	            }
	        });
	
	        function loadMoreStores(offset) {
	            var url = '${pageContext.request.contextPath}/store/lists';
	            var keyword = '${keyword}';
	            var orderType = '${param.orderType}';
	            var limit = 5; 

	            $.ajax({
	                url: url,
	                method: 'GET',
	                data: {
	                    keyword: keyword,
	                    orderType: orderType,
	                    offset: offset,
	                    limit: limit
	                },
	                success: function(response) {
	                    if (response.trim() === "") {
	                        endOfData = true;
	                        $('#storeList').append("<p id='endOfDataMessage' style='text-align:center;'>더 이상 데이터가 없습니다</p>");
	                    } else {
	                        $('#storeList').append(response);
	                    }
	                    isLoading = false;
	                },
	                error: function(xhr, status, error) {
	                    console.error('Error loading more stores:', error);
	                    isLoading = false;
	                }
	            });
	        }

	    });
	</script>

    <!-- 위도, 경도값 적용 JS -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/sessionStorage.js"></script>

</body>
</html>