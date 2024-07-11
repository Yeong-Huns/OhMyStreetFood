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
	<div class="container">
	    <!-- Oderby -->
	    <jsp:include page="orderby.jsp" />
	
	    <div class="col-md-12" id="storeList">
	        <jsp:include page="search/searchItems.jsp">
	            <jsp:param name="stores" value="${stores}" />
	            <jsp:param name="pictures" value="${pictures}" />
	        </jsp:include>
	    </div>
	</div>

    <!-- Menu -->
    <jsp:include page="menu.jsp" />

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <script>
    document.addEventListener('DOMContentLoaded', function() {
        var offset = ${stores.size()};
        var isLoading = false;
        var endOfData = false; 
        var latitude = sessionStorage.getItem('latitude');
        var longitude = sessionStorage.getItem('longitude');
        
        window.addEventListener('scroll', function() {
            if (window.scrollY + window.innerHeight >= document.documentElement.scrollHeight - 10 && !isLoading && !endOfData) {
                isLoading = true;
                loadMoreStores(offset);
                offset += 5;
            }
        });

        function loadMoreStores(offset) {
            var url = '/store/lists';
            var keyword = '${keyword}';
            var orderType = '${param.orderType}';
            var limit = 5;

            fetch(url + '?keyword=' + keyword + '&orderType=' + orderType + '&latitude=' + latitude + '&longitude=' + longitude + '&offset=' + offset + '&limit=' + limit)
                .then(function(response) {
                    return response.text();
                })
                .then(function(data) {
                    if (data.trim() === "") {
                        endOfData = true;
                        var endOfDataMessage = document.createElement('p');
                        endOfDataMessage.id = 'endOfDataMessage';
                        endOfDataMessage.style.textAlign = 'center';
                        endOfDataMessage.textContent = '더 이상 데이터가 없습니다';
                        document.getElementById('storeList').appendChild(endOfDataMessage);
                    } else {
                        document.getElementById('storeList').insertAdjacentHTML('beforeend', data);
                    }
                    isLoading = false;
                })
                .catch(function(error) {
                    console.error('Error loading more stores:', error);
                    isLoading = false;
                });
        }
    });
	</script>

    <!-- 위도, 경도값 적용 JS -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/sessionStorage.js"></script>

</body>
</html>