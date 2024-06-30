/**
 * 
 */
document.addEventListener('DOMContentLoaded', () => {
    let page = 2; // 페이지 번호
    const reviewContainer = document.getElementById('reviewContainer');

    // 더미 데이터를 추가하는 함수
    function addReviews(reviews) {
        reviews.forEach(review => {
            const reviewDiv = document.createElement('div');
            reviewDiv.className = "review";
            reviewDiv.style.width = '100%';
            reviewDiv.style.height = 'auto';
            reviewDiv.style.backgroundColor = '#f6f6f6';
            reviewDiv.style.borderRadius = '10px';
            reviewDiv.style.marginBottom = '20px';

            reviewDiv.innerHTML = `
                <span style="display: flex; flex-direction: row; justify-content: space-between;">
                    <span>${review.memberUsername}</span>
                    <span>${review.createdAt}</span>
                </span>
                <span>
                    <a href="<c:url value="/review/${review.reviewNo}" />">${review.content}</a>
                </span>
            `;
            
            reviewContainer.appendChild(reviewDiv);
        });
    }

	 // 실제 서버에 요청할 때 사용할 함수
	 function fetchReviews(page) {
	     return fetch(`${pageContext.request.contextPath}/review/api?page=${page}`)
	         .then(response => response.json())
	         .then(data => data.reviews)
	         .catch(error => console.error('Error fetching reviews:', error));
	 }

    // 스크롤 이벤트를 감지하는 함수
    function handleScroll() {
        if (window.innerHeight + window.scrollY >= document.documentElement.scrollHeight) {
            window.removeEventListener('scroll', handleScroll);

            spinner.style.display = 'block'; // 스피너 표시

            console.log("스크롤 이벤트 발생");
            fetchReviews(page).then(reviews => {
                addReviews(reviews);
                page++;
                spinner.style.display = 'none'; // 스피너 숨김
                window.addEventListener('scroll', handleScroll);
            }).catch(error => {
                console.error('Error fetching reviews:', error);
                spinner.style.display = 'none'; // 스피너 숨김
                window.addEventListener('scroll', handleScroll);
            });
        }
    }

    // 스크롤 이벤트 리스너 추가
    window.addEventListener('scroll', handleScroll);

});
