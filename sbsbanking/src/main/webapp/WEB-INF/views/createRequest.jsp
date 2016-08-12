<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
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

<script src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.js"></script>
 <script src="${pageContext.request.contextPath}/resources/js/respond.min.js"></script>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/gt_favicon.png">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/leftSideBar.css"> 
<link rel="stylesheet" media="screen" href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">

<!-- Custom styles for our template -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" media="screen">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">

<script>
 $(document)
.ready(
		function() {
			$("#reqType").blur(function(){
				
				if($("#reqType").val().indexOf("TRANSACTION")>=0)
						{
					$("#transactionDiv").css("display", "block");
						}
				
				if($("#reqType").val()=='CREATE_ACCOUNT')
				{
				
				}
			})	
		});
 
 $(document)
 .ready(
 		function() {
 			$("#reqType").blur(function(){
 				
 				if($("#reqType").val()=="CREATE_ACCOUNT")
 						{
 					$("#accountTypeDiv").css("display", "block");
 						}
 			})	
 		});
 
function reqSubmitFunc(){
	$("#btnSubmit").click(function() {
		$('#reqFormError').hide();
		if($('input:input[type=radio]:checked').size()==0 || $("#username").val().trim().length==0 || $("#reqType").val()=="Select one")
			{
			
			$('#reqFormError').text("Please enter mandatory fields");
		  	  $('#reqFormError').show();
			}
		else
			{
			if($('#transactionID').css('display') == 'block')
				
			{
			if($("#transactionID").val().trim().length==0)
				{
				$('#reqFormError').text("Please enter mandatory fields");
			  	  $('#reqFormError').show();
				}
			else
			{
				$('#reqFormError').hide();
				  document.getElementById("requestForm").submit();
			}
			}
			else if($('#accountTypeDiv').css('display') == 'block')
				{
				if($("#accountType").val().trim().length==0)
				{
				$('#reqFormError').text("Please enter mandatory fields");
			  	  $('#reqFormError').show();
				}
			else
			{
				$('#reqFormError').hide();
				  document.getElementById("requestForm").submit();
			}
				}
			
			else
				{
				$('#reqFormError').hide();
				 document.getElementById("requestForm").submit();
				}
			}
			}); 
			};
			
			
			
	/* $(document)
	.ready(
			function() {

				$
						.ajax({
							url : "${pageContext.request.contextPath}/getRequestType",
							type : "GET",
							dataType : "json",
							success : function(data) {
								$.each(data, function(key, value) {

									String(value.reuest_type).replace("_", " ");
									$("#reqType").append(
											"<option value="+value.reuest_type+">"
													+ String(value.reuest_type).replace("_", " ")
													+ "</option>");
									
								});
							}
						});
				
			}); */
	/* 		$(document)
			.ready(
					function() {
						alert("i came inside");
			$.getJSON("${pageContext.request.contextPath}/resources/json/RequestType", function(RequestType){
				alert("yar");
                $.each(RequestType,function(key,value)
    	                {
    	                	alert(value.name);
    	                	$("#reqType").append(
    								"<option value="+value.name+">"
    										+ String(value.name).replace("_", " ")
    										+ "</option>");
    	                });  
				});
					}); */
