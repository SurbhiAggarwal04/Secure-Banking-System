/**
 *
 */
$(document).ready(function () {
    $('#usernameError').hide();
    $('#passwordError').hide();
});
function login() {

    /*var errorElements = document.getElementsByClassName('help-inline')

     for (var i = 0; i < errorElements.length; i++){
     errorElements[i].style.display = 'none';
     }*/
    $('#usernameError').hide();
    $('#passwordError').hide();
    $('#username').parent().removeClass("has-error");
    $('#password').parent().removeClass("has-error");
    /*var usernameRegex=/^[a-zA-Z0-9_\.]+$/;*/


    if ($("#username").val().trim().length == 0) {

        $('#usernameError').text("Username required");
        $('#usernameError').show();

        $('#username').parent().addClass("has-error");
        /* $("input#FNameTB").labelify({ labelledClass: "greylabel" }); */
    }

    /* else if($("#username").val().trim().length<=6 && !usernameRegex.test($("#username").val()))
     {
     $('#usernameError').text("Username must be more than 6 characters long and can only consist of a-z,A-Z,0-9,.,_");
     $('#usernameError').show();
     $('#username').parent().addClass("has-error");
     document.loginForm.username.focus();
     }
     else if ($("#username").val().trim().length<=6)
     {
     $('#usernameError').text("Username must be more than 6 characters long");
     $('#usernameError').show();
     $('#username').parent().addClass("has-error");
     document.loginForm.username.focus();
     }



     else if(!usernameRegex.test($("#username").val()))
     {
     $('#usernameError').text("Username can only consist of a-z,A-Z,0-9,.,_");
     $('#usernameError').show();
     $('#username').parent().addClass("has-error");
     document.loginForm.username.focus();
     }
     */


    if ($("#password").val().trim().length == 0) {

        $('#passwordError').text("Password required");
        $('#passwordError').show();
        $('#password').parent().addClass("has-error");

        /* $("input#FNameTB").labelify({ labelledClass: "greylabel" }); */
    }
    /* else if($("#password").val().trim().length<=8)
     {
     $('#passwordError').text("Password must be more than 8 characters long");
     $('#passwordError').show();
     $('#password').parent().addClass("has-error");
     document.loginForm.password.focus();
     }*/
    if ($('#password').parent().hasClass('has-error') || $('#username').parent().hasClass('has-error')) {
        $('#passwordError').show();
        $('#usernameError').show();
    }
    else {
        /*var action="<c:url value='/login' />"
         document.getElementById("loginForm").action=action;*/

        document.getElementById("loginForm").submit();
    }


}


/*		function removeErrors() {

 var errorElements = document.getElementsByClassName('help-inline')
 for (var i = 0; i < errorElements.length; i++) {
 errorElements[i].style.display = 'none';
 }
 }*/


function usernameCheck() {

    /* var usernameRegex=/^[a-zA-Z0-9_\.]+$/;*/
    $('#username').parent().removeClass("has-error");


    if ($("#username").val().trim().length == 0) {

        $('#usernameError').text("Username required");
        $('#usernameError').show();
        $('#username').parent().addClass("has-error");

        /* $("input#FNameTB").labelify({ labelledClass: "greylabel" }); */
    }

    /* else if($("#username").val().trim().length<=6 && !usernameRegex.test($("#username").val()))
     {
     $('#usernameError').text("Username must be more than 6 characters long and can only consist of a-z,A-Z,0-9,.,_");
     $('#usernameError').show();
     $('#username').parent().addClass("has-error");
     document.loginForm.username.focus();
     }

     else if ($("#username").val().trim().length<6)
     {
     $('#usernameError').text("Username must be more than 6 characters long");
     $('#usernameError').show();
     $('#username').parent().addClass("has-error");
     document.loginForm.username.focus();
     }

     else if(!usernameRegex.test($("#username").val()))
     {
     $('#usernameError').text("Username can only consist of a-z,A-Z,0-9,.,_");
     $('#usernameError').show();
     $('#username').parent().addClass("has-error");
     document.loginForm.username.focus();
     }*/
    else {
        $('#usernameError').hide();
        /* document.loginForm.username.focus(); */
    }
}


function passwordCheck() {
    $('#password').parent().removeClass("has-error");
    if ($("#password").val().trim().length == 0) {

        $('#passwordError').text("Password required");
        $('#passwordError').show();
        $('#password').parent().addClass("has-error");

        /* $("input#FNameTB").labelify({ labelledClass: "greylabel" }); */
    }
    /* else if($("#password").val().trim().length<8)
     {
     $('#passwordError').text("Password must be more than 8 characters long");
     $('#passwordError').show();
     $('#password').parent().addClass("has-error");
     document.loginForm.password.focus();
     }*/
    else {
        $('#passwordError').hide();
    }
}
		  
		
		