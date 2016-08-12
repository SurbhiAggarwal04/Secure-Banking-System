<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaImpl" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaResponse" %>
<%@ taglib prefix='tags' tagdir='/WEB-INF/tags' %>
<%@page session="true" %>
<html>
<head>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>


    <script src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/signup.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.maskedinput.js"></script>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/gt_favicon.png">

    <link rel="stylesheet" media="screen" href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">

    <!-- Custom styles for our template -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" media="screen">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">

    <%

        if (request.getParameter("sign_up") != null) //btnSubmit is the name of your button, not id of that button.
        {
            out.print("hello");
            java.util.Date d = new java.util.Date();
            System.out.println(d.toString());

            String remoteAddr = request.getRemoteAddr();
            ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
            reCaptcha.setPrivateKey("6LcXwgwTAAAAAHjcMU1XH7l0flAcXNED79v4Yl56");

            String challenge = request.getParameter("recaptcha_challenge_field");

            String uresponse = request.getParameter("recaptcha_response_field");

            ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);

            if (reCaptchaResponse.isValid()) {
                System.out.print("Answer was entered correctly!");
            } else {
                System.out.print("Answer is wrong");
            }
        }
    %>


    <script type="text/JavaScript">

        $(document)
                .ready(
                function () {

                    $
                            .ajax({
                                url: "${pageContext.request.contextPath}/getSecurityQuestions",
                                type: "GET",
                                dataType: "json",
                                success: function (data) {
                                    $.each(data, function (key, value) {

                                        $("#question2").append(
                                                "<option value=" + value.qId + ">"
                                                + value.qText
                                                + "</option>");
                                        $("#question1").append(
                                                "<option value=" + value.qId + ">"
                                                + value.qText
                                                + "</option>");
                                    });
                                }
                            });
                });

        $(document).ready(
                function () {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/getStates",
                        type: "GET",
                        dataType: "json",
                        success: function (states) {
                            $.each(states, function (key, value) {
                                $("#states").append(
                                        "<option value=" + value.stateId + ">"
                                        + value.stateName + "</option>");

                            });
                        }
                    });
                });


        $(document).ready(function () {
            $("#username").blur(function () {

                $.ajax({
                    url: "${pageContext.request.contextPath}/getUser",
                    type: "GET",
                    data: {
                        username: $("#username").val()
                    },
                    dataType: "json",
                    success: function (questions) {
                        $('#username').parent().removeClass("has-error");
                        $('#usernameError').hide();
                    },
                    error: function (data) {
                        $('#usernameError').text("Username exists. Choose a different one");
                        $('#usernameError').show();
                        $('#username').parent().addClass("has-error");
                    }
                });
            });
        });

        $(document).ready(function () {
            $("#emailid").blur(function () {

                $.ajax({
                    url: "${pageContext.request.contextPath}/getEmail",
                    type: "GET",
                    data: {
                        emailid: $("#emailid").val()
                    },
                    dataType: "json",
                    success: function (questions) {
                        $('#emailid').parent().removeClass("has-error");
                        $('#emailidError').hide();
                    },
                    error: function (data) {
                        $('#emailidError').text("Email id already present");
                        $('#emailidError').show();
                        $('#emailid').parent().addClass("has-error");
                    }
                });
            });
        });


    </script>
</head>
<style type="text/css">
    .help-inline {
        color: #FF0000;
    }
</style>

<meta charset="utf-8">


</head>

<body>


<!-- Fixed navbar -->
<jsp:include page="header.jsp"></jsp:include>
<!-- /.navbar -->

<header id="head" class="secondary"></header>

