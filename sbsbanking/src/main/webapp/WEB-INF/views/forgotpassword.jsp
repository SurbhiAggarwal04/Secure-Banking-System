<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page session="true"%>
<html>
<!DOCTYPE html>
<html lang="en">
<head>
<style type="text/css">
.help-inline {
	color: #FF0000;
}
</style>
<script type="text/javascript">
	
</script>

<meta charset="utf-8">


<script src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/forgotPassword.js"></script>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/gt_favicon.png">

<link rel="stylesheet" media="screen" href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">

<!-- Custom styles for our template -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" media="screen">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
<style type="text/css">
.shade {
	background-color: "black";
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#error").css("display", "none");
		$("#answer1_Error").css("display", "none");
		$("#answer2_Error").css("display", "none");
		$("#question1").css("display", "none");
		$("#question2").css("display", "none");
		$("#step1").addClass('shade');
		$("#step1").css({
			backgroundColor : 'black'
		});

	});
	$(document).ready(function() {
		$("#username").click(function() {
			$("#error").css("display", "none");
			$("#answer1_Error").css("display", "none");
			$("#answer2_Error").css("display", "none");
			$("#question1").css("display", "none");
			$("#question2").css("display", "none");
			$("#generateOTP").css("display", "none");
			$("#step1").addClass('shade');
			$("#step1").css({
				backgroundColor : 'black'
			});

		});
	});

	$(document).ready(function() {
		$("#verifyUsername").click(function() {
			$.ajax({
				url : "${pageContext.request.contextPath}/getUserSecQuestions",
				type : "GET",
				data : {
					username : $("#username").val()
				},
				dataType : "json",
				success : function(questions) {
					$("#question1").css("display", "block");
					$("#question2").css("display", "block");
					$("#generateOTP").css("display", "block");
					$("#usernameDiv").css("display", "none");
					$("#step2").addClass('shade');
					$("#step1").removeClass('shade');
					$("#step1").css({
						backgroundColor : 'white'
					});
					$("#step2").css({
						backgroundColor : 'black'
					});
					var obj = 1;
					$.each(questions, function(key, value) {
						$("#q" + obj).text(value.qText);
						obj = 2;
					});
				},
				error : function(data) {

					$("#error").css("display", "block");
				}
			});
		});
	});
	/*  	$(document).ready(function() {
	 $("#verifyAnswers").click(function() {
	 alert("hiiii");
	 $.ajax({
	 url : "${pageContext.request.contextPath}/verifyAnswersAndGenerateOTP",
	 type : "POST",
	 data : {
	 username : $("#username").val(),
	 securityQuestion1 : $("#q1").val(),
	 securityAnswer1 : $("#answer1").val(),
	 securityQuestion2 : $("#q2").val(),
	 securityAnswer2 : $("#answer2").val()
	 },
	 dataType : "json",
	 success : function(data) {
	 alert("no");
	 },
	 error : function(data) {

	 alert("yes");
	 }
	 });
	 });
	 });
	 */
		/* securityQuestion1 : $("#q1").val(), */
//		securityAnswer1 : $(
//				"#answer1")
//				.val(),
		/* securityQuestion2 : $("#q2").val(), */
//		securityAnswer2 : $(
//				"#answer2")
//				.val()

// $(document).ready(function () {
//         $("#verifyAnswers").click(function () {
           
//                 $.post("${pageContext.request.contextPath}/verifyAnswersAndGenerateOTP", { username: $("#username").val(), answer1: $("#answer1").val(), answer2: $("#answer2").val() },
//                 function (data) {                    
//                    alert("hello")
//                 });
               
          
//         });
//     });
 	$(document)
			.ready(
					function() {
						$("#verifyAnswers")
								.click(
										function() {
											
											$
													.ajax({
														url : "${pageContext.request.contextPath}/verifyOTPAndResetPassword",
														type : "GET",
														contentType : "application/json",
														data : {
															username : $(
																	"#username")
																	.val(),
												
														},

														success : function(
																questions) {
															$("#answer1_Error")
																	.css(
																			"display",
																			"none");
															$("#step2").removeClass('shade');
															$("#step1").removeClass('shade');
															$("#step1").css({
																backgroundColor : 'white'
															});
															$("#step2").css({
																backgroundColor : 'white'
															});
															$("#step3").css({
																backgroundColor : 'black'
															});
															$("#verificationDiv").css("display", "block");
															$("#question1").css("display", "none");
															$("#question2").css("display", "none");
															$("#generateOTP").css("display", "none");
															
														},
														error : function(data) {
															
															$("#answer1_Error")
																	.css(
																			"display",
																			"block");
														}
													});
										});
					});
 
	/*	$(document).ready(function() {

	 $("#answer2").blur(function() {

	 $.ajax({
	 url : "${pageContext.request.contextPath}/getSecurityAnswer",
	 type : "GET",
	 data : {
	 username : $("#username").val(),
	 securityQuestion : $("#q2").text(),
	 securityAnswer : $("#answer2").val()
	 },
	 dataType : "json",
	 success : function(questions) {
	 $("#answer2_Error").css("display", "none");
	 },
	 error : function(data) {
	 $("#answer2_Error").css("display", "block");
	 }
	 });
	 });
	 }); */
