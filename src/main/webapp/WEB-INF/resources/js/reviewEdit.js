/**
 * author         : leejongseop
 * ===========================================================
          AUTHOR             NOTE
 * -----------------------------------------------------------
       leejongseop       최초 생성
 */
function enableEditing() {
	var stars = document.querySelectorAll('.rating input[name="rating"]');
	stars.forEach(function(star) {
		star.disabled = false;
	});
	document.querySelector('#reviewForm textarea[name="content"]').disabled = false;
	document.querySelector('#update-btn').style.display = 'none';
	document.querySelector('#update-submit').style.display = 'block';
}

function updateCommand() {
	document.querySelector('#reviewForm input[name="command"]').value = 'update';
	submit();
}

function deleteCommand() {
	if(confirm('정말 삭제하시겠습니까?')){
		document.querySelector('#reviewForm input[name="command"]').value = 'delete';
		submit();		
	}else{
		preventDefault();
	}
}