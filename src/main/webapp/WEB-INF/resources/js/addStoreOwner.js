import urls from './config.js';

// 메뉴 추가
$(document).ready(function() {
    $('#addMenuBtn').click(function() {
        var menuName = $('#menuName').val();
        var menuPrice = $('#menuPrice').val();
        
        // 입력값 유효성 검사
        if (menuName === '' || menuPrice === '') {
            alert('메뉴 이름과 가격을 모두 입력해주세요.');
            return;
        }
         
         var newRow = '<tr>' +
         '<td><input type="text" name="menuName" value="' + menuName + '" style="border: none;" readonly></td>' +
         '<td><input type="number" name="menuPrice" value="' + menuPrice + '" style="border: none;" readonly></td>' +
         '<td><button type="button" class="btn btn-danger btn-sm deleteBtn" style="border-radius: 500px;">-</button></td>' +
         '</tr>';
        
        $('#menuList').append(newRow);

        // 입력 필드 초기화
        $('#menuName').val('');
        $('#menuPrice').val('');
    });

    // 삭제 버튼 클릭 이벤트 처리
    $('#menuList').on('click', '.deleteBtn', function() {
        $(this).closest('tr').remove();
    });
    
    
	document.getElementById('storeForm').addEventListener('submit', function(event) {
	    event.preventDefault();
	    createStore();
	});
});


function createStore() {	
	const formData = new FormData();
	StoreForm(formData);
	pictureForm(formData);
	menuForm(formData);
	console.log(formData)
	
	fetch(urls.createStoreUrl, {
	    method: 'POST',
	    body: formData
	})
	.then(response => {
        return response.text();
    })
    .then(data => {
        console.log('Success:', data);
        alert('상점 생성이 완료되었습니다.');
        window.location.href = urls.mainUrl;
    })
    .catch(error => {
        console.error('Error:', error);
        alert('상점 생성 중 오류가 발생했습니다.');
    });
}


function StoreForm(formData) {
  const store = {
            storeName: document.getElementById('storeName').value,
            address: document.getElementById('address').value,
            latitude: parseFloat(document.getElementById('latitude').value),
            longitude: parseFloat(document.getElementById('longitude').value),
            introduce: document.getElementById('introduce').value,
            operatingHours: document.getElementById('startTime').value + ' - ' + document.getElementById('endTime').value,
            operatingDate: Array.from(document.querySelectorAll('input[name="days"]:checked')).map(e => e.value).join(', '),
        };
        
	formData.append('store', JSON.stringify(store));
} 

function pictureForm(formData) {
	 const pictureInput = document.getElementById('picture');
    if (pictureInput.files.length > 0) {
        formData.append('photo', pictureInput.files[0]);
    }
}

function menuForm(formData) {
	 const menus = [];
            $('#menuList tr').each(function() {
                const menuName = $(this).find('input[name="menuName"]').val();
                const menuPrice = $(this).find('input[name="menuPrice"]').val();
                menus.push({menuName: menuName, price: menuPrice});
            });
            
	 formData.append('menus', JSON.stringify(menus));
}