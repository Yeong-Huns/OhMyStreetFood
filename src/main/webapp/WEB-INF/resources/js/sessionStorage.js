/**
 * author         : leejongseop
 * description    :
 * ===========================================================
 *    AUTHOR            NOTE
 * -----------------------------------------------------------
 *  leejongseop       최초 생성
 */

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
            const urlParams = new URLSearchParams(window.location.search);
            const keyword = urlParams.has('keyword') ? urlParams.get('keyword') : '';

            const queryStr = `?latitude=${latitude}&longitude=${longitude}&keyword=${encodeURIComponent(keyword)}`;

            const linksToUpdate = [
                { id: 'modifiedAtLink', path: '/store/list' },
                { id: 'createdAtLink', path: '/store/list' },
                { id: 'likesLink', path: '/store/list' },
                { id: 'distanceLink', path: '/store/list' }
            ];

            for (let link of linksToUpdate) {
                const element = document.getElementById(link.id);
                if (element) {
                    element.href = `${link.path}${queryStr}`;

                    if (link.id === 'modifiedAtLink' || link.id === 'createdAtLink' || link.id === 'likesLink' || link.id === 'distanceLink') {
                        element.href += `&orderType=${link.id.replace('Link', '')}`;
                    }
                }
            }
            
            const storeLinkElement = document.getElementById('storeLink');
            if (storeLinkElement) {
                storeLinkElement.addEventListener('click', (event) => {
                    event.preventDefault();
                    const clearedKeywordQueryStr = `?latitude=${latitude}&longitude=${longitude}&keyword=`;
                    window.location.href = `${storeLinkElement.href.split('?')[0]}${clearedKeywordQueryStr}`;
                });
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