</script>

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
			<li class="active">Forgot Password</li>
		</ol>

		<div class="row">

			<!-- Article main content -->
			<article class="col-xs-12 maincontent">
				<header class="page-header">
					<h1 class="page-title">Forgot Password</h1>
				</header>

				<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
					<div class="panel panel-default">
						<div class="panel-body">
							<ul class="nav nav-tabs">

								<li class="shade" id="step1"><a href="#">Step 1</a></li>
								<li><a href="#" id="step2">Step 2</a></li>
								<li><a href="#" id="step3">Step 3</a></li>
							</ul>
							<!-- 	<h3 class="thin text-center">Sign in to your account</h3> -->
							<p></p>
							<hr>
                         <c:choose>
                         <c:when test="${not empty answersIncorrectError}">
							<form name='forgotPassword' id="forgotPassword" action="${pageContext.request.contextPath}/verifyAnswersAndGenerateOTP"
								method="post">
								<div class="form-group" id="usernameDiv">
									<label for="username" class="col-lg-12 control-label"><spring:message code="message.enterusername"
											text="Enter username"></spring:message></label>
									<div class="col-lg-8">
										<input type="text" class="form-control" id="username" name="username" placeholder="User Name" onblur="usernameCheck()" />
										<label class="help-inline" id="usernameError"></label>
										<div id="error" style="display: none;" class="col-lg-10">
											<label for="username_not_found" class="help-inline"><spring:message code="message.usernotfound"></spring:message></label>
										</div>
									</div>
									<div class="row" style="text-align: center">
										<div class="col-lg-4 text-right">
											<input type="button" id="verifyUsername" name="verify" value="verify" class="btn btn-action" />
										</div>
									</div>
								</div>
								<c:if test="${not empty answersError}">
									<label class="help-inline">${answersError}</label>
								</c:if>

								<div class="form-group" id="question1" style="display: none;">
									<label  id="q1" class="col-lg-12 control-label" name="q1" style="border: none;" class="form-control"
										></label>
									<div class="col-lg-10">
										<input type="text" class="form-control" id="answer1" placeholder="Answer" onchange="answer1Check()" name="answer1" /> <label
											class="help-inline" id="answer1Error"></label>
										<div id="answer1_Error" style="display: none;" class="col-lg-10">
											<label for="answer_not_verified" class="help-inline"><spring:message code="message.answerincorrect"></spring:message></label>
										</div>
									</div>
								</div>

								<div class="form-group" id="question2" style="display: none;">
									<!-- <label id="q2" class="col-lg-12 control-label" name="q2"></label> -->
									<label  id="q2" class="col-lg-12 control-label" name="q2" style="border: none;" class="form-control"
										 ></label>
									<div class="col-lg-10">
										<input type="text" class="form-control" id="answer2" placeholder="Answer" onchange="answer2Check()" name="answer2" /> <label
											class="help-inline" id="answer2Error"></label>
										<div id="answer2_Error" style="display: none;" class="col-lg-10">
											<label for="answer_not_verified" class="help-inline"><spring:message code="message.answerincorrect"></spring:message></label>
										</div>
									</div>
								</div>


								<hr>

								<label class="help-inline" id="forgotPasswordError"></label>

								<div class="row" style="text-align: center">
									<div class="col-lg-4 text-right" style="display: none;" id="generateOTP">
										<input type="submit" id="verifyAnswers" name="verifyAnswers" value="Verify" class="btn btn-action" />
									</div>
								</div>
								</form>
								</c:when>
								 <c:otherwise>
								  <div id="verificationDiv" style="display:none;">
                              <label class="help-inline" id="otpSuccess" style="color: green;">Please enter the OTP sent to you on email</label>
                              	<form name='verificationForm' method='GET' class="form-horizontal" id="choiceForm" action="<c:url value='/verifyOTPandPassword' />">
                              
								<!-- 	<div class="form-group" id="otpDiv" style="visibility: hidden;"> -->
									<div class="form-group" id="otpDiv">
										<label for="otp" class="col-lg-4 control-label" style="margin-left: 0%; text-align: left;">Verification
											Code</label>
										<div class="col-lg-8">
											<input type="text" class="form-control" id="verificationCode"
												name="verificationCode"> <label class="help-inline"
												id="verificationCodeError"></label>
										</div>

									</div>
									<!-- <div class="form-group" id="passwordDiv" style="visibility: hidden;"> -->
										<div class="form-group" id="passwordDiv">
										<label for="otp" class="col-lg-4 control-label" style="margin-left: 0%; text-align: left;">Password</label>
										<div class="col-lg-8">
											<input type="text" class="form-control" id="password"
												name="password"> <label class="help-inline"
												id="passwordError"></label>
										</div>

									</div>
									<div class="row">
									<label class="help-inline"
												id="verificationFormError"></label>
									<div class="col-lg-4 text-right">
								        
										<input name="btnVerifyOTP" type="button" value="Verify" class="btn btn-action" id="btnVerifyOTP"/> 
									</div>
										
									
										
									</div>

									<hr>


								
								
							</form>
							</div>
							</c:otherwise>
							</c:choose>
							</div>
								
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							
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