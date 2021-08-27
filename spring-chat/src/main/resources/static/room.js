'use strict';


// let messageForm = document.querySelector('#messageForm');
let messageInput = document.querySelector('#messageInp');
let messageArea = document.querySelector('#messageArea');
let connectingElement = document.querySelector('.connecting');
let loadHistoryBtn = document.querySelector("#load-history-btn");
let stompClient = null;
let username = null;
let colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

//useful constants
const roomId = window.location.href.split("/").pop()
console.log("RoomID: ", roomId)


function connect() {
    username = document.querySelector('#name').value.trim();
    console.log("User name is: ", username)
    if(username) {
        let socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
    }
}
function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe(`/topic/${roomId}`, onMessageReceived);
    // Tell your username to the server
    stompClient.send(`/app/chat/${roomId}/addUser`,
        {},
        JSON.stringify({
            sender: username,
            type: 'JOIN'})
    )
    connectingElement.innerHTML = "Connected successfully!"
    connectingElement.style.color = 'green'

    loadHistoryBtn.removeAttribute("disabled");
}
function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}
function sendMessage() {
    let messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        let chatMessage = {
            sender: username,
            content: messageInput.value,
            tod: new Date(Date.now()).toISOString(),
            type: 'CHAT'
        };
        stompClient.send(`/app/chat/${roomId}`, {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
}
function onMessageReceived(payload) {
    let message = JSON.parse(payload.body);
    let messageElement = document.createElement('li');

    let textElement = document.createElement('p');
    console.log("Message:", message)



    if(message.type === 'JOIN') {

        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';

        textElement.appendChild(document.createTextNode(message.content));
        messageElement.appendChild(textElement);

    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message'); //TODO user left implement
        message.content = message.sender + ' left!';

        textElement.appendChild(document.createTextNode(message.content));
        messageElement.appendChild(textElement);

    } else {
        messageElement.classList.add('chat-message');
        messageElement.style.display = "flex"
        messageElement.style.justifyContent = "space-between"
        let leftMsg = document.createElement("div")
        let rightMsg = document.createElement("div")
        rightMsg.appendChild(document.createTextNode(message.tod))

        let avatarElement = document.createElement('i');
        let avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);
        messageElement.appendChild(avatarElement);
        let usernameElement = document.createElement('span');
        let usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        leftMsg.appendChild(usernameElement)
        leftMsg.appendChild(textElement);
        messageElement.appendChild(leftMsg);
        messageElement.appendChild(rightMsg);

        textElement.appendChild(document.createTextNode(message.content));
    }


    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}
function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

// messageForm.addEventListener('submit', sendMessage, true)
//Start
setTimeout(connect, 1000);

function loadHistory() {
 stompClient.send(`/app/chat/${roomId}/loadHistory`, {})
}