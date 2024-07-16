/**
 * 
 */
document.addEventListener('DOMContentLoaded', (event) => {
    const popup = document.getElementById("chatbot-popup");
    const btn = document.getElementById("chatbot-button");
    const span = document.getElementsByClassName("chatbot-close-button")[0];
	const input = document.getElementById('chatbot-message-input');
    btn.onclick = function() {
        popup.style.display = "block";
    }

    span.onclick = function() {
        popup.style.display = "none";
    }

    window.onclick = function(event) {
        if (event.target == popup) {
            popup.style.display = "none";
        }
    }
    
    input.addEventListener('keypress', function (event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            console.log("엔터키");
            sendToChatbot();
        }
    });
    
    document.getElementById('chatbot-send-button').addEventListener('click', sendToChatbot);
    
    function sendToChatbot(){
		const messageInput = document.getElementById('chatbot-message-input');
        const chatMessages = document.getElementById('chatbot-messages');

        if (messageInput.value.trim() !== '') {
            const newMessage = document.createElement('div');
            newMessage.classList.add('user-sent-message');
            const messageContent = document.createElement('div');
            let message = messageInput.value;
            messageContent.textContent = message;
            newMessage.appendChild(messageContent);
            chatMessages.appendChild(newMessage);
            messageInput.value = '';
            chatMessages.scrollTop = chatMessages.scrollHeight; // 새로운 메시지로 스크롤
            
            console.log('입력 메시지 : ' + message);
            // 챗봇 api에 전송
            fetch("http://localhost:8000/api/main_chat",{
				method: "POST",
				headers:{
		            'Content-Type' : 'application/json'
		        },
				body: JSON.stringify({
					message: message
				})
			})
			.then((response) => response.json())
			.then((result) => {
				console.log(result);
				const newMessage = document.createElement('div');
                newMessage.classList.add('chatbot-response-message');
                const messageContent = document.createElement('div');
                messageContent.textContent = result.response;
                newMessage.appendChild(messageContent);
                chatMessages.appendChild(newMessage);
                chatMessages.scrollTop = chatMessages.scrollHeight; // 새로운 메시지로 스크롤
			})
			.catch((error) => console.log(error));
            
        }
	}
});

