
document.addEventListener("DOMContentLoaded", function() {
 fetch('/store/popular')
        .then(response => response.json())
        .then(data => {
            const storeCarousel = document.getElementById('storeCarousel');
            data.storeInfos.forEach((storeInfo, index) => {
				const store = storeInfo.store;
				const photo= storeInfo.photo;
                const card = document.createElement('div');
                card.classList.add('item');
                
                card.innerHTML = `
                    <div class="card" style="width: 300px;">
                        <a href="/store/${store.storeNo}" style="text-decoration: none; color: inherit;">
                            <div class="card-img-wrapper" style="height: 0; padding-top: 100%; position: relative; overflow: hidden;">
                                <img src="${store.picture && photo.picture ? photo.picture : '/img/00.jpg'}" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; object-fit: cover;">
                            </div>
                            <div class="card-body">
                                <h5 class="card-title">${store.storeName}</h5>
                                <p class="card-text">${store.address.split(' ').slice(0, 2).join(' ')}</p>
                                <p class="card-text">
                                	<small class="text-muted">
                                    	리뷰 ${store.totalReview}, 평점 ${store.totalRating}, 찜 ${store.likes}
                                    </small>
                                </p>
                            </div>
                        </a>
                    </div>
                `;
                storeCarousel.appendChild(card);
            });

            // Initialize Owl Carousel after content is dynamically added
            $('.owl-carousel').owlCarousel({
                loop: true,
                margin: 10,
                dots: false,
                autoplay: true, 
                autoplayTimeout: 2500,
                responsive: {
                    600: {
                        items: 3.5
                    }
                }
            });
        })
        .catch(error => console.error('Error fetching data:', error));
});
