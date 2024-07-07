/**
 * author         : leejongseop
 * ===========================================================
          AUTHOR             NOTE
 * -----------------------------------------------------------
       leejongseop       최초 생성
 */

document.addEventListener("DOMContentLoaded", function() {

    const sendDataButtons = document.querySelectorAll(".like-btn");
    const notificationInsert = document.getElementById("notification-insert");
    const notificationDelete = document.getElementById("notification-delete");
    const nowLikeElement = document.getElementById("now-like");
    const fireworksContainer = document.getElementById('fireworks-container');

	const memberUsernameElement = document.getElementById("memberUsername");
	const memberUsername = memberUsernameElement.value;
	console.log(memberUsername);
	
	sendDataButtons.forEach(function(button) {
		const storeStoreNo = button.getAttribute("data-store-no");
	
	    if (!storeStoreNo) {
	        console.error("storeStoreNo 요소를 찾을 수 없습니다.");
	        return;
	    }
	    
	    if (!memberUsername) {
	        console.error("memberUsername 요소를 찾을 수 없습니다.");
	        return;
	    }
    
		const requestData = {
        	storeStoreNo: storeStoreNo,
        	memberUsername: memberUsername
    	};
    
    	isLike(button, requestData);
    	
        button.addEventListener("click", function(event) {
			const buttonClassName = this.className;
			
            if (buttonClassName.includes("far fa-heart")) {
                insertLike(this, requestData);
            } else if (buttonClassName.includes("fas fa-heart")) {
                deleteLike(this, requestData);
            } else {
                console.error("알 수 없는 클래스입니다.");
            }
            
        });
    });
    
       
    function showNotificationInsert() {
		// yunbin
		if (!notificationInsert) {
			return;
		}
		
        // 알림 문구를 보이도록 설정
        notificationInsert.classList.add("show");

        // 3초 후에 알림 문구를 서서히 사라지게 함
        setTimeout(function() {
            notificationInsert.style.transition = "opacity 1.5s linear"; // 희미해지게 하는 시간
            notificationInsert.style.opacity = 0;
        }, 3000);

        // 4.5초 후에 알림 문구를 완전히 숨김
        setTimeout(function() {
            notificationInsert.classList.remove("show");
            notificationInsert.style.opacity = 1; // opacity를 다시 초기값으로 설정
            notificationInsert.style.transition = "none"; // 초기화 후 transition 제거
        }, 4500);
    }
    
    function showNotificationDelete() {
		// yunbin
		if (!notificationDelete) {
			return;
		}
		
        // 알림 문구를 보이도록 설정
        notificationDelete.classList.add("show");

        // 3초 후에 알림 문구를 서서히 사라지게 함
        setTimeout(function() {
            notificationDelete.style.transition = "opacity 1.5s linear"; // 희미해지게 하는 시간
            notificationDelete.style.opacity = 0;
        }, 3000);

        // 4.5초 후에 알림 문구를 완전히 숨김
        setTimeout(function() {
            notificationDelete.classList.remove("show");
            notificationDelete.style.opacity = 1; // opacity를 다시 초기값으로 설정
            notificationDelete.style.transition = "none"; // 초기화 후 transition 제거
        }, 4500);
    }

    function insertLike(button, requestData) {

        fetch('/store/like/insert', { // URL을 실제 API 엔드포인트로 변경
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
        })
        .then(response => {
            if (!response.ok) {
   				return response.text().then(text => {
					   throw new Error(text);
				   })
            }
            return response;
        })
        .then(data => {
			
            console.log('Success:', data);
            // 추가적인 성공 처리 로직
			button.className = "like-btn fas fa-heart";
			button.style.color = "red";
			eventNowLike('insert');
			
			showNotificationInsert();
			
        })
        .catch(error => {
			alert(error);
            // 추가적인 에러 처리 로직
        });
    }
    
    function deleteLike(button, requestData) {

        fetch('/store/like/delete', { // URL을 실제 API 엔드포인트로 변경
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
        })
        .then(response => {
            if (!response.ok) {
   				return response.text().then(text => {
					   throw new Error(text);
				   })
            }
            return response;
        })
        .then(data => {
            console.log('Success:', data);
            // 추가적인 성공 처리 로직
			button.className = "like-btn far fa-heart";
			button.style.color = "";

			eventNowLike('delete');
			showNotificationDelete();
			
        })
        .catch(error => {
            alert(error);
            // 추가적인 에러 처리 로직
        });
    }
    
    function isLike(button, requestData) {
		console.log('isLike 함수 실행');
		console.log(requestData);
		
		// 쿼리스트링
		const queryString = new URLSearchParams(requestData).toString();
		const url = '/store/like/check?' + queryString;
		
		console.log(queryString);
		console.log(url);
		
        fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Success:', data);
            // 추가적인 성공 처리 로직
            if(data > 0){
				button.className = "like-btn fas fa-heart";
				button.style.color = "red";
			}
			
        })
        .catch(error => {
            console.error('Error:', error);
            // 추가적인 에러 처리 로직
        });
    }
    
    
    function eventNowLike(command){
		let nowLike = 0;
		try{
			let currentCount = Number(nowLikeElement.textContent);
			nowLike = currentCount;
			console.log("현재 like수 : " + nowLike);
		} catch (err){
			console.log("숫자로 변환할 수 없는 문자입니다.");
			return;
		}
		
		if(command === 'insert'){
			console.log("insert명령");
			console.log(nowLike + 1);
			let updateVal = nowLike + 1;
			nowLikeElement.textContent = updateVal.toString(); 
//			insertEvent();
		}else if(command === 'delete'){
			console.log("delete명령");
			console.log(nowLike - 1);
			let updateVal = nowLike - 1;
			nowLikeElement.textContent = updateVal.toString();
		}else{
			console.log("잘못된 명령입니다.");
			return;
		}
		
	}
	
	function insertEvent(){
		const numberOfFireworks = 10;
        for (let i = 0; i < numberOfFireworks; i++) {
            createFirework();
        }

        // Remove fireworks after animation
       setTimeout(() => {
            while (fireworksContainer.firstChild) {
                fireworksContainer.removeChild(fireworksContainer.firstChild);
            }
        }, 1000); // Duration of the fireworks effect
	}
	
	function createFirework() {
        const firework = document.createElement('div');
        firework.className = 'firework';
        firework.style.backgroundColor = getRandomColor();
        firework.style.top = (Math.random() * 100 - 50) + '%';
        firework.style.left = (Math.random() * 100 - 50) + '%';
        nowLikeElement.appendChild(firework);
    }

    function getRandomColor() {
        const colors = ['#FF1461', '#18FF92', '#5A87FF', '#FBF38C'];
        return colors[Math.floor(Math.random() * colors.length)];
    }
    
});