document.getElementById('inputEndTime')
	.addEventListener(
		'change',
		function() {
			var startTime = document
				.getElementById('inputStartTime').value;
			var endTime = this.value;

			// 시간을 Date 객체로 변환하여 비교
			var startDate = new Date('2000-01-01T' + startTime
				+ ':00');
			var endDate = new Date('2000-01-01T' + endTime
				+ ':00');

			if (endDate <= startDate) {
				alert('종료 시간은 시작 시간보다 커야 합니다.');
				// 종료 시간을 시작 시간으로 설정
				this.value = startTime;
			}
		});

function addMenu() {
	var menuInput = document.getElementById('menuInput').value;
	var priceInput = document.getElementById('priceInput').value;

	var menuContainer = document.getElementById('menuContainer');

	var card = document.createElement('span');
	card.classList.add('card');
	card.style.height = '50px';
	card.style.margin = "20px 0 0 0";
	//             card.style.border = 'none';

	var cardBody = document.createElement('span');
	cardBody.classList.add('card-body', 'd-flex', 'justify-content-between', 'align-items-center');
	cardBody.style.padding = '5px';
	//             cardBody.style.border = 'none';

	var menuName = document.createElement('span');
	menuName.textContent = menuInput;
	menuName.style.whiteSpace = 'nowrap';
	menuName.style.overflow = 'hidden';
	menuName.style.maxWidth = '40%';

	var menuPrice = document.createElement('span');
	menuPrice.textContent = priceInput;
	menuPrice.style.whiteSpace = 'nowrap';
	menuPrice.style.overflow = 'hidden';
	menuPrice.style.maxWidth = '40%';

	var deleteButton = document.createElement('button');
	deleteButton.textContent = '―';
	deleteButton.classList.add('btn', 'btn-danger', 'btn-sm', 'rounded-circle');
	deleteButton.onclick = function() {
		menuContainer.removeChild(card);
	};

	cardBody.appendChild(menuName);
	cardBody.appendChild(menuPrice);
	cardBody.appendChild(deleteButton);
	card.appendChild(cardBody);
	menuContainer.appendChild(card);

	document.getElementById('menuInput').value = "";
	document.getElementById('priceInput').value = "";
}
