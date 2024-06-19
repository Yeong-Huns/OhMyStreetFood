/**
 * 
 */

 

var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption); 

//// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();






// 마커를 표시할 위치와 title 객체 배열입니다 
var positions = [
    {
        title: '카카오', 
        latlng: new kakao.maps.LatLng(33.450705, 126.570677)
    },
    {
        title: '생태연못', 
        latlng: new kakao.maps.LatLng(33.450936, 126.569477)
    },
    {
        title: '텃밭', 
        latlng: new kakao.maps.LatLng(33.450879, 126.569940)
    },
    {
        title: '근린공원',
        latlng: new kakao.maps.LatLng(33.451393, 126.570738)
    }
];

var positions = getRandomPositions(30);

// 마커 이미지의 이미지 주소입니다
var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
    



var marker = new kakao.maps.Marker({ 
    // 지도 중심좌표에 마커를 생성합니다 
    position: map.getCenter() 
});
marker.setMap(map);
renderingMarker();


kakao.maps.event.addListener(map, 'idle', function() {
	      
    renderingMarker();   
    console.log("드래그 완료");   
});



// 마우스 드래그로 지도 이동이 완료되었을 때 마지막 파라미터로 넘어온 함수를 호출하도록 이벤트를 등록합니다
kakao.maps.event.addListener(map, 'bounds_changed', function() {        
//    renderingMarker();
    // 지도 중심좌표를 얻어옵니다 
    var latlng = map.getCenter();
    
    console.log(map.getBounds());
    
    var message = '변경된 지도 중심좌표는 ' + latlng.getLat() + ' 이고, ';
    message += '경도는 ' + latlng.getLng() + ' 입니다';
    
    marker.setPosition(latlng);
    
//    var resultDiv = document.getElementById('result');  
//    resultDiv.innerHTML = message;

	
//	searchAddrFromCoords(latlng, displayCenterInfo);
//	console.log(message);
	
	searchDetailAddrFromCoords(latlng, function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
//            detailAddr = '<div>지번 주소 : ' + result[0].address.address_name + '</div>';
            detailAddr = result[0].address.address_name;
            var resultDiv = document.getElementById('result');  
    		resultDiv.innerHTML = detailAddr;

        }   
    });
    
    
});

function renderingMarker(){
	
	// 지도의 영역
	console.log(map.getBounds());
	
	var bounds = map.getBounds();
	// 영역의 남서쪽 좌표를 얻어옵니다 
	var swLatLng = bounds.getSouthWest(); 
	    
	// 영역의 북동쪽 좌표를 얻어옵니다 
	var neLatLng = bounds.getNorthEast();  
	
	var sw = new kakao.maps.LatLng(swLatLng.getLat(), swLatLng.getLng()),
	    ne = new kakao.maps.LatLng(neLatLng.getLat(), neLatLng.getLng()),
	    mapBounds = new kakao.maps.LatLngBounds(sw, ne);
	console.log(swLatLng);
	console.log(neLatLng);
	
	for (var i = 0; i < positions.length; i ++) {
    
    // 마커 이미지의 이미지 크기 입니다
    var imageSize = new kakao.maps.Size(24, 35); 
    
    // 마커 이미지를 생성합니다    
    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize); 
    
    console.log("마커");
    console.log(positions[i].latlng.La);
    console.log(positions[i].latlng.Ma);
    var isTrue = mapBounds.contain(new kakao.maps.LatLng(positions[i].latlng.Ma, positions[i].latlng.La));
    console.log(isTrue);
    
    // 마커를 생성합니다
    if(isTrue){
		var marker = new kakao.maps.Marker({
        map: map, // 마커를 표시할 지도
        position: positions[i].latlng, // 마커를 표시할 위치
        title : positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        image : markerImage // 마커 이미지 
    	});
	}

}
}


function searchAddrFromCoords(coords, callback) {
    // 좌표로 행정동 주소 정보를 요청합니다
    geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);         
}

function searchDetailAddrFromCoords(coords, callback) {
    // 좌표로 법정동 상세 주소 정보를 요청합니다
    geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
}

// 랜덤
function getRandomLatitude(min = 33, max = 34) {
    return Math.random() * (max - min) + min;
}

function getRandomLongitude(min = 126, max = 127) {
    return Math.random() * (max - min) + min;
}

function getRandomPositions(count) {
    const positions = [];
    for (let i = 0; i < count; i++) {
        const latitude = getRandomLatitude();
        const longitude = getRandomLongitude();
        positions.push({
            title: `Random Place ${i + 1}`,
            latlng: new kakao.maps.LatLng(latitude, longitude)
        });
    }
    return positions;
}