/* $(document)
			.ready(
					function() {
						alert("i came inside");
			$.ajax({
	            type: "GET",
	            url:"${pageContext.request.contextPath}/resources/json/RequestType.json",
	            dataType: "json",
	            success: function (data) {
	            	alert("yes");
	                $.each(RequestType,function(key,value)
	                {
	                	alert(value.name);
	                	$("#reqType").append(
								"<option value="+value.name+">"
										+ String(value.name).replace("_", " ")
										+ "</option>");
	                });  
	                }
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
				<div class="row">
				<%-- <sec:authorize access="hasRole('ROLE_USER')"> --%>
					<jsp:include page="leftSideBar.jsp"></jsp:include>
				<%-- </sec:authorize> --%>
				
				<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2"  style="margin-left: 0%">
					<div class="panel panel-default">
						<div class="panel-body">
							<!-- 	<h3 class="thin text-center">Sign in to your account</h3> -->
							<p></p>
							<hr>
							<c:if test="${empty successRequestMessage}">
			
							<form:form name='requestForm' method='POST' class="form-horizontal" id="requestForm" action="${pageContext.request.contextPath}/submitRequest" commandName="requestObj">

								<div class="form-group">
									<label for="choice" class="col-lg-4 control-label"
										style="margin-left: 0%; text-align: left;">Request Receiver*:</label>
									<div class="col-lg-8">
										<label for="user" class="col-lg-8 control-label">Customer</label>
											<form:select class="form-control"  id="reqType" name="reqType" path="requestObj.requestFor">
											<option value="Select one">Select one</option>
											
											<option value="ROLE_CUSTOMER">VIEW TRANSACTION</option>
											<option value="ROLE_ADMINISTRATOR">MODIFY TRANSACTION</option>
											
										</form:select>
<!-- 											 <label -->
<%-- 											for="sysAdmin" class="col-lg-8 control-label">System Administrator</label><form:radiobutton value="sysAdmin" name="choiceRadio" --%>
<%-- 											id="choiceRadio" class="col-lg-3" path="requestObj.requestFor"/> --%>

									</div>

								</div>
								<div class="form-group">
									<label class="col-lg-4 control-label" style="margin-left: 0%; text-align: left;">Request Type*:</label>
									<div class="col-lg-8">
										<form:select class="form-control"  id="reqType" name="reqType" path="requestObj.requestType">
											<option value="Select one">Select one</option>
											<sec:authorize access="hasAnyRole('ROLE_MANAGER', 'ROLE_ADMINISTRATOR')">
											<option value="CREATE_ACCOUNT">CREATE ACCOUNT</option></sec:authorize>
											<option value="VIEW_ACCOUNT">VIEW ACCOUNT</option>
											<option value="MODIFY_ACCOUNT">MODIFY ACCOUNT</option>
											<sec:authorize access="hasAnyRole('ROLE_MANAGER', 'ROLE_ADMINISTRATOR')">
											<option value="DELETE_ACCOUNT">DELETE ACCOUNT</option></sec:authorize>
											<option value="VIEW_TRANSACTION">VIEW TRANSACTION</option>
											<option value="MODIFY_TRANSACTION">MODIFY TRANSACTION</option>
											<option value="DELETE_TRANSACTION">DELETE TRANSACTION</option>
										</form:select>
										<label class="help-inline" id="reqTypeError" style="margin-left: 0%; text-align: left;" path=""></label>
									
									</div>
								</div>
									<div class="form-group">
									<label for="username" class="col-lg-4 control-label" style="margin-left: 0%; text-align: left;">Customer UserName*</label>
									<div class="col-lg-8">
										<form:input type="text" class="form-control" id="username" name="username" placeholder="User Name" path="requestObj.reqUsername"/>
										<label class="help-inline" id="usernameError" path="" ></label>
										<!-- <span class="help-inline">Username is atleast 6 characters long</span>
											<span class="help-inline">Username can only consist of a-z,A-Z,.,_</span> -->
									</div>

								</div>

										<div class="form-group">
											<label for="accountNum" class="col-lg-4 control-label"
												style="margin-left: 0%; text-align: left;">Account
												Number</label>
											<div class="col-lg-8">
												<form:input type="text" class="form-control" id="accountNum"
													name="accountNum" path="requestObj.reqAccountNum"/> <label class="help-inline"
													id="accountNumError"></label>

											</div>

										</div>
										<!-- <div class="form-group" style="display: none;"
											id="accountTypeDiv">
											<label for="accountTypeDiv" class="col-lg-4 control-label"
												style="margin-left: 0%; text-align: left;">Account Type*:</label>
											<div class="col-lg-8">
											
										<select class="form-control"  id="accountType" name="accountType">
										
										<option value="CHECKINGS">CHECKINGS</option>
										<option value="SAVINGS">SAVINGS</option>
										</select>
												<label class="help-inline"
													id="accountTypeError"></label>
											</div>

										</div> -->
										<div class="form-group" style="display:none;" id="transactionDiv">
									<label for="transactionId" class="col-lg-4 control-label"  style="margin-left: 0%; text-align: left;">Transaction Id*:</label>
									<div class="col-lg-8">
									 	<form:input type="text" class="form-control" id="transactionId" name="transactionId" path="requestObj.reqTID" />  
										<label class="help-inline" id="transactionIdError"></label>
									</div>

								</div>

								<hr>

								<div class="row">

										<div class="row" style="text-align: center">
											<label class="help-inline" id="reqFormError"></label>
											<input name="btnSubmit" type="button" value="Submit"
												class="btn btn-action" onclick="reqSubmitFunc();" id="btnSubmit"/> <input
												name="btnCancel" type="button" value="Cancel"
												class="btn btn-action" onclick="login()" />
										</div>
									</div>
								
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							</form:form>
							</c:if>
							<c:if test="${not empty successRequestMessage}">
							<label for="successMessage" class="col-lg-10 control-label"
										style="margin-left: 0%; text-align: left;">${successRequestMessage}</label>
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