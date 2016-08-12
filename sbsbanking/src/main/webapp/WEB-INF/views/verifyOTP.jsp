<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<html>
<!DOCTYPE html>
<html lang="en">
<head>

<style type="text/css">
.help-inline {
	color: #FF0000;
}
</style>

<meta charset="utf-8">


<script src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.js"></script>

<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/gt_favicon.png">

<link rel="stylesheet" media="screen" href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">

<!-- Custom styles for our template -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" media="screen">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">

<script type="text/javascript">
	$(document).ready(function() {

		$("#step2").addClass('shade');
		$("#step2").css({
			backgroundColor : 'black'
		});

	});

// 	$(document).ready(function() {
// 		$("#btnResendOTP").click(function() {
// 			alert("hiiii");
// 			$.ajax({
// 				url : "${pageContext.request.contextPath}/getOTP",
// 				type : "GET",
// 				success : function(data) {
// 				},
// 				error : function(data) {
// 				}
// 			});
// 		});
// 	});

	$(document).ready(function() {
		$("#btnResendOTP").click(function() {
			alert("yes");
			$('#verificationForm').attr('action', '/internetbanking/getOTP');
			alert("chnge action");
			$("#verificationForm").submit();

		});
	});
	$(document)
			.ready(
					function() {
						$("#verificationCode")
								.blur(
										function() {

											$('#verificationCode').parent()
													.removeClass("has-error");
											if ($("#verificationCode").val()
													.trim().length == 0
													|| $("#verificationCode")
															.val().trim().length < 4
													|| $("#verificationCode")
															.val().trim().length > 4) {
												$('#verificationCodeError')
														.text(
																"Valid OTP Required");
												$('#verificationCodeError')
														.show();
												$('#verificationCode').parent()
														.addClass("has-error");
											} else {
												$('#verificationCodeError')
														.hide();
											}
										});
					});
	$(document)
			.ready(
					function() {
						$("#verificationCode")
								.blur(
										function() {

											$('#verificationCode').parent()
													.removeClass("has-error");
											if ($("#verificationCode").val()
													.trim().length == 0
													|| $("#verificationCode")
															.val().trim().length < 4
													|| $("#verificationCode")
															.val().trim().length > 4) {
												$('#verificationCodeError')
														.text(
																"Valid OTP Required");
												$('#verificationCodeError')
														.show();
												$('#verificationCode').parent()
														.addClass("has-error");
											} else {
												$('#verificationCodeError')
														.hide();
											}

										});
					});
	$(document).ready(function() {
		$("#password").blur(function() {
			$('#password').parent().removeClass("has-error");
			if ($("#password").val().trim().length == 0) {
				$('#passwordError').text("Password Required");
				$('#passwordError').show();
				$('#password').parent().addClass("has-error");
			} else {
				$('#passwordError').hide();
			}

		});
	});
</script>
</head>

<body>
	<!-- Fixed navbar -->
	<!-- Fixed navbar -->
	<jsp:include page="header.jsp"></jsp:include>
	<!-- /.navbar -->

	<header id="head" class="secondary"></header>
	<!-- container -->
	<div class="container">
		<div id="keyboard"></div>

		<ol class="breadcrumb">
			<li><a href="index.html">Home</a></li>
			<li class="active">User access</li>
		</ol>

		<div class="row">

			<!-- Article main content -->
			<article class="col-xs-12 maincontent">
				<header id="head" class="secondary"></header>
				<div class="row">
					<ul>
						<li class="shade" id="step1"><a href="#">Step 1</a></li>
						<li><a href="#" id="step2">Step 2</a></li>
					</ul>
				</div>
				<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
					<div class="panel panel-default">
						<div class="panel-body">
							<p></p>
							<hr>
							<p>
								<c:if test="${not empty lastAttemptWarning}">
									<div style="color: red;">${lastAttemptWarning}</div>
								</c:if>
								<c:if test="${not empty otpAttempts}">
									<div style="color: red;">${otpAttempts}</div>
								</c:if>
							</p>
							<hr>

							<div id="verificationDiv">
								<label class="help-inline" id="otpSuccess" style="color: green;">Please enter the OTP sent to you on email</label>
								<form id='verificationForm' name='verificationForm' method='POST' class="form-horizontal"
									action="<c:url value='/verifyOTPAndResetPassword' />">
									<div class="form-group" id="otpDiv">
										<label for="otp" class="col-lg-4 control-label" style="margin-left: 0%; text-align: left;">Verification Code</label>
										<div class="col-lg-8">
											<input type="text" class="form-control" id="verificationCode" name="verificationCode"> <label
												class="help-inline" id="verificationCodeError"></label>
										</div>

									</div>
									<!-- <div class="form-group" id="passwordDiv" style="visibility: hidden;"> -->
									<div class="row">
										<label class="help-inline" id="verificationFormError"></label>
										<div class="col-lg-4 text-right">
											<input name="btnVerifyOTP" type="submit" value="Verify" class="btn btn-action" id="btnVerifyOTP" />
										</div>
									</div>
									<c:if test="${not empty otpResend and otpResend eq true}">
										<div class="row">
											<label class="help-inline" id="verificationFormError"></label>
											<div class="col-lg-4 text-right">
												<input name="btnResend" type="button" value="Resend OTP" class="btn btn-action" id="btnResendOTP" />
											</div>
										</div>
									</c:if>
									<hr>
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								</form>
							</div>
						</div>
					</div>

				</div>
			</article>
			<!-- /Article -->

		</div>
	</div>
	<!-- /container -->
	<jsp:include page="footer.jsp"></jsp:include>
	<jsp:include page="dashboard.jsp"></jsp:include>
</body>
</html>
</html>