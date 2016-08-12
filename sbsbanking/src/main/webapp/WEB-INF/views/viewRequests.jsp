<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
@media ( min-width : 768px) {
	.sidebar-nav .navbar .navbar-collapse {
		padding: 0;
		max-height: none;
	}
	.sidebar-nav .navbar ul {
		float: none;
	}
	.sidebar-nav .navbar ul:not {
		display: block;
	}
	.sidebar-nav .navbar li {
		float: none;
		display: block;
	}
	.sidebar-nav .navbar li a {
		font-size: 16px;
		padding-top: 12px;
		padding-bottom: 12px;
	}
}
</style>

<meta charset="utf-8">

<script
	src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/js/respond.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.js"></script>

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

<!-- <script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#account")
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
					}); -->
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#approve').click(function() {
			alert("yes");
			"src/main/webapp/WEB-INF/views/viewRequests.jsp"
			$('#notificationsform').attr("action", "approvenotification");
			document.getElementById("notificationsform").submit();

		});
		$('#reject').click(function() {
			$('#notificationsform').attr("action", "rejectnotifications");
			document.getElementById("notificationsform").submit();

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
			<label for="username" class="col-lg-10 control-label"
				style="margin-left: 0%; text-align: right; font-size: large;">Welcome
				User : ${pageContext.request.userPrincipal.name}</label>

		</c:if>
		<div class="row">

			<!-- Article main content -->
			<article class="col-xs-12 maincontent"> <header
				class="page-header">
			<div class="row">
				<jsp:include page="leftSideBar.jsp"></jsp:include>
				<div class="col-sm-9">

					<%-- <c:if test="${empty viewRequestSucessMessage}"> --%>
					<form:form class="form-horizontal" id="notificationsform" action=""
						method="post" modelAttribute="form">
						<c:if test="${not empty requestlist}">
							<c:forEach var="request" items="${requestlist}">
								<table class="table">
									<thead>
										<tr>
											<th></th>
											<th>Request Type</th>
											<th>Request From</th>
											<th>Requested Time</th>
											<th>Requested Content</th>
										</tr>
									</thead>

									<tbody>
										<tr class="success">
											<td><form:radiobutton path="map['requestId']"
													value="${request.requestId}" /></td>
											<%-- <td>${account.accountNumber}</td> --%>
											<td>${request.requestType}</td>
											<td>${request.requestFromAccountNum}</td>
											<td>${request.creationTimeStamp}</td>
											<td>${request.requestContent}</td>
										</tr>
									</tbody>
								</table>
							</c:forEach>
						</c:if>
						<c:if test="${not empty transactionslist}">
							<c:forEach var="transaction" items="${transactionslist}">
								<table class="table">
									<thead>
										<tr>
											<th></th>
											<th>Transaction Type</th>
											<th>Transaction From</th>
											<th>Transaction Time</th>
											<th>Amount</th>
										</tr>
									</thead>
									<tbody>
										<tr class="success">
											<td><form:radiobutton path="map['transactionId']"
													value="${transaction.transactionId}" /></td>
											<%-- <td>${account.accountNumber}</td> --%>
											<td>${transaction.transactionType}</td>
											<td>${transaction.transactionFromAccountNum}</td>
											<%-- <td>${account.balance}</td> --%>
											<td>${transaction.tranUpdateTS}</td>
											<%-- <td>${request.requestContent}</td>--%>


										</tr>


									</tbody>

								</table>

							</c:forEach>
						</c:if>
						<input type="button" id="approve" value="Approve" />
						<input type="button" id="reject" value="Reject" />
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form:form>
					<%-- </c:if> --%>

					<%-- <c:if test="${not empty requestlist}">--%>
					<%-- 
					<c:if test="${not empty approveFailure}">
						<form:form class="form-horizontal" id="notificationsform"
							action="" method="post" modelAttribute="form">

							<c:forEach var="request" items="${requestlist}">

								<table class="table">


									<thead>
										<tr>
											<th></th>
											<th>Request Type</th>
											<th>Request From</th>
											<th>Requested Time</th>
											<th>Requested Content</th>
										</tr>
									</thead>

									<tbody>
										<tr class="success">
											<td><form:radiobutton path="map['requestId']"
													value="${request.requestId}" /></td>
											<td>${account.accountNumber}</td>
											<td>${request.requestType}</td>
											<td>${request.requestFromAccountNum}</td>
											<td>${account.balance}</td>
											<td>${request.creationTimeStamp}</td>
											<td>${request.requestContent}</td>



										</tr>

									</tbody>
								</table>

							</c:forEach>

							<c:forEach var="transaction" items="${transactionslist}">
								<table class="table">
									<thead>
										<tr>
											<th></th>
											<th>Transaction Type</th>
											<th>Transaction From</th>
											<th>Transaction Time</th>
											<th>Amount</th>
										</tr>
									</thead>




									<tbody>
										<tr class="success">
											<td><form:radiobutton path="map['transactionId']"
													value="${transaction.transactionId}" /></td>
											<td>${account.accountNumber}</td>
											<td>${transaction.transactionType}</td>
											<td>${transaction.transactionFromAccountNum}</td>
											<td>${account.balance}</td>
											<td>${transaction.tranUpdateTS}</td>
											<td>${request.requestContent}</td>


										</tr>


									</tbody>

								</table>

							</c:forEach>
							<input type="button" id="approve" value="Approve" />
							<input type="button" id="reject" value="Reject" />
						</form:form>
						<label class="help-inline" id="failure" style="color: red;">${approveFailure }</label>
					</c:if>
 --%>
					<c:if test="${not empty approveFailure}">
						<label class="help-inline" id="failure" style="color: red;">${approveFailure }</label>
					</c:if>
					<c:if test="${not empty viewRequestSucessMessage}">
						<label class="help-inline" id="failure" style="color: red;">${viewRequestSucessMessage }</label>
					</c:if>
				</div>
			</div>
			<%-- </sec:authorize> --%></article>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>