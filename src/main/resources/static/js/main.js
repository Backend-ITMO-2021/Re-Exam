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
    getMyUsername();
});

function getMyUsername() {
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
        $("#rooms").show();
        $("#room").hide();
        $("#room-user").hide();
        $("#room-stats").hide();

        document.getElementById("rooms-list").innerHTML = "";
        for (const availableRoomString of roomsListData.availableRooms) {
            const availableRoom = JSON.parse(availableRoomString);
            let roomElement = document.createElement('li');
            roomElement.id = `rooms-list-${availableRoom.id}`;
            document.getElementById("rooms-list").appendChild(roomElement)
            let roomLinkElement = document.createElement('a');
            roomLinkElement.id = `rooms-list-${availableRoom.id}-link`;
            roomLinkElement.href = "#";
            roomLinkElement.onclick = function () {
                getRoom(availableRoom.id);
            };
            roomElement.appendChild(roomLinkElement);
            roomLinkElement.innerHTML = availableRoom.name;
            if (availableRoom.isPrivate) {
                roomLinkElement.innerHTML += "(P)";
            }
        }
    });
}

function getRoom(room_id) {
    $.get(`/${room_id}`).then(roomData => {
        if (roomData.error !== undefined) {
            console.log(`getRoom(${room_id}) error`);
            console.log(roomData);
            alert(roomData.error);
        } else {
            $("#room").show();
            $("#rooms").hide();
            $("#room-stats").hide();
            $("#room-all-messages").show();
            $("#room-all-messages-from-text").hide();
            $("#room-all-messages-to-text").hide();

            if (roomData.isPrivate) {
                $("#room-name").html(roomData.name + "(P)");
            } else {
                $("#room-name").html(roomData.name);
            }
            $("#room-id").val(roomData.id);

            if (JSON.parse(roomData.admin).username === $('#username').html()) {
                $("#room-name-change").show();
                $("#room-private-change").show();
                $("#room-invite").show();
            } else {
                $("#room-name-change").hide();
                $("#room-private-change").hide();
                $("#room-invite").hide();
            }

            $("#room-admin-name").html(JSON.parse(roomData.admin).username);

            showRoomMessages(room_id, roomData);
        }
    });
}

function getRoomAllMessages() {
    const room_id = $("#room-id").val();
    let requestBody = {};

    const roomAllMessagesFrom = $("#room-all-messages-from").val();
    if (roomAllMessagesFrom !== "") {
        requestBody.from = roomAllMessagesFrom;
    }
    const roomAllMessagesTo = $("#room-all-messages-to").val();
    if (roomAllMessagesTo !== "") {
        requestBody.to = roomAllMessagesTo;
    }

    $.post(`/${room_id}/all-messages`, JSON.stringify(requestBody)
    ).then(roomAllMessagesData => {
        if (roomAllMessagesData.error !== undefined) {
            console.log(`getRoomAllMessages() error`);
            console.log(roomAllMessagesData);
            alert(roomAllMessagesData.error);
        } else {
            $("#room").show();
            $("#rooms").hide();
            $("#room-stats").hide();
            $("#room-all-messages").hide();

            $("#room-all-messages-from-text-value").html();
            $("#room-all-messages-from-text").show();

            $("#room-all-messages-to-text-value").html();
            $("#room-all-messages-to-text").show();

            $("#room-name-change").hide();
            $("#room-private-change").hide();
            $("#room-invite").hide();

            showRoomMessages(room_id, roomAllMessagesData);
        }
    });
}

function getRoomStatsTop() {
    const room_id = $("#room-id").val();
    $.get(`/${room_id}/stats/top`).then(roomStatsTopData => {
        if (roomStatsTopData.error !== undefined) {
            console.log(`getRoomStatsTop() error`);
            console.log(roomStatsTopData);
            alert(roomStatsTopData.error);
        } else {
            $("#room-stats").show();
            $("#room").hide();
            $("#rooms").hide();
            $("#room-all-messages").hide();
            $("#room-all-messages-from-text").show();
            $("#room-all-messages-to-text").show();

            $("#room-name-change").hide();
            $("#room-private-change").hide();
            $("#room-invite").hide();

            document.getElementById("room-stats-top").innerHTML = "";
            for (const rowTopString of roomStatsTopData.top) {
                const rowTop = JSON.parse(rowTopString);
                console.log(rowTop);
                let rowTopElement = document.createElement('li');
                document.getElementById("room-stats-top").appendChild(rowTopElement);
                rowTopElement.appendChild(document.createTextNode(`${rowTop.username}: ${rowTop["count"]}`));
            }
        }
    });
}

