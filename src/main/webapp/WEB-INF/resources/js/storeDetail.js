
function scrollGallery(direction) {
    const container = document.getElementById('galleryContainer');
    const galleryItems = document.querySelectorAll('.gallery-item');
    const itemWidth = galleryItems[0].offsetWidth + 10; 
    const scrollAmount = itemWidth; 
    
    if (direction === 'left') {
        container.scrollLeft -= scrollAmount;
    } else {
        container.scrollLeft += scrollAmount;
    }
}