<!-- container -->
<div class="container">

    <ol class="breadcrumb">
        <li><a href="index.html">Home</a></li>
        <li class="active">User access</li>
    </ol>

    <div class="row">

        <!-- Article main content -->
        <article class="col-xs-12 maincontent">
            <header class="page-header">
                <h1 class="page-title">Sign Up</h1>
            </header>

            <div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <!-- 	<h3 class="thin text-center">Sign in to your account</h3> -->
                        <p></p>
                        <hr>
                        <!--  action="${pageContext.request.contextPath}/register" -->
                        <c:if test="${empty signUpSuccessMessage}">


                            <form:form method='POST' commandName="userForm" id="signupForm"
                                       name="signupForm" class="form-horizontal"
                                       action="${pageContext.request.contextPath}/register"
                                       style="width: 100%">

                                <div class="form-group">
                                    <form:label for="firstname" path="" class="col-lg-4 control-label"
                                                style="margin-left: 0%; text-align: left;">First Name*</form:label>

                                    <div class="col-lg-8" style="margin-left: 0%;">
                                        <form:input path="userProfileInfo.firstname" class="form-control" id="firstname"
                                                    name="firstname"
                                                    placeholder="First Name" onblur="firstnameCheck()"/>

                                        <form:label path="" class="help-inline" id="firstnameError"></form:label>

                                    </div>

                                </div>


                                <div class="form-group">
                                    <form:label path="" for="" class="col-lg-4 control-label"
                                                style="margin-left: 0%; text-align: left;">Last Name*</form:label>
                                    <div class="col-lg-8" style="margin-left: 0%;">
                                        <form:input path="userProfileInfo.lastname" class="form-control" id="lastname"
                                                    name="lastname" placeholder="Last Name" onblur="lastnameCheck()"/>
                                        <form:label class="help-inline" id="lastnameError" path=""></form:label>

                                    </div>
                                </div>
                                <div class="form-group">
                                    <form:label for="dob" class="col-lg-4 control-label"
                                                style="margin-left: 0%; text-align: left;" path="">Date of
                                        Birth*</form:label>
                                    <div class="col-lg-8">
                                        <form:input path="userProfileInfo.dob" class="form-control" id="dob" name="dob"
                                                    onblur="dobCheck()"/>
                                        <form:label class="help-inline" id="dobError" path=""></form:label>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <form:label path="" for="addressLine1" class="col-lg-4 control-label"
                                                style="margin-left: 0%; text-align: left;">Address
                                        Line 1*</form:label>
                                    <div class="col-lg-8">
                                        <form:input path="userProfileInfo.address.addressLine1" type="text"
                                                    class="form-control" id="addressLine1"
                                                    name="addressLine1" placeholder="Address"
                                                    onblur="addressLine1chcek()"/>
                                        <form:label class="help-inline" id="address1Error" path=""></form:label>
                                    </div>

                                </div>
                                <div class="form-group">
                                    <form:label path="" for="addressLine2" class="col-lg-4 control-label"
                                                style="margin-left: 0%; text-align: left;">Address
                                        Line 2</form:label>
                                    <div class="col-lg-8">
                                        <form:input path="userProfileInfo.address.addressLine2" type="text"
                                                    class="form-control" id="addressLine2"
                                                    name="addressLine2" placeholder="Address"/>
                                        <form:label class="help-inline" id="address2Error" path=""></form:label>
                                    </div>

                                </div>
                                <div class="form-group">
                                    <form:label for="city" class="col-lg-4 control-label"
                                                style="margin-left: 0%; text-align: left;" path="">City*</form:label>
                                    <div class="col-lg-8">
                                        <form:input path="userProfileInfo.address.city" type="text" class="form-control"
                                                    id="city" name="city"
                                                    placeholder="City" onblur="cityCheck()"/>
                                        <form:label class="help-inline" id="cityError" path=""></form:label>
                                    </div>

                                </div>
                                <div class="form-group">
                                    <form:label for="state" class="col-lg-4 control-label"
                                                style="margin-left: 0%; text-align: left;"
                                                path="userProfileInfo.address.state">State*</form:label>
                                    <div class="col-lg-8">
                                        <div class="col-lg-8">
                                            <form:select id="states" name="states" class="form-control"
                                                         path="userProfileInfo.address.state" onblur="stateCheck()">
                                                <form:option value="Select one">Select one</form:option>
                                            </form:select>
                                            <!--<input type="text" class="form-control" id="state" -->
                                            <!--	name="state" placeholder="State"> <label -->
                                            <!-- 	class="help-inline" id="stateError"></label> -->
                                        </div>
                                        <form:label class="help-inline" id="statesError" path=""></form:label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <form:label for="zipcode" class="col-lg-4 control-label"
                                                style="margin-left: 0%; text-align: left;"
                                                path="">Zip Code*</form:label>
                                    <div class="col-lg-8">
                                        <div class="col-lg-8">
                                            <form:input id="zipcode" name="zipcode" class="form-control"
                                                        path="userProfileInfo.address.areaCode" type="text"
                                                        onblur="zipcodeCheck()"/>
                                            <form:label class="help-inline" id="zipcodeError" path=""></form:label>


                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <form:label for="emailid" class="col-lg-4 control-label"
                                                style="margin-left: 0%; text-align: left;"
                                                path="">Email id*</form:label>
                                    <div class="col-lg-8">
                                        <form:input type="text" class="form-control" id="emailid" name="emailid"
                                                    placeholder="Email id"
                                                    path="userProfileInfo.emailId" onfocus="emailCheck()"
                                                    onkeypress="emailCheck()" onmousemove="emailCheck()"/>
                                        <form:label class="help-inline" id="emailidError" path=""></form:label>
                                    </div>

                                </div>
                                <div class="form-group">
                                    <form:label for="ssn" class="col-lg-4 control-label"
                                                style="margin-left: 0%; text-align: left;" path="">SSN*</form:label>
                                    <div class="col-lg-8">
                                        <form:input type="text" class="form-control" id="ssn" name="ssn"
                                                    path="userProfileInfo.ssn" onblur="ssnCheck()"/>
                                        <form:label class="help-inline" id="ssnError" path=""></form:label>
                                    </div>

                                </div>
                                <div class="form-group">
                                    <form:label for="phno" class="col-lg-4 control-label"
                                                style="margin-left: 0%; text-align: left;"
                                                path="">Phone No*</form:label>
                                    <div class="col-lg-8">
                                        <form:input type="text" class="form-control" id="phno" name="phno"
                                                    path="userProfileInfo.phone" onblur="phnoCheck()"/>
                                        <form:label class="help-inline" id="phnoError" path=""></form:label>
                                    </div>

                                </div>
                                <div class="form-group">
                                    <form:label for="username" class="col-lg-4 control-label"
                                                style="margin-left: 0%; text-align: left;"
                                                path="">UserName*</form:label>
                                    <div class="col-lg-8">
                                        <form:input type="text" class="form-control" id="username" name="username"
                                                    placeholder="User Name" path="username" onfocus="usernameCheck()"
                                                    onkeypress="usernameCheck()" onmousemove="usernameCheck()"/>
                                        <form:label class="help-inline" id="usernameError" path=""></form:label>
                                        <!-- <span class="help-inline">Username is atleast 6 characters long</span>
                                            <span class="help-inline">Username can only consist of a-z,A-Z,.,_</span> -->
                                    </div>

                                </div>

                                <div class="form-group">
                                    <form:label for="password" class="col-lg-4 control-label"
                                                style="margin-left: 0%; text-align: left;"
                                                path=""> Password*</form:label>
                                    <div class="col-lg-8">
                                        <form:input type="password" class="form-control" id="password" name="password"
                                                    placeholder="Password" path="password" onblur="passwordCheck()"/>
                                        <form:label class="help-inline" id="passwordError" path=""></form:label>
                                    </div>
                                    <!-- 	<label>Password <span class="text-danger">*</span></label>
                                    <input type="password" class="form-control" name='password' id="password"> -->
                                </div>

                                <div class="form-group">
                                    <form:label for="confirmPassword" class="col-lg-4 control-label"
                                                style="margin-left: 0%; text-align: left;" path="">Confirm
                                        Password*</form:label>
                                    <div class="col-lg-8">
                                        <form:input type="password" class="form-control" id="confirmPassword"
                                                    name="confirmPassword"
                                                    placeholder="Confirm Password" path="confirmPassword"
                                                    onblur="confirmpasswordCheck()"/>
                                        <form:label class="help-inline" id="confirmPasswordError" path=""></form:label>
                                    </div>

                                </div>
                                <div class="form-group">
                                    <form:label class="col-lg-4 control-label"
                                                style="margin-left: 0%; text-align: left;" path="question1">Select
                                        Security Question 1*:</form:label>
                                    <div class="col-lg-8">
                                        <form:select class="form-control" path="question1" id="question1"
                                                     onblur="question1Check()" name="question1">
                                            <form:option value="Select one">Select one</form:option>
                                        </form:select>
                                        <form:label class="help-inline" id="question1Error"
                                                    style="margin-left: 0%; text-align: left;" path=""></form:label>
                                        <form:input type="text" class="form-control" id="answer1" name="answer1"
                                                    placeholder="Answer" path="answer1" onblur="answer1Check()"/>
                                        <form:label class="help-inline" id="answer1Error" path=""></form:label>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <form:label for="sq2" class="col-lg-4 control-label"
                                                style="margin-left: 0%; text-align: left;" path="question2">Select
                                        Security Question 2*:</form:label>
                                    <div class="col-lg-8">
                                        <form:select class="form-control" path="question2" id="question2"
                                                     onblur="question2Check()">
                                            <form:option value="Select one">Select one</form:option>
                                        </form:select>
                                        <form:label class="help-inline" id="question2Error" path=""></form:label>
                                        <form:input type="text" class="form-control" id="answer2" name="answer2"
                                                    placeholder="Answer" path="answer2" onblur="answer2Check()"/>
                                        <form:label class="help-inline" id="answer2Error" path=""></form:label>

                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-lg-8" style="margin-left: 35%">

                                        <spring:message code="recaptcha.private.key" var="privateKey"></spring:message>
                                        <spring:message code="recaptcha.private.key" var="publicKey"></spring:message>
                                        <tags:reCaptcha privateKey="${privateKey}"
                                                        publicKey="6LcXwgwTAAAAAPE67KlQs-lPzZ_Eb3CMR6vj5V6H"> </tags:reCaptcha>
                                        <c:if test="${not empty recaptchaMessage}">
                                            <%-- <div style="color: red;">${error}</div> --%>
                                            <form:label class="help-inline" id="recaptchaError"
                                                        path="">${error}</form:label>
                                        </c:if>
                                            <%-- <form:errors path="captcha" cssClass="error" /> --%>
                                    </div>
                                </div>


                                <hr>

                                <div class="row" style="text-align: center">

                                    <form:label class="help-inline" id="sign_upError" path=""></form:label>
                                </div>
                                <div class="row" style="text-align: center">
                                    <form:input id="btnSignup" name="btnSignup" type="button" value="Sign Up"
                                                class="btn btn-primary" path="" onclick="signup()"/>

                                    <form:button name="btnCancel" type="button" value="Cancel" class="btn btn-action"
                                                 onclick="cancel()">Cancel</form:button>

                                </div>
                                <form:input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" path=""/>
                            </form:form>
                        </c:if>
                        <c:if test="${not empty signUpSuccessMessage}">

                            <label for="confirmPassword" class="col-lg-4 control-label"
                                   style="margin-left: 0%; text-align: left;" path="">${signUpSuccessMessage}</label>

                        </c:if>
                    </div>
                </div>

            </div>

        </article>
        <!-- /Article -->

    </div>
</div>
<!-- /container -->


<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>
</html>