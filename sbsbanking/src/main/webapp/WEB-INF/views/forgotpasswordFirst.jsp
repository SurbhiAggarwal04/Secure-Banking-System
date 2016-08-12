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
		
		$("#step1").addClass('shade');
		$("#step1").css({
			backgroundColor : 'black'
		});

	});
	$(document).ready(function() {
		$("#username").click(function() {
			$("#error").css("display", "none");
			
			$("#step1").addClass('shade');
			$("#step1").css({
				backgroundColor : 'black'
			});

		});
	});

	
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
                       
                         
							<form name='forgotPassword' id="forgotPassword" action="${pageContext.request.contextPath}/getUserSecQuestions" method="post">
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
											<input type="submit" id="verifyUsername" name="verify" value="verify" class="btn btn-action" />
										</div>
									</div>
								</div>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								</form>

								<hr>

								<label class="help-inline" id="forgotPasswordError"></label>
								</form>
								<c:if test="${not empty securityQuestionfetchError}">
								<label class="col-lg-12 control-label" style="color: red">${securityQuestionfetchError}</label>
								</c:if>
								<c:if test="${not empty usernameFetchError}">
								<label  class="col-lg-12 control-label" style="color: red">${usernameFetchError}</label>
								</c:if>
														
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






</body>
</html>
</html>