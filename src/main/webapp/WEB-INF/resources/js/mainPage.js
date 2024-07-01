
document.addEventListener("DOMContentLoaded", function() {
    fetch('/store/popular')
        .then(response => response.json())
        .then(data => {
            const storeCarousel = document.getElementById('storeCarousel');
            data.stores.forEach((store, index) => {
                const card = document.createElement('div');
                card.classList.add('item');
                card.innerHTML = `
                    <div class="card">
                        <a href="/store/${store.storeNo}" style="text-decoration: none; color: inherit;">
                            <div class="card-img-wrapper" style="height: 0; padding-top: 100%; position: relative; overflow: hidden;">
                                <img src="${data.pictures[index] ? data.pictures[index].picture : '/img/00.jpg'}" class="card-img-top " alt="${store.storeName}" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; object-fit: cover;">
                            </div>
                            <div class="card-body">
                                <h5 class="card-title">${store.storeName}</h5>
                                <p class="card-text">${store.address.split(' ').slice(0, 2).join(' ')}</p>
                                <p class="card-text text-muted">
                                    리뷰 ${store.totalReview}<br>
                                    평점 ${store.totalRating}<br>
                                    찜 ${store.likes}
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
                        items: 3
                    }
                }
            });
        })
        .catch(error => console.error('Error fetching data:', error));
});
