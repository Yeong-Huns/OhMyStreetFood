/**
 * 
 */

document.querySelector('.close-button').onclick = function() {
    const storeNo = document.querySelector('#storeStoreNo').value;
    location.href = '/review/list/' + storeNo;
};

document.addEventListener('DOMContentLoaded', function() {
    const ratingInputs = document.querySelectorAll('.rating input');
    const labels = document.querySelectorAll('.rating label');
	
    const checkedRadio = document.querySelector('.rating input:checked');
    if (checkedRadio) {
        const index = parseInt(checkedRadio.value) - 1; // value는 1부터 시작
        colorStars(index);
   	}
    
    // 평점 색상 변경 함수
    function colorStars(index) {
        labels.forEach((innerLabel, innerIndex) => {
            if (innerIndex <= index) {
                innerLabel.style.color = '#f5b301';
            } else {
                innerLabel.style.color = '#ccc';
            }
        });
    }


//    labels.forEach((label, index) => {
//        label.addEventListener('mouseover', () => {
//            labels.forEach((innerLabel, innerIndex) => {
//                if (innerIndex <= index) {
//                    innerLabel.style.color = '#f5b301';
//                } else {
//                    innerLabel.style.color = '#ccc';
//                }
//            });
//        });
//
//        label.addEventListener('mouseout', () => {
//            ratingInputs.forEach((input, inputIndex) => {
//                if (input.checked) {
//                    labels.forEach((innerLabel, innerIndex) => {
//                        if (innerIndex <= inputIndex) {
//                            innerLabel.style.color = '#f5b301';
//                        } else {
//                            innerLabel.style.color = '#ccc';
//                        }
//                    });
//                }
//            });
//
//            // 선택된 별이 없을 경우 모든 별의 색상을 기본값으로 변경
//            if (!document.querySelector('.rating input:checked')) {
//                labels.forEach(innerLabel => {
//                    innerLabel.style.color = '#ccc';
//                });
//            }
//        });
//    });

    ratingInputs.forEach((input, index) => {
        input.addEventListener('change', () => {
            labels.forEach((label, labelIndex) => {
                if (labelIndex <= index) {
                    label.style.color = '#f5b301';
                } else {
                    label.style.color = '#ccc';
                }
            });
        });
    });
});