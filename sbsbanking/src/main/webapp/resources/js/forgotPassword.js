/**
 *
 */

function usernameCheck() {

    /* var usernameRegex=/^[a-zA-Z0-9_\.]+$/;*/
    $('#username').parent().removeClass("has-error");


    if ($("#username").val().trim().length == 0) {

        $('#usernameError').text("Username required");
        $('#usernameError').show();
        $('#username').parent().addClass("has-error");


    }


    else {
        $('#usernameError').hide();
        /* document.loginForm.username.focus(); */
    }
}

function answer1Check() {

    $('#answer1').parent().removeClass("has-error");
    $('#answer1Error').hide();
    if ($("#answer1").val().trim().length == 0) {

        $('#answer1Error').text("Answer required");
        $('#answer1Error').show();
        $('#answer1').parent().addClass("has-error");

        /* $("input#FNameTB").labelify({ labelledClass: "greylabel" }); */
    }
}
function answer2Check() {

    $('#answer2').parent().removeClass("has-error");
    $('#answer2Error').hide();
    if ($("#answer2").val().trim().length == 0) {

        $('#answer2Error').text("Answer required");
        $('#answer2Error').show();
        $('#answer2').parent().addClass("has-error");

        /* $("input#FNameTB").labelify({ labelledClass: "greylabel" }); */
    }
}