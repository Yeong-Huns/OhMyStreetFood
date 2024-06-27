import {urls} from './config.js';
import {addMenuInputForm, menuForm, storeForm} from './storeForm.js'

// 메뉴 추가
$(document).ready(function() {
	addMenuInputForm();
	document.getElementById('storeForm').addEventListener('submit', function(event) {
	    event.preventDefault();
	    createStore();
	});
});


function createStore() {	
	const formData = new FormData();
	storeForm(formData);
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


function pictureForm(formData) {
	 const pictureInput = document.getElementById('picture');
    if (pictureInput.files.length > 0) {
        formData.append('photo', pictureInput.files[0]);
    }
}

