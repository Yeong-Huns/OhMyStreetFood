var defaultLat = 37.58206193578359; // 기본 위도
var defaultLon = 127.00206048814997; // 기본 경도 => 혜화역 좌표

// 중심좌표 마커 이미지 주소
var centerMarkerImg = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

async function getCurrentLocationAsync() {
    if (!navigator.geolocation) {
        throw new Error("Geolocation is not supported by this browser.");
    }

    return new Promise((resolve, reject) => {
        navigator.geolocation.getCurrentPosition(resolve, reject);
    });
}

async function displayLocation() {
    try {
        const position = await getCurrentLocationAsync();
        const gps_lat = position.coords.latitude;
        const gps_lng = position.coords.longitude;
        console.log(`Latitude: ${gps_lat}, Longitude: ${gps_lng}`);
        return { gps_lat, gps_lng }; // 위치 정보를 반환
    } catch (error) {
        console.error(error.message);
        return { gps_lat: defaultLat, gps_lng: defaultLon }; // 오류 발생 시 기본 위치 반환
    }
}

(async () => {
    const location = await displayLocation();
    var gps_lat = location.gps_lat;
    var gps_lng = location.gps_lng;

    console.log('위도(gps_lat) : ' + gps_lat + ', 경도(gps_lng) : ' + gps_lng);

    var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
        mapOption = { 
            center: new kakao.maps.LatLng(gps_lat, gps_lng), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨
        };

    // 지도를 표시할 div와 지도 옵션으로 지도를 생성
    var map = new kakao.maps.Map(mapContainer, mapOption); 

    // 지도 중심좌표에 마커를 생성
    var marker = new kakao.maps.Marker({ 
        position: map.getCenter(),
        image: new kakao.maps.MarkerImage(centerMarkerImg, new kakao.maps.Size(24, 35))
    });

    marker.setMap(map);

	// 맨 처음 화면 나왔을때 중심 좌표 주소 출력
    searchDetailAddrFromCoords(map.getCenter(), function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            var detailAddr = result[0].address.address_name;
			detailAddr += map.getCenter();
            var resultDiv = document.getElementById('kakaoMap');  
            resultDiv.innerHTML = detailAddr;
        }   
    });

    // 마우스 드래그로 지도 이동이 완료되었을 때 중심 좌표 주소 출력
    kakao.maps.event.addListener(map, 'bounds_changed', function() {        
        var latlng = map.getCenter();

        marker.setPosition(latlng);

        searchDetailAddrFromCoords(latlng, function(result, status) {
            if (status === kakao.maps.services.Status.OK) {
                var detailAddr = result[0].address.address_name;
			detailAddr += latlng;
                var resultDiv = document.getElementById('kakaoMap');  
                resultDiv.innerHTML = detailAddr;
            }   
        });
    });
})();