const searchButton = document.querySelector('.searchButton');
const dropdownItems = document.querySelectorAll('.dropdown-item');
const storeList = document.getElementById('storeList');
let orderType;
let curPage = 1;

document.addEventListener('DOMContentLoaded', function() {
    fetchStores(); 
});

//정렬기준
dropdownItems.forEach(item => {
    item.addEventListener('click', function(event) {
        event.preventDefault();
        orderType = this.getAttribute('href').split('=')[1];
        curPage = 1;
        fetchStores(); 
    });
});

searchButton.addEventListener('click', function() {
    curPage = 1;
    fetchStores();
});

window.addEventListener('scroll', function() {
    if (window.innerHeight + window.scrollY >= document.body.offsetHeight) {
        fetchStores();
    }
});

function fetchStores() {
    const keyword = document.getElementById("keyword").value;
    console.log("키워드: " + keyword + " 정렬 기준: " + orderType);
    
    let url = "store/list/page?";
    if (orderType !== undefined) {
        url += "order=" + orderType + "&";
    }
    url += "page=" + curPage + "&keyword=" + keyword + "&sort=DESC";
    
    fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    })
    .then(response => response.json())
    .then(data => {
        renderStoreList(data); // 받아온 데이터를 화면에 표시
   		curPage += 1;
    })
    .catch(error => {
        console.error('error', error);
    });
}

function renderStoreList(data) {
	 if (curPage == 1) {
	 storeList.innerHTML = ''; 
	 } 
	 
	 data.forEach(store => {
		const totalReview = store.totalReview ?? 0;
        const totalRating = store.totalRating ?? 0;
        const likes = store.likes ?? 0;
        const introduce = store.introduce ?? '';
        
        const storeCard = `
            <div class="card" style="width: 100%; height: 200px; cursor: pointer; margin-bottom: 20px;" onclick="location.href='store/${store.storeNo}'">
                <div class="row g-0">
                    <div class="col-md-3" style="padding: 0 20px;">
                        <img src="$/img/00.jpg" class="card-img-top rounded-circle" alt="사진" style="max-width: 120px; height: auto;">
                    </div>
                    <div class="col-md-9 card-body" style="padding: 0 20px;">
                        <h5 class="card-title">${store.storeName}</h5>
                        <p class="card-text">${introduce}</p>
                        <p class="card-text">
                            <small class="text-muted">
                                리뷰 ${totalReview}
                                평점 ${totalRating}
                                찜 ${likes}
                            </small>
                        </p>
                    </div>
                </div>
            </div>
        `;
        storeList.innerHTML += storeCard; 
    });
}

