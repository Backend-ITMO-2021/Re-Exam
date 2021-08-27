window.onload = function () {
    $.ajaxSetup({
        beforeSend : function(xhr, settings) {
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
    getUserName()
};

function getUserName() {
    $.get("/user", function(data) {
        this.username = data.username;
        this.userid = data.id;
        $("#username").html(this.username);
        $(".unauthenticated").hide();
        $(".authenticated").show();
    });
}
function getRooms() {
    $.get("/");
}


function logout() {
    $.post("/logout", function() {
        $("#user").html('');
        $(".unauthenticated").show();
        $(".authenticated").hide();
    })
    return true;
}