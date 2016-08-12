/**
 *
 */


$(document).ready(function () {

    $("#dob").mask("99/99/9999", {placeholder: 'MM/DD/YYYY'});
    $("#phno").mask("(999) 999-9999", {placeholder: '(000) 000-0000'});
    $("#ssn").mask("999-99-9999", {placeholder: '000-00-0000'});
    $("#zipcode").mask("99999-99999", {placeholder: '00000-00000'});
});
function signup() {

    if ($('#password').parent().hasClass('has-error') || $('#username').parent().hasClass('has-error') || $('#city').parent().hasClass('has-error') ||
        $('#emailid').parent().hasClass('has-error') || $('#dob').parent().hasClass('has-error') || $('#states').parent().hasClass("has-error") ||
        $('#phno').parent().hasClass('has-error') || $('#ssn').parent().hasClass('has-error') ||
        $('#firstname').parent().hasClass('has-error') || $('#lastname').parent().hasClass('has-error') ||
        $('#answer1').parent().hasClass('has-error') || $('#answer2').parent().hasClass('has-error') ||
        $('#question1').parent().hasClass('has-error') || $('#question2').parent().hasClass('has-error') ||
        $('#zipcode').parent().hasClass('has-error') || $('#addressLine1').parent().hasClass('has-error') ||
        $("#dob").val() == "" || $("#dob").val() == null || $("#ssn").val() == "" || $("#ssn").val() == null || $("#ssn").val() == '000-00-0000' ||
        $("#phno").val() == "" || $("#phno").val() == null || $("#phno").val() == '(000) 000-0000' || String($("#phno").val()).indexOf('(000)', 0) == 0 ||
        $("#zipcode").val() == "" || $("#zipcode").val() == null || $("#zipcode").val() == '00000-00000' || String($("#phno").val()).indexOf('00000', 0) == 0 ||
        $("#answer1").val().trim().length == 0 || $("#answer2").val().trim().length == 0 || $("#question1").val() == "Select one" || $("#question2").val() == "Select one" ||
        $("#emailid").val().trim().length == 0 || $("#addressLine1").val().trim().length == 0 || $("#firstname").val().trim().length == 0 ||
        $("#lastname").val().trim().length == 0 || $("#username").val().trim().length == 0 || $("#password").val().trim().length == 0 || $("#confirmPassword").val().trim().length == 0

        || $("#city").val().trim().length == 0 || $("#states").val() == "Select one") {
        $('#sign_upError').text("Please Enter Mandatory Fields");
        $('#sign_upError').show();
    }
    else {
        $('#sign_upError').hide();
        document.getElementById("signupForm").submit();
    }

};


function dobCheck() {


    $('#dobError').hide();

    $('#dobError').parent().removeClass("has-error");
    if ($("#dob").val() == "" || $("#dob").val() == null || String($("#dob").val()).indexOf('D', 0) >= 0 || String($("#dob").val()).indexOf('Y', 0) >= 0 || String($("#dob").val()).indexOf('M', 0) >= 0) {

        $('#dobError').text("Date of birth required");
        $('#dobError').show();
        $('#dob').parent().addClass("has-error");

    }
}
function ssnCheck() {


    $('#ssnError').hide();

    $('#ssnError').parent().removeClass("has-error");
    if ($("#ssn").val() == "" || $("#ssn").val() == null || $("#ssn").val() == '000-00-0000') {

        $('#ssnError').text("SSN required");
        $('#ssnError').show();
        $('#ssn').parent().addClass("has-error");

    }
}

