<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${stores}" var="store">               
      <div class="card" style="width: 100%; height: 200px; cursor: pointer; margin-bottom: 20px;" onclick="location.href='${pageContext.request.contextPath}/store/${store.storeNo}'">
          <div class="row g-0">
              <div class="col-md-3" style="padding: 0 20px;">
                  <img src="" class="card-img-top rounded-circle" alt="사진" style="max-width: 120px; height: auto;">
              </div>
              <div class="col-md-9 card-body" style="padding: 0 20px;">
                  <h5 class="card-title">${store.storeName}</h5>
                  <p class="card-text">${store.introduce}</p>
                  <p class="card-text">
                      <small class="text-muted">
                          리뷰 ${store.totalReview}
                          평점 ${store.totalRating}
                          찜 ${store.likes}
                      </small>
                  </p>
              </div>
          </div>
      </div>
  </c:forEach>
