<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<meta charset="utf-8">

<%-- <script src="${pageContext.request.contextPath}/resources/js/vkboardc.js"></script> --%>
<script src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/login.js"></script>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/gt_favicon.png">

<link rel="stylesheet" media="screen" href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">

<!-- Custom styles for our template -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" media="screen">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">

<script>
	function blockFirebug() {
		if (!("console" in window) || !("firebug" in console)) {
			var names = [ "log", "debug", "info", "warn", "error", "assert",
					"dir", "dirxml", "group", "groupEnd", "time", "timeEnd",
					"count", "trace", "profile", "profileEnd" ];
			window.console = {};
			for (var i = 0; i < names.length; ++i)
				window.console[names[i]] = function() {
				};
		}
	}
	function keyb_callback(char) {
		var text = document.getElementById("username"), val = text.value;

		switch (ch) {
		case "BackSpace":
			var min = (val.charCodeAt(val.length - 1) == 10) ? 2 : 1;
			text.value = val.substr(0, val.length - min);
			break;

		case "Enter":
			text.value += "\n";
			break;

		default:
			text.value += ch;
		}
	}
</script>

</head>

<body onload="blockFirebug();">
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
				<header class="page-header">
					<h1 class="page-title">Sign in</h1>
				</header>

				<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
					<div class="panel panel-default">
						<div class="panel-body">
							<!-- 	<h3 class="thin text-center">Sign in to your account</h3> -->
							<p></p>
							<hr>
							<form name='loginForm' method='POST' class="form-horizontal" id="loginForm" action="<c:url value='/login' />">

								<c:if test="${not empty error}">
									<div style="color: red;">${error}</div>
								</c:if>
								<c:if test="${not empty msg}">
									<div style="color: green;">${msg}</div>
								</c:if>
								<c:if test="${not empty message}">
									<div style="color: red;">${message}</div>	
								</c:if>
								<c:if test="${not empty otpMessage}">
									<div style="color: red;">${otpMessage}</div>	
								</c:if>
								<c:if test="${not empty locked}">
									<div style="color: red;">${locked}</div>	
								</c:if>
								<div class="form-group">
									<label for="username" class="col-lg-2 control-label">UserName</label>
									<div class="col-lg-10">
										<input type="text" class="form-control" id="username" name="username" placeholder="User Name" onblur="usernameCheck()">
										<label class="help-inline" id="usernameError"></label>
									</div>
								</div>

								<div class="form-group">
									<label for="password" class="col-lg-2 control-label">Password</label>
									<div class="col-lg-10">
										<input type="password" class="form-control" id="password" name="password" placeholder="Password"
											onblur="passwordCheck()"> <label class="help-inline" id="passwordError"></label>
									</div>

								</div>

								<hr>

								<div class="row">
									<div class="col-lg-8">
										<b><a href="${pageContext.request.contextPath}/forgotPassword">Forgot password?</a></b>
									</div>
									<div class="col-lg-4 text-right">

										<input name="btnSubmit" type="button" value="Sign in" class="btn btn-action" onclick="login()" />
									</div>
								</div>
								<div class="row">
									<div class="col-lg-8">
										<b>Don't have an account yet?<a href="${pageContext.request.contextPath}/signup">Register</a></b>
									</div>

								</div>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							</form>
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