function phnoCheck() {


    $('#phnoError').hide();

    $('#phnoError').parent().removeClass("has-error");
    if ($("#phno").val() == "" || $("#phno").val() == null || $("#phno").val() == '(000) 000-0000' || String($("#phno").val()).indexOf('(000)', 0) == 0) {

        $('#phnoError').text("Phone number required");
        $('#phnoError').show();
        $('#phno').parent().addClass("has-error");

    }
}
function zipcodeCheck() {

    $('#zipcodeError').hide();
    $('#zipcodeError').parent().removeClass("has-error");
    if ($("#zipcode").val() == "" || $("#zipcode").val() == null || $("#zipcode").val() == '00000-00000' || String($("#zipcode").val()).indexOf('00000', 0) == 0) {

        $('#zipcodeError').text("Zipcode required");
        $('#zipcodeError').show();
        $('#zipcode').parent().addClass("has-error");

    }
    else {
        $('#zipcodeError').hide();
        $('#zipcodeError').parent().removeClass("has-error");
    }
}
function cityCheck() {

    $('#city').parent().removeClass("has-error");
    $('#cityError').hide();
    if ($("#city").val().trim().length == 0) {

        $('#cityError').text("City required");
        $('#cityError').show();
        $('#city').parent().addClass("has-error");


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
function stateCheck() {

    $('#states').parent().removeClass("has-error");
    $('#statesError').hide();
    if ($("#states").val() == "Select one") {

        $('#statesError').text("State required");
        $('#statesError').show();
        $('#states').parent().addClass("has-error");

    }


}
function question2Check() {

    $('#question2').parent().removeClass("has-error");
    $('#question2Error').hide();
    if ($("#question2").val() == "Select one") {

        $('#question2Error').text("Question required");
        $('#question2Error').show();
        $('#question2').parent().addClass("has-error");

    }
    else if ($("#question2").val() == $("#question1").val()) {

        $('#question2Error').text("Choose a different question");
        $('#question2Error').show();
        $('#question2').parent().addClass("has-error");

    }

}
function question1Check() {


    $('#question1').parent().removeClass("has-error");
    $('#question1Error').hide();
    if ($("#question1").val() == "Select one") {

        $('#question1Error').text("Question required");
        $('#question1Error').show();
        $('#question1').parent().addClass("has-error");

    }
    else if ($("#question2").val() == $("#question1").val()) {

        $('#question1Error').text("Choose a different question");
        $('#question1Error').show();
        $('#question1').parent().addClass("has-error");

    }
}

function emailCheck() {

    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    $('#emailid').parent().removeClass("has-error");
    $('#emailidError').hide();
    if ($("#emailid").val().trim().length == 0) {

        $('#emailidError').text("Email id required");
        $('#emailidError').show();
        $('#emailid').parent().addClass("has-error");


    }
    else if (!re.test($("#emailid").val())) {
        $('#emailidError').text("Email id Invalid");
        $('#emailidError').show();
        $('#emailid').parent().addClass("has-error");
    }

}

function addressLine1chcek() {

    $('#addressLine1').parent().removeClass("has-error");
    $('#address1Error').hide();
    if ($("#addressLine1").val().trim().length == 0) {

        $('#address1Error').text("Address required");
        $('#address1Error').show();
        $('#addressLine1').parent().addClass("has-error");


    }

}

function firstnameCheck() {

    var usernameRegex = /^[a-zA-Z ]+$/;
    $('#firstname').parent().removeClass("has-error");


    if ($("#firstname").val().trim().length == 0) {

        $('#firstnameError').text("Firstname required");
        $('#firstnameError').show();
        $('#firstname').parent().addClass("has-error");


    }

    else if ($("#firstname").val().trim().length < 2 && !usernameRegex.test($("#username").val())) {

        $('#firstnameError').text("First name must be atleast 2 characters long and can only consist of alphabets");
        $('#firstnameError').show();
        $('#firstname').parent().addClass("has-error");

    }

    else if ($("#firstname").val().trim().length < 2) {

        $('#firstnameError').text("First name must be atleast 2 characters long");
        $('#firstnameError').show();
        $('#firstname').parent().addClass("has-error");

    }

    else if (!usernameRegex.test($("#firstname").val())) {
        $('#firstnameError').text("First name can only consist of alphabets");
        $('#firstnameError').show();
        $('#firstname').parent().addClass("has-error");

    }
    else {
        $('#firstnameError').hide();

    }
}
function lastnameCheck() {

    var usernameRegex = /^[a-zA-Z ]+$/;
    $('#lastname').parent().removeClass("has-error");


    if ($("#lastname").val().trim().length == 0) {

        $('#lastnameError').text("Lastname required");
        $('#lastnameError').show();
        $('#lastname').parent().addClass("has-error");


    }

    else if ($("#lastname").val().trim().length < 2 && !usernameRegex.test($("#lastname").val())) {

        $('#lastnameError').text("Lastname must be atleast 2 characters long and can only consist of alphabets");
        $('#lastnameError').show();
        $('#lastname').parent().addClass("has-error");

    }

    else if ($("#lastname").val().trim().length < 2) {
        /*alert("yes");*/
        $('#lastnameError').text("Lastname must be atleast 2 characters long");
        $('#lastnameError').show();
        $('#lastname').parent().addClass("has-error");

    }

    else if (!usernameRegex.test($("#lastname").val())) {
        $('#lastnameError').text("Lastname can only consist of alphabets");
        $('#lastnameError').show();
        $('#lastname').parent().addClass("has-error");

    }
    else {
        $('#lastnameError').hide();

    }
}

function usernameCheck() {

    var usernameRegex = /^[a-zA-Z0-9_\.\$\@]+$/;
    $('#username').parent().removeClass("has-error");


    if ($("#username").val().trim().length == 0) {

        $('#usernameError').text("Username required");
        $('#usernameError').show();
        $('#username').parent().addClass("has-error");


    }
    else if (!usernameRegex.test($("#username").val())) {
        $('#usernameError').text("Username can only consist of a-z,A-Z,0-9,.,_,$,@");
        $('#usernameError').show();
        $('#username').parent().addClass("has-error");

    }
    else if ($("#username").val().trim().length <= 6 && !usernameRegex.test($("#username").val())) {

        $('#usernameError').text("Username must be more than 6 characters long and can only consist of a-z,A-Z,0-9,.,_");
        $('#usernameError').show();
        $('#username').parent().addClass("has-error");

    }

    else if ($("#username").val().trim().length <= 6) {

        $('#usernameError').text("Username must be more than 6 characters long");
        $('#usernameError').show();
        $('#username').parent().addClass("has-error");

    }


    else {
        $('#usernameError').hide();

    }
}

function passwordCheck() {
    var pwdre = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@,$,.,_]).{8,}/;
    $('#password').parent().removeClass("has-error");
    if ($("#password").val().trim().length == 0) {

        $('#passwordError').text("Password required");
        $('#passwordError').show();
        $('#password').parent().addClass("has-error");

    }
    else if ($("#password").val().trim().length <= 8) {
        $('#passwordError').text("Password must be more than 8 characters long.");
        $('#passwordError').show();
        $('#password').parent().addClass("has-error");

    }
    else if (!pwdre.test($("#password").val())) {
        $('#passwordError').text("Password must include an uppercase charcter, a number and a symbol {@,$,.,_}");
        $('#passwordError').show();
        $('#password').parent().addClass("has-error");

    }
    else if ($("#password").val().trim().length <= 8 && !pwdre.test($("#password").val())) {
        $('#passwordError').text("Password must be more than 8 characters long and must include an uppercase charcter, a number and a symbol");
        $('#passwordError').show();
        $('#password').parent().addClass("has-error");

    }


    else {
        $('#passwordError').hide();
    }
}

