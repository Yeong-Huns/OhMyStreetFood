var defaultLat = 37.58206193578359; // 기본 위도
var defaultLon = 127.00206048814997; // 기본 경도

// 마커 이미지의 이미지 주소
var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

// 중심좌표 마커 이미지 주소
var centerMarkerImg = "http://t1.daumcdn.net/localimg/localimages/07/2018/pc/img/marker_spot.png";

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

// 스피너 표시 함수
function showSpinner() {
	document.getElementById('spinner-wrapper').style.display = 'block';
}

// 스피너 숨김 함수
function hideSpinner() {
	document.getElementById('spinner-wrapper').style.display = 'none';
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

	// 마커를 렌더링할 위치 배열
	var positions = [];

	// 서버로부터 데이터를 받아와서 positions 배열을 업데이트
	async function fetchPositionsByAddress(address) {
		console.log(address);
		showSpinner(); // 스피너 표시
		setTimeout(async () => { // 3초 지연
			try {
				const response = await fetch('/store/api?' + address);
				const data = await response.json();
				positions = data; // 서버로부터 받은 데이터로 positions 업데이트
				console.log(data);
				renderingMarker();
			} catch (error) {
				console.error('Error fetching positions:', error);
			} finally {
				hideSpinner(); // 스피너 숨김
			}
		}, 3000);
	}

	// 마커 렌더링
	function renderingMarker() {
		// 기존 마커 제거
		var markers = [];
		if (positions) {
			for (var i = 0; i < positions.length; i++) {
				var marker = new kakao.maps.Marker({
					map: map,
					position: new kakao.maps.LatLng(positions[i].longitude, positions[i].latitude),
					title: positions[i].storeName,
					image: new kakao.maps.MarkerImage(imageSrc, new kakao.maps.Size(24, 35))
				});
				markers.push(marker);
			}
		}
	}

	renderingMarker();

	// 마우스 드래그로 지도 이동이 완료되었을 때 마커 렌더링 갱신
	kakao.maps.event.addListener(map, 'idle', function() {
		renderingMarker();
	});

	function searchDetailAddrFromCoords(coords, callback) {
		var geocoder = new kakao.maps.services.Geocoder();
		geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
	}

	// 맨 처음 화면 나왔을 때 중심 좌표 주소 출력 및 서버에 요청
	searchDetailAddrFromCoords(map.getCenter(), function(result, status) {
		if (status === kakao.maps.services.Status.OK) {

			var detailAddr = result[0].address.address_name;
			console.log("위도 : " + map.getCenter().getLng().toString());
			console.log("경도 : " + map.getCenter().getLat().toString());
			address = {
				longitude: map.getCenter().getLng().toString(),
				latitude: map.getCenter().getLat().toString()
			};
			const queryStr = new URLSearchParams(address).toString();
			console.log(queryStr);
			fetchPositionsByAddress(queryStr); // 주소를 사용하여 서버에 요청
//			fetchPositionsByAddress(detailAddr);
		}
	});

})();
