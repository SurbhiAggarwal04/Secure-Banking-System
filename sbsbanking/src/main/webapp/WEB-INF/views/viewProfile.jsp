<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/welcome.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/respond.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/login.js"></script>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/gt_favicon.png">

<link rel="stylesheet" media="screen" href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/leftSideBar.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">

<!-- Custom styles for our template -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" media="screen">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
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
			<label for="username" class="col-lg-10 control-label" style="margin-left: 0%; text-align: right; font-size: large;">Welcome
				User : ${pageContext.request.userPrincipal.name}</label>

		</c:if>
		<div class="row">

			<!-- Article main content -->
			<article class="col-xs-12 maincontent"> <header class="page-header"> <!-- <h1 class="page-title">Welcome</h1> -->
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
                </form> --%> </header>
			<div class="row">
				<jsp:include page="leftSideBar.jsp"></jsp:include>
				<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2" style="margin-left: 0%">
					<div class="panel panel-default">
						<div class="panel-body">
							<!-- 	<h3 class="thin text-center">Sign in to your account</h3> -->
							<p></p>
							<hr>
							<form name='viewProfile' method='POST' class="form-horizontal">

								<div class="form-group">
									<label for="firstname" class="col-lg-4 control-label" style="margin-left: 0%; text-align: left;">Name</label>

									<div class="col-lg-8" style="margin-left: 0%;">
										<!-- <input path="userProfileInfo.firstname" class="form-control" id="firstname" name="firstname"
                                                placeholder="First Name" onblur="firstnameCheck()"/> -->
										<label for="firstname" class="col-lg-8 control-label" style="margin-left: 0%; text-align: left;">${user.userProfileInfo.firstname}
											${user.userProfileInfo.lastname}</label>

									</div>

								</div>


								<div class="form-group">
									<label for="dob" class="col-lg-4 control-label" style="margin-left: 0%; text-align: left;">Date of Birth</label>

									<div class="col-lg-8">
										<label for="dob" class="col-lg-8 control-label" style="margin-left: 0%; text-align: left;">${user.userProfileInfo.dob}</label>
										<!-- 		<input path="userProfileInfo.dob" class="form-control" id="dob" name="dob"  onblur="dobCheck()"/>
                                                    <label class="help-inline" id="dobError" ></label> -->
									</div>

								</div>
								<div class="form-group">
									<label for="address" class="col-lg-4 control-label" style="margin-left: 0%; text-align: left;">Address</label>

									<div class="col-lg-8">
										<label for="address" class="col-lg-12 control-label" style="margin-left: 0%; text-align: left;">${user.userProfileInfo.address.addressLine1},${user.userProfileInfo.address.addressLine2},${user.userProfileInfo.address.city},${user.userProfileInfo.address.state},${user.userProfileInfo.address.areaCode}</label>
										<!-- 	<input path="userProfileInfo.address.addressLine1" type="text" class="form-control" id="addressLine1"
                                                    name="addressLine1" placeholder="Address" onblur="addressLine1chcek()"/>
                                                <label class="help-inline" id="address1Error" ></label> -->
									</div>

								</div>


								<div class="form-group">
									<label for="emailid" class="col-lg-4 control-label" style="margin-left: 0%; text-align: left;">Email id*</label>

									<div class="col-lg-8">
										<label for="emailid" class="col-lg-8 control-label" style="margin-right: 0%; text-align: left;">${user.userProfileInfo.emailId}</label>
										<!-- <input type="text" class="form-control" id="emailid" name="emailid" placeholder="Email id"
                                                path="userProfileInfo.emailId" onfocus="emailCheck()" onkeypress="emailCheck()" onmousemove="emailCheck()"/>
                                            <label class="help-inline" id="emailidError" ></label> -->
									</div>

								</div>
								<%-- <div class="form-group">
                                            <label for="ssn" class="col-lg-4 control-label" style="margin-left: 0%; text-align: left;" >SSN</label>
                                            <div class="col-lg-8">
                                            <label for="ssn" class="col-lg-8 control-label" style="margin-left: 0%; text-align: left;">${user.userProfileInfo.ssn}</label>
                                                <!-- <input type="text" class="form-control" id="ssn" name="ssn"  path="userProfileInfo.ssn" onblur="ssnCheck()"/>
                                                <label class="help-inline" id="ssnError" ></label> -->
                                            </div>

                                        </div> --%>
								<div class="form-group">
									<label for="phno" class="col-lg-4 control-label" style="margin-left: 0%; text-align: left;">Phone No</label>

									<div class="col-lg-8">
										<label for="phno" class="col-lg-8 control-label" style="margin-left: 0%; text-align: left;">${user.userProfileInfo.phone}</label>
										<!-- <input type="text" class="form-control" id="phno" name="phno"  path="userProfileInfo.phone" onblur="phnoCheck()"/>
                                            <label class="help-inline" id="phnoError" ></label> -->
									</div>

								</div>

							</form>
						</div>
					</div>


				</div>
			</div>
			</article>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>