function confirmpasswordCheck() {

    $('#confirmPassword').parent().removeClass("has-error");
    var pwdre = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@,$,.,_]).{8,}/;
    if ($("#confirmPassword").val().trim().length == 0) {

        $('#confirmPasswordError').text("Password required");
        $("#confirmPasswordError").parent('label').css('color', 'red');
        $('#confirmPasswordError').show();
        $('#confirmPassword').parent().addClass("has-error");

    }
    else if ($("#confirmPassword").val().trim().length <= 8) {
        $('#confirmPasswordError').text("Password must be more than 8 characters long.");
        $("#confirmPasswordError").parent('label').css('color', 'red');
        $('#confirmPasswordError').show();
        $('#confirmPassword').parent().addClass("has-error");

    }
    else if (!pwdre.test($("#confirmPassword").val())) {
        $('#usernameError').text("Password must include an uppercase charcter, a number and a symbol");
        $("#confirmPasswordError").parent('label').css('color', 'red');
        $('#usernameError').show();
        $('#username').parent().addClass("has-error");

    }
    else if ($("#confirmPassword").val().trim().length <= 8 && !pwdre.test($("#confirmPassword").val())) {
        $('#confirmPasswordError').text("Password must be more than 8 characters long and must include an uppercase charcter, a number and a symbol");
        $("#confirmPasswordError").parent('label').css('color', 'red');
        $('#confirmPasswordError').show();
        $('#confirmPassword').parent().addClass("has-error");

    }


    else if ($("#password").val() != $("#confirmPassword").val()) {
        $('#confirmPasswordError').text("Password does not match");
        $("#confirmPasswordError").parent('label').css('color', 'red');
        $('#confirmPasswordError').show();
        $('#confirmPassword').parent().addClass("has-error");

    }
    else if ($("#password").val() == $("#confirmPassword").val()) {
        $('#confirmPasswordError').text("Password matched");
        $("#confirmPasswordError").prop('disabled', true).css({color: '#006400'});
        /*	$("#confirmPasswordError").parent('label').css('color','#00FF00');*/
        $('#confirmPasswordError').show();
        $('#confirmPassword').parent().addClass("has-success");
    }
    else {
        $('#confirmPasswordError').hide();
    }
}


	 
	    