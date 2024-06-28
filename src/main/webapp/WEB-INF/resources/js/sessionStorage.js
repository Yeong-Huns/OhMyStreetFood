///**
// * 
// */

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

 // 세션 스토리지 값 가져오기
async function getLatitudeAndLongitude(){
	
	if(sessionStorage.getItem('latitude') === null || 
		sessionStorage.getItem('longitude') === null){
			console.log("세션 스토리지에 값이 없어 다시 위치 정보를 불러옵니다.");
			const location = await displayLocation();
	
			sessionStorage.setItem('latitude', location.gps_lat);
			sessionStorage.setItem('longitude', location.gps_lng);
			
	}
	
	const latitude = sessionStorage.getItem('latitude');
	const longitude = sessionStorage.getItem('longitude');
	
	console.log("현재 위도 값 : " + latitude);
	console.log("현재 경도 값 : " + longitude);
	
	address = {
		latitude: latitude,
		longitude: longitude
	};
	const queryStr = new URLSearchParams(address).toString();
	
	const nowHref = document.getElementById("distance").href;
	
	document.getElementById("distance").href = nowHref + '&' + queryStr;
	
}

(async () => {
	
	getLatitudeAndLongitude();
	
})();

//async function getCurrentLocationAsync() {
//    if (!navigator.geolocation) {
//        throw new Error("Geolocation is not supported by this browser.");
//    }
//
//    return new Promise((resolve, reject) => {
//        navigator.geolocation.getCurrentPosition(resolve, reject);
//    });
//}
//
//async function displayLocation() {
//    try {
//        const position = await getCurrentLocationAsync();
//        const gps_lat = position.coords.latitude;
//        const gps_lng = position.coords.longitude;
//        console.log(`Latitude: ${gps_lat}, Longitude: ${gps_lng}`);
//        return { gps_lat, gps_lng }; // 위치 정보를 반환
//    } catch (error) {
//        console.error(error.message);
//        return { gps_lat: defaultLat, gps_lng: defaultLon }; // 오류 발생 시 기본 위치 반환
//    }
//}
//
//async function getLatitudeAndLongitude() {
//    if (sessionStorage.getItem('latitude') === null || 
//        sessionStorage.getItem('longitude') === null) {
//        console.log("세션 스토리지에 값이 없어 다시 위치 정보를 불러옵니다.");
//        const location = await displayLocation();
//
//        sessionStorage.setItem('latitude', location.gps_lat);
//        sessionStorage.setItem('longitude', location.gps_lng);
//    }
//
//    const latitude = sessionStorage.getItem('latitude');
//    const longitude = sessionStorage.getItem('longitude');
//
//    console.log("현재 위도 값 : " + latitude);
//    console.log("현재 경도 값 : " + longitude);
//
//    const address = {
//        latitude: latitude,
//        longitude: longitude
//    };
//    const queryStr = new URLSearchParams(address).toString();
//
//    const distanceElement = document.getElementById("distance");
//    if (distanceElement) {
//        const nowHref = distanceElement.href;
//        distanceElement.href = nowHref + '&' + queryStr;
//    } else {
//        console.error("요소를 찾을 수 없습니다.");
//    }
//}
//
//document.addEventListener("DOMContentLoaded", async () => {
//    await getLatitudeAndLongitude();
//});
