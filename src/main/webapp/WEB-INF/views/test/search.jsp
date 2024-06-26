<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>OhMyStreetFood!</title>
</head>
<body>
   <form class="input-group" id="searchForm" action="/store/search" method="get">
        <input type="text" class="form-control" id="searchInput" name="keyword" placeholder="Search" aria-label="Search" aria-describedby="button-addon2">
        <button class="btn btn-primary" type="submit" id="button-addon2">
            <i class="fas fa-search" style="color: white;"></i>
        </button>
    </form>
</body>
</html>