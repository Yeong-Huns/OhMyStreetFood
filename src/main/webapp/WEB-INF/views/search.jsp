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
	<form class="input-group" id="searchForm" action="/store/list" method="get">
		<input type="hidden" id="latitudeInput" name="latitude" value="${latitude}">
	    <input type="hidden" id="longitudeInput" name="longitude" value="${longitude}">
		<input type="text" class="form-control" id="searchInput" name="keyword" placeholder="Search"
			aria-label="Search" aria-describedby="button-addon2">
		<button class="btn btn-primary" type="submit" id="button-addon2">
			<i class="fas fa-search" style="color: white;"></i>
		</button>
	</form>
	
	<script>
        document.addEventListener('DOMContentLoaded', function() {
            var latitude = sessionStorage.getItem('latitude');
            var longitude = sessionStorage.getItem('longitude');

            document.getElementById('latitudeInput').value = latitude;
            document.getElementById('longitudeInput').value = longitude;
        });
    </script>
</body>
</html>