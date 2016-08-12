<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<html>
<head>
<link href="<c:url value="/resources/bootstrap.css" />" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<script src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.js"></script>

<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/gt_favicon.png">

<link rel="stylesheet" media="screen" href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">

<!-- Custom styles for our template -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" media="screen">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">

<title><spring:message code="message.updatePassword"></spring:message></title>
<script type="text/javascript">
	function savePass() {
		var pass = $("#password").val();
		var valid = pass == $("#passConfirm").val();
		if (!valid) {
			$("#error").show();
			return;
		} else {
		alert();
			$("#error").hide();
			document.getElementById("updatePasswordForm").submit();
		}
	}
</script>
</head>
<body>
	<div class="container">
		<div class="span12">
			<h1 style="font-family: cursive;">
				<spring:message code="message.resetYourPassword"></spring:message>
			</h1>
			<div>
				<form method="post" name="updatePasswordForm" id="updatePasswordForm" action="${pageContext.request.contextPath}/savePassword">
					<br> <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> <label> <spring:message
							code="label.enterpassword"></spring:message>
					</label> <input id="password" name="password" type="password" value="" /> <label> <spring:message
							code="label.user.confirmPass"></spring:message>
					</label> <input id="passConfirm" type="password" value="" /> <span id="error" class="alert alert-error" style="display: none">
						<spring:message code="PasswordMatches.user"></spring:message>
					</span> <br> <br>
					<button type="button" onclick="savePass()">
						<spring:message code="message.updatePassword"></spring:message>
					</button>
				</form>
			</div>

		</div>
	</div>


</body>
</html>