<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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


<script
	src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/forgotPassword.js"></script>
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
			$("#step1").addClass('shade');
			$("#step1").css({
				backgroundColor : 'black'
			});

		});
	});
	
	$(document).ready(function() {
		$("#verifyAnswers").click(function() {
			alert($("#answer1").val());
			if($("#answer1").val().trim().length==0 || $("#answer2").val().trim().length==0)
				{
				$('#answerError').text("Answer required");
	        	$('#answerError').show();
				}
			else
				{
				document.getElementById("forgotPasswordAnswer").submit();
				}
			
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
							<form name='forgotPasswordAnswer' id="forgotPasswordAnswer"
								action="${pageContext.request.contextPath}/verifyAnswersAndGenerateOTP"
								method="post">
								<input type="hidden" name="username" value="${username}">
								<c:if test="${not empty questionList}">
									<c:set var="count" value="1" scope="page" />
									<c:forEach items="${questionList}" var="questions">
										<tr>
											<td><c:out value="${questions.qText}" /></td>
											</br>
											<td><input id="answer${count}" type="text"
												name="answer${count}" /></td>
											</br>
											<c:set var="count" value="${count + 1}" scope="page" />
										</tr>
										
									</c:forEach>
									<div class="row" style="text-align: center">
										<div class="col-lg-4 text-right" id="generateOTP">
										<label class="help-inline" id="answerError"></label>
											<input type="button" id="verifyAnswers" name="verifyAnswers"
												value="Verify" class="btn
											btn-action" />
										</div>
									</div>
								</c:if>
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
							</form>
							<c:if test="${not empty answersIncorrectError}">
								<label class="col-lg-12 control-label" style="color: red">${answersIncorrectError}</label>
								</c:if>
								
								<c:if test="${not empty emptyAnswers}">
								<label class="col-lg-12 control-label" style="color: red">${emptyAnswers}</label>
								</c:if>
							<hr>
							<label class="help-inline" id="forgotPasswordError"></label>
						</div>
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