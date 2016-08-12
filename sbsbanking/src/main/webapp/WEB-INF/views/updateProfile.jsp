<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <style type="text/css">
        .help-inline {
            color: #FF0000;
        }

    </style>
    <script type="text/javascript">
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

    </script>

    <script
            src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.js"></script>
    <script
            src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <script
            src="${pageContext.request.contextPath}/resources/js/jquery.maskedinput.js"></script>
    <%-- <script
        src="${pageContext.request.contextPath}/resources/js/updateProfile.js"></script> --%>
    <script
            src="${pageContext.request.contextPath}/resources/js/respond.min.js"></script>
    <script
            src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/leftSideBar.css">
    <link rel="shortcut icon"
          href="${pageContext.request.contextPath}/resources/images/gt_favicon.png">

    <link rel="stylesheet" media="screen"
          href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">

    <!-- Custom styles for our template -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css"
          media="screen">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/main.css">
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
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <label for="username" class="col-lg-10 control-label"
               style="margin-left: 0%; text-align: right; font-size: large;">Welcome User
            : ${pageContext.request.userPrincipal.name}</label>

    </c:if>
    <div class="row">

        <!-- Article main content -->
        <article class="col-xs-12 maincontent">
            <header class="page-header">

                <!-- <h1 class="page-title">Welcome</h1> -->
                <%-- <c:url value="/logout" var="logoutUrl" />
                <form action="${logoutUrl}" method="post" id="logoutForm" class="form-horizontal">
                    <input type="hidden" name="${_csrf.parameterName}"
                        value="${_csrf.token}" />

                <c:if test="${pageContext.request.userPrincipal.name != null}">
                    <h2>
                        User : ${pageContext.request.userPrincipal.name} | <a
                            href="javascript:formSubmit()"> Logout</a>
                    </h2>
                </c:if>
                </form> --%>
            </header>
            <sec:authorize access="hasRole('ROLE_USER')">
                <div class="row">
                    <jsp:include page="leftSideBar.jsp"></jsp:include>
                    <div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2" style="margin-left: 0%">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <!-- 	<h3 class="thin text-center">Sign in to your account</h3> -->
                                <p></p>
                                <hr>
                                <form:form name='updateProfile' id="updateProfile" method='POST' class="form-horizontal"
                                           commandName="userForm"
                                           action="${pageContext.request.contextPath}/updateProfile">

                                    <div class="form-group">
                                        <form:label for="firstname" path="" class="col-lg-4 control-label"
                                                    style="margin-left: 0%; text-align: left;">First Name*</form:label>

                                        <div class="col-lg-8" style="margin-left: 0%;">
                                            <form:input path="userProfileInfo.firstname" class="form-control"
                                                        id="firstname" name="firstname"
                                                        onblur="firstnameCheck()"
                                                        value="${user.userProfileInfo.firstname}"/>

                                            <form:label path="" class="help-inline" id="firstnameError"></form:label>

                                        </div>

                                    </div>


                                    <div class="form-group">
                                        <form:label path="" for="" class="col-lg-4 control-label"
                                                    style="margin-left: 0%; text-align: left;">Last Name*</form:label>
                                        <div class="col-lg-8" style="margin-left: 0%;">
                                            <form:input path="userProfileInfo.lastname" class="form-control"
                                                        id="lastname" name="lastname" onblur="lastnameCheck()"
                                                        value="${user.userProfileInfo.lastname}"/>
                                            <form:label class="help-inline" id="lastnameError" path=""></form:label>

                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <form:label for="dob" class="col-lg-4 control-label"
                                                    style="margin-left: 0%; text-align: left;" path="">Date of
                                            Birth*</form:label>
                                        <div class="col-lg-8">
                                            <form:input path="userProfileInfo.dob" class="form-control" id="dob"
                                                        name="dob" onblur="dobCheck()"
                                                        value="${user.userProfileInfo.dob}"/>
                                            <form:label class="help-inline" id="dobError" path=""></form:label>
                                        </div>

                                    </div>
                                    <%-- <div class="form-group">
                                        <form:label path="" for="addressLine1" class="col-lg-4 control-label" style="margin-left: 0%; text-align: left;">Address
                                            Line 1*</form:label>
                                        <div class="col-lg-8">
                                            <form:input path="userProfileInfo.address.addressLine1" type="text" class="form-control" id="addressLine1"
                                                name="addressLine1" onblur="addressLine1chcek()" value="${user.userProfileInfo.address.addressLine1}"/>
                                            <form:label class="help-inline" id="address1Error" path=""></form:label>
                                        </div>

                                    </div> --%>
                                    <%-- <div class="form-group">
                                        <form:label path="" for="addressLine2" class="col-lg-4 control-label" style="margin-left: 0%; text-align: left;">Address
                                            Line 2</form:label>
                                        <div class="col-lg-8">
                                            <form:input path="userProfileInfo.address.addressLine2" type="text" class="form-control" id="addressLine2"
                                                name="addressLine2" placeholder="Address" value="${user.userProfileInfo.address.addressLine2}"/>
                                            <form:label class="help-inline" id="address2Error" path=""></form:label>
                                        </div>

                                    </div> --%>
                                    <%-- <div class="form-group">
                                        <form:label for="city" class="col-lg-4 control-label" style="margin-left: 0%; text-align: left;" path="">City*</form:label>
                                        <div class="col-lg-8">
                                            <form:input path="userProfileInfo.address.city" type="text" class="form-control" id="city" name="city"
                                                placeholder="City" onblur="cityCheck()" value="${user.userProfileInfo.address.city}"/>
                                            <form:label class="help-inline" id="cityError" path=""></form:label>
                                        </div>

                                    </div> --%>
                                    <%-- 	<div class="form-group">
                                            <form:label for="state" class="col-lg-4 control-label" style="margin-left: 0%; text-align: left;" path="userProfileInfo.address.state">State*</form:label>
                                            <div class="col-lg-8">
                                                <div class="col-lg-8">
                                                    <form:select id="states" name="states" class="form-control" path="userProfileInfo.address.state" onblur="stateCheck()">
                                                    <form:option value="${user.userProfileInfo.address.state}" selected="selected">${user.userProfileInfo.address.state}</form:option>
                                                </form:select>
                                                    <!--<input type="text" class="form-control" id="state" -->
                                                    <!--	name="state" placeholder="State"> <label -->
                                                    <!-- 	class="help-inline" id="stateError"></label> -->
                                                </div>
                                                <form:label class="help-inline" id="statesError" path=""></form:label>
                                            </div>
                                        </div> --%>
                                    <%-- <div class="form-group">
                                        <form:label for="zipcode" class="col-lg-4 control-label" style="margin-left: 0%; text-align: left;" path="">Zip Code*</form:label>
                                        <div class="col-lg-8">
                                            <div class="col-lg-8">
                                                <form:input id="zipcode" name="zipcode" class="form-control" path="userProfileInfo.address.areaCode" type="text" onblur="zipcodeCheck()" value="${user.userProfileInfo.address.areaCode}"/>
                                                <form:label class="help-inline" id="zipcodeError" path=""></form:label>


                                            </div>
                                        </div>
                                    </div>
     --%>
                                    <div class="form-group">
                                        <form:label for="emailid" class="col-lg-4 control-label"
                                                    style="margin-left: 0%; text-align: left;"
                                                    path="">Email id*</form:label>
                                        <div class="col-lg-8">
                                            <form:input type="text" class="form-control" id="emailid" name="emailid"
                                                        path="userProfileInfo.emailId" onfocus="emailCheck()"
                                                        onkeypress="emailCheck()" onmousemove="emailCheck()"
                                                        value="${user.userProfileInfo.emailId}"/>
                                            <form:label class="help-inline" id="emailidError" path=""></form:label>
                                        </div>

                                    </div>
                                    <%-- <div class="form-group">
                                        <form:label for="ssn" class="col-lg-4 control-label" style="margin-left: 0%; text-align: left;" path="">SSN*</form:label>
                                        <div class="col-lg-8">
                                            <form:input type="text" class="form-control" id="ssn" name="ssn"  path="userProfileInfo.ssn" onblur="ssnCheck()"/>
                                            <form:label class="help-inline" id="ssnError" path=""></form:label>
                                        </div>

                                    </div> --%>
                                    <div class="form-group">
                                        <form:label for="phno" class="col-lg-4 control-label"
                                                    style="margin-left: 0%; text-align: left;"
                                                    path="">Phone No*</form:label>
                                        <div class="col-lg-8">
                                            <form:input type="text" class="form-control" id="phno" name="phno"
                                                        path="userProfileInfo.phone" onblur="phnoCheck()"
                                                        value="${user.userProfileInfo.phone}"/>
                                            <form:label class="help-inline" id="phnoError" path=""></form:label>
                                        </div>

                                    </div>

                                    <hr>

                                    <div class="row" style="text-align: center">

                                        <form:label class="help-inline" id="updateProfileError" path=""></form:label>
                                    </div>
                                    <div class="row" style="text-align: center">
                                            <%-- <form:input id="btnUpdate" name="btnUpdate" type="button" value="Update"
                                               class="btn btn-primary"  path="" />   --%>
                                        <input type="submit" value="submit"/>
                                        <form:button name="btnCancel" type="button" value="Cancel"
                                                     class="btn btn-action" onclick="cancel()">Cancel</form:button>

                                    </div>
                                    <form:input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"
                                                path=""/>

                                </form:form>
                            </div>
                        </div>


                    </div>
                </div>
            </sec:authorize>
        </article>
    </div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>