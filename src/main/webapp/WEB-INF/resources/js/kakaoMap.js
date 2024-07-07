/**
 * author         : leejongseop
 * ===========================================================
          AUTHOR             NOTE
 * -----------------------------------------------------------
       leejongseop       최초 생성
 */

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

// 세션 스토리지 저장
function saveLatitudeAndLongitude(latitude, longitude){
	
	if(sessionStorage.getItem('latitude') !== null || 
		sessionStorage.getItem('longitude') !== null){
			console.log("이미 세션스토리지에 값이 있습니다.");
			return;
	}
	
	sessionStorage.setItem('latitude', latitude);
	sessionStorage.setItem('longitude', longitude);
	console.log("현재 위도 값 : " + latitude);
	console.log("현재 경도 값 : " + longitude);
}

function undefinedTodefault(imgURL){
	return imgURL === undefined ? '/img/00.jpg' : imgURL;
}

(async () => {
	const location = await displayLocation();
	var gps_lat = location.gps_lat;
	var gps_lng = location.gps_lng;
	saveLatitudeAndLongitude(gps_lat, gps_lng);

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
//		showSpinner(); // 스피너 표시
//		setTimeout(async () => { // 3초 지연
			try {
				const response = await fetch('/store/api?position=' + address);
				const data = await response.json();
//				const data = JSON.stringify(response.json());
				positions = data; // 서버로부터 받은 데이터로 positions 업데이트
				console.log("data 값 : " + data);
				renderingMarker();
			} catch (error) {
				console.error('Error fetching positions:', error);
			} finally {
//				hideSpinner(); // 스피너 숨김
			}

//		}, 1500);
	}

	// 마커 렌더링
	function renderingMarker() {
		// 기존 마커 제거
		var markers = [];
		
		if (positions) {
			positions.forEach(function (pos) {
				var marker = new kakao.maps.Marker({
					map: map,
					position: new kakao.maps.LatLng(pos.latitude, pos.longitude),
					title: pos.storeName,
					image: new kakao.maps.MarkerImage(imageSrc, new kakao.maps.Size(24, 35))
				});
				markers.push(marker);
				
				// content HTMLElement 생성
				var content = document.createElement('div');
				content.className = 'wrap';
				
				var info = document.createElement('div');
				info.className = 'info';
				content.appendChild(info);
				
				var title = document.createElement('div');
				title.className = 'title';
				title.appendChild(document.createTextNode(pos.storeName));
				
				var close = document.createElement('div');
				close.className = 'close';
				close.setAttribute('onclick', 'closeOverlay()');
				close.setAttribute('title', '닫기');
				title.appendChild(close);
				info.appendChild(title);
				
				var body = document.createElement('div');
				body.className = 'body';
				info.appendChild(body);
				
				var img = document.createElement('div');
				img.className = 'img';
				var imgTag = document.createElement('img');
				imgTag.setAttribute('src', undefinedTodefault(pos.pictureUrl));
				imgTag.setAttribute('width', '73');
				imgTag.setAttribute('height', '70');
				img.appendChild(imgTag);
				body.appendChild(img);
				
				var desc = document.createElement('div');
				desc.className = 'desc';
				body.appendChild(desc);
				
				var ellipsis1 = document.createElement('div');
				ellipsis1.className = 'ellipsis';
				ellipsis1.appendChild(document.createTextNode(pos.address));
				desc.appendChild(ellipsis1);
				
				var ellipsis2 = document.createElement('div');
				ellipsis2.className = 'jibun ellipsis';
				ellipsis2.appendChild(document.createTextNode('평점 ' + pos.totalRating + "   찜  " + pos.likes));
				desc.appendChild(ellipsis2);
				
				var link = document.createElement('div');
				var anchor = document.createElement('a');
				anchor.setAttribute('href', '/store/' + pos.storeNo);
				anchor.setAttribute('target', '_blank');
				anchor.className = 'link';
				anchor.appendChild(document.createTextNode('상세보기'));
				link.appendChild(anchor);
				desc.appendChild(link);

			    // 닫기 이벤트 추가
			    close.onclick = function() {
			        overlay.setMap(null);
			    };
			
//			    content.appendChild(closeBtn);
			    
			    // customoverlay 생성, 이때 map을 선언하지 않으면 지도위에 올라가지 않습니다.
			    var overlay = new daum.maps.CustomOverlay({
			        position: new kakao.maps.LatLng(pos.latitude, pos.longitude),
			        content: content
			    });
			
			    // 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
			    kakao.maps.event.addListener(marker, 'click', function() {
			        overlay.setMap(map);
			    });
			}
			

			)	
		}
	}

	renderingMarker();
	

	// 마우스 드래그로 지도 이동이 완료되었을 때 마커 렌더링 갱신
//	kakao.maps.event.addListener(map, 'idle', function() {
//		renderingMarker();
//	});

	async function searchDetailAddrFromCoords(coords, callback) {
		var geocoder = new kakao.maps.services.Geocoder();
		geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
	}

	// 맨 처음 화면 나왔을 때 중심 좌표 주소 출력 및 서버에 요청
	searchDetailAddrFromCoords(map.getCenter(), function(result, status) {
		if (status === kakao.maps.services.Status.OK) {

			var detailAddr = result[0].address.address_name;
			showSpinner(); // 스피너 표시
			setTimeout(async () => { // 1.5초 지연
				await fetchPositionsByAddress(detailAddr);
				hideSpinner(); // 스피너 숨김
			},1500);
		}
	});
	
	// 마우스 드래그로 지도 이동이 완료되었을 때 중심 좌표 주소 출력
    kakao.maps.event.addListener(map, 'idle', function() {        
        var latlng = map.getCenter();

        searchDetailAddrFromCoords(latlng, function(result, status) {
            if (status === kakao.maps.services.Status.OK) {
                var detailAddr = result[0].address.address_name;
                
                console.log(detailAddr);
				fetchPositionsByAddress(detailAddr);
            }   
        });
    });
	

})();
