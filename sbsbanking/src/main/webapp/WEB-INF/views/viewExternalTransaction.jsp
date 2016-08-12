<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
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
<script type="text/javascript">
	$(document).ready(
			function() {
				$('#approve').click(
						function() {
							alert("yes");
							$('#notificationsform').attr("action",
									"approveExternalTransaction");
							document.getElementById("notificationsform")
									.submit();

						});
			});
	$(document).ready(
			function() {
				$('#reject').click(
						function() {
							$('#notificationsform').attr("action",
									"rejectExternalTransaction");
							document.getElementById("notificationsform")
									.submit();

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
				class="page-header"> </header> <%-- <sec:authorize access="hasRole('ROLE_USER')"> --%>
			<div class="row">
				<jsp:include page="leftSideBar.jsp"></jsp:include>
				<div class="col-sm-9">

					<form:form class="form-horizontal" id="notificationsform" action=""
						method="post">
						<c:choose>
							<c:when test="${not empty etRequestList}">
								<c:forEach var="transaction" items="${etRequestList}">
									<table class="table">
										<thead>
											<tr>
												<th>Select</th>
												<th>Transaction Type</th>
												<th>Transaction From</th>
												<th>Transaction Time</th>
												<th>Amount</th>
											</tr>
										</thead>
										<tbody>
											<tr class="success">
												<td><input type="radio"
													value="${transaction.transactionId}" id="transId"
													name="transId" /></td>
												<%-- <td>${account.accountNumber}</td> --%>
												<td>${transaction.transactionType}</td>
												<td>${transaction.transactionFromAccountNum}</td>
												<%-- <td>${account.balance}</td> --%>
												<td>${transaction.transactionUpdateTimestamp}</td>
												<%-- <td>${request.requestContent}</td>--%>
												<td><fmt:formatNumber value="${transaction.transactionAmount}"
														type="currency" />
											</tr>
										</tbody>
									</table>

								</c:forEach>
								<input type="button" id="approve" value="Approve" />
								<input type="button" id="reject" value="Reject" />
							</c:when>
							<c:otherwise>
									No Requests Found
							</c:otherwise>
						</c:choose>
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form:form>
				</div>
			</div>
			<%-- </sec:authorize> --%> </article>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>