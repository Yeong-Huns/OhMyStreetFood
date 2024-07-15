/**
 * 
 */
document.addEventListener('DOMContentLoaded', (event) => {
    const popup = document.getElementById("chatbot-popup");
    const btn = document.getElementById("chatbot-button");
    const span = document.getElementsByClassName("chatbot-close-button")[0];

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
});