function getUserMessages(room_id, username) {
    $.get(`/${room_id}/messages/${username}`).then(messagesUsernameData => {
        if (messagesUsernameData.error !== undefined) {
            console.log("getUserMessages() error");
            console.log(messagesUsernameData);
            alert(messagesUsernameData.error);
        } else {
            $("#room").show();
            $("#room-all-messages").hide();
            $("#rooms").hide();
            $("#room-stats").hide();
            $("#room-all-messages-from-text").hide();
            $("#room-all-messages-to-text").hide();

            $("#room-name-change").hide();
            $("#room-private-change").hide();
            $("#room-invite").hide();

            showRoomMessages(room_id, messagesUsernameData);
        }
    })
}

function changeMyUsername() {
    const newUsername = $("#username-change-new-username").val()
    if (newUsername !== "") {
        $.post("/me/username/change",
            JSON.stringify({
                "username": newUsername
            })
        ).then(data => {
            if (data.error !== undefined) {
                console.log("changeMyUsername() error");
                console.log(data);
                alert(data.error);
            } else {
                getMyUsername();
            }
        })
    } else {
        alert("Type in your new username");
    }
}

function changeRoomName() {
    const newRoomName = $("#room-name-change-new-room-name").val()
    const room_id = $("#room-id").val();
    if (newRoomName !== "") {
        $.post(`/${room_id}/change_name`,
            JSON.stringify({
                "name": newRoomName
            })
        ).then(data => {
            if (data.error !== undefined) {
                console.log("changeRoomName() error");
                console.log(data);
                alert(data.error);
            } else {
                getRoom(room_id);
            }
        })
    } else {
        alert("Type in new room name");
    }
}

function changeRoomPrivate() {
    const room_id = $("#room-id").val();
    $.post(`/${room_id}/make_private`,
        JSON.stringify({
            "username": $("#room-invite-username").val()
        })
    ).then(data => {
        if (data.error !== undefined) {
            console.log("changeRoomPrivate() error");
            console.log(data);
            alert(data.error);
        } else {
            getRoom(room_id);
        }
    })
}

function inviteUser() {
    const invitedUsername = $("#room-invite-username").val()
    const room_id = $("#room-id").val();
    if (invitedUsername !== "") {
        $.post(`/${room_id}/invite`,
            JSON.stringify({
                "username": invitedUsername
            })
        ).then(data => {
            if (data.error !== undefined) {
                console.log("inviteUser() error");
                console.log(data);
                alert(data.error);
            } else {
                getRoom(room_id);
            }
        })
    } else {
        alert("Type in your new username");
    }
}

function createMessage() {
    let roomId = $("#room-id").val();
    $.post(`/${roomId}/messages/create`,
        JSON.stringify({
            "text": $("#room-messages-create-text").val()
        })
    ).then(data => {
        if (data.error !== undefined) {
            console.log("createMessage() error");
            console.log(data);
            alert(data.error);
        } else {
            getRoom(roomId);
        }
    });
}

function createRoom() {
    $.post("/rooms/create",
        JSON.stringify({
            "name": $("#rooms-create-name").val(),
            "isPrivate": $("#rooms-create-private").is(":checked")
        })
    ).then(data => {
        if (data.error !== undefined) {
            console.log("createRoom() error");
            console.log(data);
            alert(data.error);
        } else {
            getRoom(data.room_id);
        }
    });
}

function logout() {
    $.post("/logout").then(() => {
        $("#user").html('');
        $(".unauthenticated").show();
        $(".authenticated").hide();
    })
    return true;
}

function showRoomMessages(room_id, messagesData) {
    document.getElementById("room-messages").innerHTML = "";
    for (const messageString of messagesData.messages) {
        const message = JSON.parse(messageString);
        const messageUsername = JSON.parse(message.user).username;

        let messageElement = document.createElement('li');
        messageElement.id = `room-messages-${message.id}`
        document.getElementById("room-messages").appendChild(messageElement);

        let messageUsernameLinkElement = document.createElement('a');
        messageUsernameLinkElement.id = `room-messages-${message.id}-username-link`;
        messageUsernameLinkElement.href = "#";
        messageUsernameLinkElement.onclick = function () {
            getUserMessages(room_id, messageUsername);
        };

        messageElement.appendChild(document.createTextNode(`(${message.time}) `));
        messageElement.appendChild(messageUsernameLinkElement);
        messageElement.appendChild(document.createTextNode(`: ${message.text}`));
        $(`#room-messages-${message.id}-username-link`).html(messageUsername);
    }
}