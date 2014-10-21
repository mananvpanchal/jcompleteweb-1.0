function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ')
            c = c.substring(1);
        if (c.indexOf(name) != -1)
            return c.substring(name.length, c.length);
    }
    return "";
}

function checkCookie() {
    var user = getCookie("username");
    if (user != "") {
        alert("Welcome again " + user);
    } else {
        user = prompt("Please enter your name:", "");
        if (user != "" && user != null) {
            setCookie("username", user, 365);
        }
    }
}

function doLogin() {
    var jsonObj;
    var cred = {
        "username": $("#username").val(),
        "password": $("#password").val()
    };
    $.ajax({
        contentType: "application/json",
        dataType: "text",
        data: JSON.stringify(cred),
        type: "POST",
        url: "webresources/dologin",
        success: function(data, status, xhr) {
            var jwt = JSON.parse(data);
            //setCookie("JWToken", encodeURIComponent(JSON.stringify(jwt)), 2);
            setCookie("JWToken", JSON.stringify(jwt), 2);
            jsonObj = jwt;
        },
        error: function(xhr, status, error) {
            var error = JSON.parse(xhr.responseText);
            jsonObj = error;
        },
        async: false
    });
    return jsonObj;
}

function gotoPage(page) {
    var jsonObj;
    var jwtStr=getCookie("JWToken");
    if(jwtStr===null || jwtStr==="") {
        jwtStr = "{\"username\":\"\", \"userrole\":\"\", \"accessToken\":\"\"}";
    }
    var jwt = JSON.parse(jwtStr);
    $.ajax({
        contentType: "application/json",
        dataType: "text",
        data: JSON.stringify(jwt),
        type: "POST",
        url: "webresources/" + page,
        success: function(data, status, xhr) {
            if (data === "success") {
                window.location.href = page;
            } else {
                window.location.href = "unauthorized.html";
            }
        },
        error: function(xhr, status, error) {
            jsonObj = JSON.parse(xhr.responseText);
        },
        async: false
    });
    return jsonObj;
}

function checkAuthorization(page) {
    var jwtStr=getCookie("JWToken");
    if(jwtStr===null || jwtStr==="") {
        jwtStr = "{\"username\":\"\", \"userrole\":\"\", \"accessToken\":\"\"}";
    }
    var jwt = JSON.parse(jwtStr);
    $.ajax({
        contentType: "application/json",
        dataType: "text",
        data: JSON.stringify(jwt),
        type: "POST",
        url: "webresources/" + page,
        success: function(data, status, xhr) {
            if (data !== "success") {
               alert("The page access is unauthorized.");
            }
        },
        error: function(xhr, status, error) {
            alert(xhr.responseText);
        },
        async: false
    });
}

$(document).ready(function() {
    $("a").click(function(event) {
        event.preventDefault();
        var path=$(this).attr("href");
        gotoPage(path);
    });
});