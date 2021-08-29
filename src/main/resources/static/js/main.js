$(document).ready(function () {
    $.ajaxSetup({
        headers: {"Content-Type":  "application/json"},
        beforeSend: function (xhr, settings) {
            if (settings.type == 'POST' || settings.type == 'PUT'
                || settings.type == 'DELETE') {
                if (!(/^http:.*/.test(settings.url) || /^https:.*/
                    .test(settings.url))) {
                    // Only send the token to relative URLs i.e. locally.
                    xhr.setRequestHeader("X-XSRF-TOKEN",
                        Cookies.get('XSRF-TOKEN'));
                }
            }
        }
    });
    getUserName();
});

function getUserName() {
    $.get("/me/username")
        .then(data => {
            $('#username').html(data.username);
            $('.unauthenticated').hide();
            $('.authenticated').show();
            getRooms();
        });
}

function getRooms() {
    $.get("/rooms/").then(roomsListData => {
        for (const room of roomsListData) {
            let roomElement = document.createElement('li');
            roomElement.id = `rooms-list-${room.id}`;
            document.getElementById("rooms-list").appendChild(roomElement)
            if (room.isPrivate) {
                $(`#rooms-list-${room.id}`).html(room.name);
            } else {
                let roomLinkElement = document.createElement('a');
                roomLinkElement.id = `rooms-list-${room.id}-link`;
                roomLinkElement.href = "#";
                roomLinkElement.onclick = function () {
                    getRoom(room.id);
                }
                document.getElementById(`rooms-list-${room.id}`).appendChild(roomLinkElement);
                $(`#rooms-list-${room.id}-link`).html(room.name);
            }
        }
    });
}

function getRoom(id) {
    $.get(`/${id}`).then(roomData => {
        console.log(roomData);
        $("#rooms").hide();
        $("#room").show();
        $("#room-name").html(roomData.name);
        $("#room-id").val(roomData.id);
        $("#room-admin").html(`Admin is ${JSON.parse(roomData.admin).username}`);
        document.getElementById("room-messages").innerHTML = "";
        for (const messageString of roomData.messages) {
            let message = JSON.parse(messageString);
            let messageElement = document.createElement('li');
            messageElement.innerText = `(${message.time}) ${JSON.parse(message.user).username}: ${message.text}`;
            document.getElementById("room-messages").appendChild(messageElement);
        }
    });
}

function createMessage() {
    let roomId = $("#room-id").val();
    $.post(`/${roomId}/messages/create`,
        JSON.stringify({
            "text": $("#room-messages-create-text").val()
        })
    ).then(data => getRoom(roomId));
}

function createRoom() {
    $.post("/rooms/create",
        JSON.stringify({
            "name": $("#rooms-create-name").val(),
            "isPrivate": $("#rooms-create-private").val()
        })
    ).then(data => getRoom(data.room_id));
}

function logout() {
    $.post("/logout").then(() => {
        $("#user").html('');
        $(".unauthenticated").show();
        $(".authenticated").hide();
    })
    return true;
}