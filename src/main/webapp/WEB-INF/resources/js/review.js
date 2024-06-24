/**
 * 
 */
document.addEventListener('DOMContentLoaded', () => {
    let page = 1; // 페이지 번호
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
                    <span>${review.nickname}</span>
                    <span>${review.date}</span>
                </span>
                <span>
                    ${review.content}
                </span>
            `;
            
            reviewContainer.appendChild(reviewDiv);
        });
    }

    // 서버에 AJAX 요청을 보내는 함수 (여기서는 더미 데이터를 사용합니다)
    function fetchReviews() {
        return new Promise((resolve, reject) => {
            setTimeout(() => {
                // 더미 데이터
                const reviews = [
                    {
                        nickname: '닉네임1',
                        date: '작성일자',
                        content: '내가 먹었던 붕어빵 중에서 가장 맛있었음다. 겉바속촉.. 존맛탱.. 슈크림붕어빵이 진리임니다리.. 사장님이 친절하고 붕어빵이 맛있어요 냠냠'
                    },
                    {
                        nickname: '닉네임2',
                        date: '작성일자',
                        content: '내가 먹었던 붕어빵 중에서 가장 맛있었음다. 겉바속촉.. 존맛탱.. 슈크림붕어빵이 진리임니다리.. 사장님이 친절하고 붕어빵이 맛있어요 냠냠'
                    },
                    {
                        nickname: '닉네임3',
                        date: '작성일자',
                        content: '내가 먹었던 붕어빵 중에서 가장 맛있었음다. 겉바속촉.. 존맛탱.. 슈크림붕어빵이 진리임니다리.. 사장님이 친절하고 붕어빵이 맛있어요 냠냠'
                    },
                    // 추가 리뷰 데이터...
                ];
                resolve(reviews);
            }, 3000);
        });
    }

    // 실제 서버에 요청할 때 사용할 함수
    // function fetchReviews(page) {
    //     return fetch(`/api/reviews?page=${page}`,{
    //              method: "POST",
    //                 headers: {
    //                         "Content-Type": "application/json",
    //                 },
    //                 body: JSON.stringify({

    //                 }),
    //          })
    //         .then(response => response.json())
    //         .then(data => data.reviews)
    //         .catch(error => console.error('Error fetching reviews:', error));
    // }

    // 스크롤 이벤트를 감지하는 함수
    function handleScroll() {
        if (window.innerHeight + window.scrollY >= document.documentElement.scrollHeight) {
            window.removeEventListener('scroll', handleScroll);

            spinner.style.display = 'block'; // 스피너 표시

            console.log("스크롤 이벤트 발생");
            fetchReviews().then(reviews => {
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

    // 초기 데이터 로드
    fetchReviews().then(reviews => {
        addReviews(reviews);
    });

    // 스크롤 이벤트 리스너 추가
    window.addEventListener('scroll', handleScroll);

});
