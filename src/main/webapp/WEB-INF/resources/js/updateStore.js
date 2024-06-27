import {urls} from './config.js'
import {addMenuInputForm, menuForm, storeForm} from './storeForm.js'

$(document).ready(function() {
    addMenuInputForm();
    sortableGallery();
    handlePictureChange();
    

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
}

//function updateStorePhoto() {
//	const input = $('#picture')[0];
//   	const storeNo = getStoreNOFromUrl();
//   	const photoNo = $('#storePicture').data('id');
//    
//    if (input.files && input.files[0]) {
//        
//        console.log(photoNo)
//        $.ajax({
//            type: 'DELETE',
//            url: urls.ImageUrl + storeNo + "/" + photoNo,
//            success: function(response) {
//                uploadNewPhoto(input.files[0]);
//            },
//            error: function(xhr, status, error) {
//                console.error('기존 사진 삭제 실패', error);
//                alert("오류가 발생했습니다. 잠시 후 다시 시도해주세요.")
//            }
//        });
//        
//        const reader = new FileReader();
//            reader.onload = function(e) {
//                $('#storePicture').attr('src', e.target.result);
//            };
//            reader.readAsDataURL(file);
//    }
//}
//
//function uploadNewPhoto(file) {
//    const formData = new FormData();
//    formData.append('file', file);
//
//    // 신규 사진 업로드 AJAX 요청
//    $.ajax({
//        type: 'POST',
//        url: urls.ImageUrl + storeNo + "/upload-photo",
//        data: formData,
//        processData: false,
//        contentType: false,
//        success: function(response) {
//            console.log('새로운 사진 업로드 성공');
//            // 업로드 성공 후 이미지 미리보기 업데이트
//            
//        },
//        error: function(xhr, status, error) {
//            console.error('새로운 사진 업로드 실패', error);
//            // 실패 처리 로직 추가
//        }
//    });
//}

function deleteGallery() {
	const liItem = $(this).closest('li');
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