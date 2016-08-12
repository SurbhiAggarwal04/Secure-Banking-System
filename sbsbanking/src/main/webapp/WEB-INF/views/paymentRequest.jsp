<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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
<style type="text/css">
    .help-inline {
        color: #FF0000;
    }
</style>
<!-- Custom styles for our template -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" media="screen">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
<script type="text/javascript">
$(document)
.ready(
		function() {
			$("#approve")
					.click(
							function() {

								if ($("#randomNumber").val()
										.trim().length == 0
										|| $("#accountNum")
												.val().trim().length ==0
										|| $("#amount")
												.val().trim().length == 0) {
									$('#paymentPage')
											.text(
													"Plesae Enter all mandatory fields");
									$('#paymentPage')
											.show();
									
								} else {
									$('#paymentPage')
											.hide();
									document.getElementById("paymentRequest").submit();
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
	<form:form class="form-horizontal" id='paymentRequest' method="post" action="paymentRequestform" modelAttribute="form">
<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />
		<div class="row">
					
				
		<div class="control-group" style="azimuth:center">
				<form:label class="control-label" path="map['AccountNumber']">AccountNum</form:label>
				<form:input class="controls" path="map['AccountNumber']" name="accountNum" id="accountNum"/>
				</div>
				<br>
				<br>
			<div class="control-group" style="azimuth:center">
				<form:label class="control-label" path="map['Amount']">Amount</form:label>
				<form:input class="controls" path="map['Amount']" name="amount" id="amount"/>
				</div>
				<br>
				<br>
			</div>
				<div class="control-group" style="azimuth:center">
				<form:label class="control-label" path="map['RandomNumber']">Random Number</form:label>
				<form:input class="controls" path="map['RandomNumber']" name="randomNumber" id="randomNumber"/>
				</div>
				<br>
				<br>
			
			<form:label class="help-inline" id="paymentPage" path=""></form:label>
			<input id="approve" type="button" name="approve"
				value="Approve" />
				
				</form:form>
			
			
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