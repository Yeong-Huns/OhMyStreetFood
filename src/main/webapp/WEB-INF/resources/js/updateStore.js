import {urls} from './config.js'
import {addMenuInputForm, menuForm, storeForm, galleryForm} from './storeForm.js'

$(document).ready(function() {
    addMenuInputForm();
    sortableGallery();
    handlePictureChange();
   	$('.updateStoreBtn').click(function(e) {
        e.preventDefault();
        updateStore();
    });

});

function sortableGallery() {

    
    $('#sortable').sortable({
        axis: 'y', 

        update: function(event, ui) {
            // 순서가 변경되면 이벤트 발생
            console.log('순서가 변경되었습니다.');
        }
    });
    $('#sortable').disableSelection();  
}

function handlePictureChange() {
	$('#updateStorePhoto').click(function(e) {
		e.preventDefault();
        $('#picture').click();
    });

    $('#picture').change(function() {
        updateStorePhoto();
    });
    
   	$('#galleryList').on('click', '.deleteBtn', async function() {
   		deleteGallery();
  	});
  	
  	$('#addPhotoBtn').click(function() {
		$('#pictureInput').click();
	});
	
	$('#pictureInput').change(function() {
		addGallery()
	});
}

function updateStore() {	
	const formData = new FormData();
	const storeNo = getStoreNOFromUrl();
	storeForm(formData);
	galleryForm(formData);
	menuForm(formData);
	
	fetch(urls.storeUrl + storeNo, {
	    method: 'POST',
	    body: formData
	})
	.then(response => {
        return response.text();
    })
    .then(data => {
        console.log('Success:', data);
        alert('상점 수정이 완료되었습니다.');
        window.location.href = urls.mainUrl + "store/" + storeNo;
    })
    .catch(error => {
        console.error('Error:', error);
        alert('상점 수정 중 오류가 발생했습니다.');
    });
}


function updateStorePhoto() {
	const input = $('#picture')[0];
   	const storeNo = getStoreNOFromUrl();
   	const photoNo = $('#storePicture').data('id');
    
    if (input.files && input.files[0]) {
        const formData = new FormData();
   		formData.append('photo', input.files[0]);
        console.log(photoNo)
        $.ajax({
            type: 'PUT',
            url: urls.ImageUrl + storeNo + "/" + photoNo,
            data: formData,
	        processData: false,
	        contentType: false,
            success: function(response) {
   				console.error('사진을 변경했습니다.', response);
	   			const reader = new FileReader();
	            reader.onload = function(e) {
	                $('#storePicture').attr('src', e.target.result);
	            };
	            
	            $('#storePicture').data('id', response);
            reader.readAsDataURL(input.files[0]);
            },
            error: function(xhr, status, error) {
                console.error('기존 사진 삭제 실패', error);
                alert("오류가 발생했습니다. 잠시 후 다시 시도해주세요.")
            }
        });
        
       
    }
}


function deleteGallery() {
	const liItem = $(event.target).closest('li');
    const photoNo = liItem.data('id'); 
    const storeNo = getStoreNOFromUrl();

	 $.ajax({
        type: 'DELETE', 
        url: urls.ImageUrl + storeNo + "/" + photoNo, 
        data: JSON.stringify({ photoNo: photoNo }),
        contentType: 'application/json; charset=utf-8',
        success: function(response) {
            
             liItem.remove();
            console.log('사진이 성공적으로 삭제되었습니다.');
        },
        error: function(xhr, status, error) {
            console.error('사진 삭제 중 오류가 발생했습니다.', error);
            alert("삭제중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.")
        	}
    	});
}

function getStoreNOFromUrl() {
    const url = window.location.pathname;
    const parts = url.split('/');
    return parts[parts.indexOf('store') + 1];
}


function addGallery() {
    // 갤러리 사진 추가 버튼 클릭 시
 
        const input = $('#pictureInput')[0];
        const storeNo = getStoreNOFromUrl(); // URL에서 상점 번호 가져오는 함수

        if (input.files && input.files[0]) {
            const formData = new FormData();
            formData.append('photo', input.files[0]);

            $.ajax({
                type: 'POST',
                url: urls.ImageUrl + storeNo + "/upload-photo", // 사진을 추가하는 엔드포인트 URL
                data: formData,
                processData: false,
                contentType: false,
                success: function(newPhoto) {
                    console.log('새로운 사진 추가 성공. 새로운 사진 ID:', newPhoto.photoNo);

                    // 새로운 갤러리 항목 생성
                    const newGalleryItem = `
                        <li class="list-group-item" data-id="${newPhoto.photoNo}">
                            <img src="${newPhoto.picture}" class="img-thumbnail" style="max-width: 100px;">
                            <button type="button" class="btn btn-danger btn-sm float-end deleteBtn">삭제</button>
                        </li>
                    `;

                    // 갤러리 리스트에 새로운 항목 추가
                    $('#sortable').append(newGalleryItem);

                    // 입력 필드 초기화
                    $('#pictureInput').val('');
                },
                error: function(xhr, status, error) {
                    console.error('갤러리 사진 추가 실패', error);
                    alert("오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
                }
            });
        } else {
            alert('사진을 선택해주세요.');
        }
}
