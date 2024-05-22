var stompClient = null;

// Estabelecer a conexão websocket quando a página é carregada
document.addEventListener("DOMContentLoaded", function(event) {
    connect();
});

function connect() {
    var socket = new SockJS('/chat-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function (message) {
            showMessage(JSON.parse(message.body));
        });
    });
}

function sendMessage() {
    var messageInput = document.getElementById('messageInput');
    var username = document.getElementById('username').value;
    var message = {
        sender: username,
        content: messageInput.value,
        timestamp: null // Will be set by the server
    };
    stompClient.send("/app/send", {}, JSON.stringify(message));
    messageInput.value = '';
}

function showMessage(message) {
    var messagesDiv = document.getElementById('messages');
    var messageElement = document.createElement('div');
    messageElement.appendChild(document.createTextNode(message.sender + ": " + message.content));
    messagesDiv.appendChild(messageElement);
}

function showRegistrationForm() {
    document.getElementById('loginForm').style.display = 'none';
    document.getElementById('registrationForm').style.display = 'block';
}

function showLoginForm() {
    document.getElementById('registrationForm').style.display = 'none';
    document.getElementById('loginForm').style.display = 'block';
}

function login() {
    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;
    // Objeto de dados para enviar para o backend
    var data = {
        username: username,
        password: password
    };
    // Enviar uma solicitação POST para o endpoint de login no backend
    fetch('/api/users/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (response.ok) {
            document.getElementById('loginForm').style.display = 'none';
            document.getElementById('messages').style.display = 'block';
            document.getElementById('typeMessage').style.display = 'flex';
        } else {
            alert('Login failed. Please check your username and password.');
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

function register() {
    var newUsername = document.getElementById('newUsername').value.trim();
    var newPassword = document.getElementById('newPassword').value.trim();
    // Verificar se os campos estão vazios
    if (newUsername === "" || newPassword === "") {
        alert('Username and password cannot be blank.');
        return;
    }
    var data = {
        username: newUsername,
        password: newPassword
    };
    fetch('/api/users/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (response.ok) {
            document.getElementById('registrationForm').style.display = 'none';
            document.getElementById('loginForm').style.display = 'block';
        } else {
            alert('Registration failed. Please try again.');
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}
