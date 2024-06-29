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

async function getLatitudeAndLongitude() {
	const latitude = sessionStorage.getItem('latitude');
	const longitude = sessionStorage.getItem('longitude');

	if (latitude === null || longitude === null) {
		console.log("세션 스토리지에 값이 없어 다시 위치 정보를 불러옵니다.");
		const location = await displayLocation();

		sessionStorage.setItem('latitude', location.gps_lat);
		sessionStorage.setItem('longitude', location.gps_lng);

		return { latitude: location.gps_lat, longitude: location.gps_lng };
	} else {
		console.log("현재 위도 값 : " + latitude);
		console.log("현재 경도 값 : " + longitude);
		return { latitude, longitude };
	}
}

async function updateLinkWithCoordinates() {
	try {
		const { latitude, longitude } = await getLatitudeAndLongitude();

		if (latitude && longitude) {
			const queryStr = `?latitude=${latitude}&longitude=${longitude}`;
			const storeLink = document.getElementById('storeLink'); // 링크를 추가할 요소의 ID

			if (storeLink) {
				storeLink.href = `${storeLink.getAttribute('href')}${queryStr}`;
			} else {
				console.error("링크 요소를 찾을 수 없습니다.");
			}

			await fetch('/store/list' + queryStr, {
				method: 'GET',
				headers: {
					'Content-Type': 'application/json'
				},
			});
			
			await fetch('/store/lists' + queryStr, {
				method: 'GET',
				headers: {
					'Content-Type': 'application/json'
				},
			});
		}
	} catch (error) {
		console.error('오류 발생:', error.message);
	}
}

document.addEventListener('DOMContentLoaded', updateLinkWithCoordinates);
