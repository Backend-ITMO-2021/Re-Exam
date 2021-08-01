$(document).ready(function () {
    draw(false)
});

let timerId = setInterval(() => updateInfo(), 2000);

function draw(authorized, users, game) {
    if(authorized) {
        $('#feedback').prop("hidden", true)
        $('#logout').prop("hidden", false)
        $('#registration_login').prop("hidden", true)
        $('#field').prop("hidden", true)
        $('#users').prop("hidden", true)

        if(game !== undefined) {

            if(game["winner"] !== undefined) {
                $('#winner').html("<pre>Winner: "+game["winner"]+"</pre>")
            } else {
                $('#winner').html("")
            }

            $('#field').prop("hidden", false)
            var field = game["field"]
            for (var i = 0; i < field.length; i++) {
                $("#bt"+i).text(field[i]);
                if(!game["play"] || field[i] !== '*') {
                    $("#bt"+i).prop("disabled", true);
                } else if(field[i] === '*') {
                    $("#bt"+i).prop("disabled", false);
                }
            }
        } else {
            var usersView = ""
            for (var i = 0; i < users.length; i++) {
                var user = users[i]
                usersView += "<div><pre>" + user["name"] + "</pre><button onclick='startGame(" + user["id"] + ")'>Play</button></div>"
            }
            $('#users').html(usersView).prop("hidden", false)
        }

    } else {
        $('#field').prop("hidden", true)
        $('#feedback').prop("hidden", false)
        $('#logout').prop("hidden", true)
        $('#registration_login').prop("hidden", false)
        $('#users').prop("hidden", true)
    }
}

function logout() {
    sessionStorage.clear()
}

function updateInfo() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/status",
        dataType: 'json',
        beforeSend: function(xhr){
            if(sessionStorage.getItem('session') !== null) {
                xhr.setRequestHeader('authorization', sessionStorage.getItem('session'));
            }},
        cache: false,
        timeout: 600000,
        success: function (data) {

            var authorized = data['authorized']
            var users = data['users']
            var game = data['game']
            draw(authorized, users, game)
            console.log("info : ", data);

        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function press(bt_ind) {

    var turn_json = {}
    turn_json["turn"] = bt_ind;

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/turn",
        data: JSON.stringify(turn_json),
        dataType: 'json',
        beforeSend: function(xhr){
            if(sessionStorage.getItem('session') !== null) {
                xhr.setRequestHeader('authorization', sessionStorage.getItem('session'));
            }},
        cache: false,
        timeout: 600000,
        success: function (data){
            console.log("SUCCESS : ", data);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });

    $("#bt"+bt_ind).prop("disabled", true);
}

function leave() {

    var leave_json = {}

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/leave",
        data: JSON.stringify(leave_json),
        dataType: 'json',
        beforeSend: function(xhr){
            if(sessionStorage.getItem('session') !== null) {
                xhr.setRequestHeader('authorization', sessionStorage.getItem('session'));
            }},
        cache: false,
        timeout: 600000,
        success: function (data){
            console.log("SUCCESS : ", data);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });

}

function login() {

    var login_json = {}
    login_json["username"] = $("#login_name").val();
    login_json["password"] = $("#login_password").val();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/login",
        data: JSON.stringify(login_json),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data){
            if(data.hasOwnProperty('session')) {
                sessionStorage.setItem('session', data['session'])
            }
            console.log("SUCCESS : ", data);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function startGame(player) {
    var game_json = {}
    game_json["player"] = player;

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/game",
        data: JSON.stringify(game_json),
        dataType: 'json',
        beforeSend: function(xhr){
            if(sessionStorage.getItem('session') !== null) {
                xhr.setRequestHeader('authorization', sessionStorage.getItem('session'));
            }},
        cache: false,
        timeout: 600000,
        success: function (data){
            console.log("SUCCESS : ", data);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function registerF() {

    var register_json = {}
    register_json["username"] = $("#reg_name").val();
    register_json["password"] = $("#reg_password").val();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/register",
        data: JSON.stringify(register_json),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data){
            console.log("SUCCESS : ", data);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}