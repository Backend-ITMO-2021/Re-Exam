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
    let username = "";
    getUserName();
    console.log(username);
    let currentRoomId = 0;
});


function getUserName() {
    // TODO: figure out how to save data (room_id)
    $.get("/me/username").then(data => {
        username = data.username;
        $('#name').html(username);
        $('.unauthenticated').hide();
        $('.authenticated').show();
    });
}

function getRooms() {
    console.log("Response /rooms/");
    console.log($.get("/rooms/"));
}

function getRoom(id) {
    $.get(`/rooms/${id}`);
}

function createRoom() {
    $.post("/rooms/create",
        JSON.stringify({"name": "NewRoomName", "isPrivate": false})
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