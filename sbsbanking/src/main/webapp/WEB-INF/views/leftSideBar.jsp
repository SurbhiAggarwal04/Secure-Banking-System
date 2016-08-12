<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
@media (min-width: 768px) {
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

<%--    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>

    <script src="${pageContext.request.contextPath}/resources/js/respond.min.js"></script>
   <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script> 
  <script src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.js"></script>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/gt_favicon.png">
	
 	<link rel="stylesheet" media="screen" href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700"> 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"> 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">

	<!-- Custom styles for our template -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" media="screen" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css"> --%>
</head>
<body>
<!-- External User left side menu -->
<sec:authorize access="hasAnyRole('ROLE_CUSTOMER', 'ROLE_MERCHANT')">
<%-- <sec:authorize access="hasRole('ROLE_CUSTOMER')"> --%>
 <div class="col-sm-3">
    <div class="sidebar-nav">
      <div class="navbar navbar-default" role="navigation">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <span class="visible-xs navbar-brand">Sidebar menu</span>
        </div>
        <div class="navbar-collapse collapse sidebar-navbar-collapse">
          <ul class="nav navbar-nav">
           <li class="active"><a href="${pageContext.request.contextPath}/getAccount" class="form-horizontal">Account</a></li>
                     <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Profile <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="${pageContext.request.contextPath}/getProfile">View Profile</a></li>
                <li class="divider"></li>
                 <li><a href="${pageContext.request.contextPath}/getUpdateProfile">Update Profile</a></li>
               
              </ul>
            </li>
            
             <li><a href="${pageContext.request.contextPath}/viewRequests">View Requests</a></li>
                 <li><a href="${pageContext.request.contextPath}/requestedPayments">Payments Requests</a></li>
       
           
            <li><a href="${pageContext.request.contextPath}/credit">Credit</a></li>
 
            <li><a href="#">Debit</a></li>
            <li><a href="#">Transfer</a></li>
            <sec:authorize access="hasRole('ROLE_MERCHANT')">
             <li><a href="${pageContext.request.contextPath}/paymentRequest">Requesting Payment</a></li></sec:authorize>
            <li><a href="${pageContext.request.contextPath}/getAllStatement">Statement</a></li>
            <li><a href="#">Customer Support</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>
  </div>
  <!-- External User left side menu -->
  
  <!-- Regular Employee left side menu -->
  </sec:authorize>
  <sec:authorize access="hasRole('ROLE_EMPLOYEE')">
 <div class="col-sm-3">
    <div class="sidebar-nav">
      <div class="navbar navbar-default" role="navigation">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <span class="visible-xs navbar-brand">Sidebar menu</span>
        </div>
        <div class="navbar-collapse collapse sidebar-navbar-collapse">
          <ul class="nav navbar-nav">
                     <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Profile <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="#">View Profile</a></li>
                <li class="divider"></li>
                 <li><a href="#">Update Profile</a></li>
               
              </ul>
            </li>
             <%--  <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Requests <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="${pageContext.request.contextPath}/viewRequests">View Requests</a></li>
                <li class="divider"></li>
                 <li><a href="${pageContext.request.contextPath}/raiseRequest">New Request</a></li>
               
              </ul>
            </li> --%>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Requests <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="${pageContext.request.contextPath}/viewExternalTransactions">View External Transactions</a></li>
                <li class="divider"></li>
                 <li><a href="${pageContext.request.contextPath}/viewInternalRequests">View Internal Requests</a></li>
                  <li><a href="${pageContext.request.contextPath}/raiseRequest">New Request</a></li>
               
              </ul>
            </li>

							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown">Accounts<b class="caret"></b></a>
								<ul class="dropdown-menu">
									<li><a href="#">View Accounts</a></li>
									<li class="divider"></li>
									<li><a href="#">Modify Accounts</a></li>

								</ul></li>


							<li><a href="#">Notifications</a></li>
 
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>
  </div>
  </sec:authorize>
  <!-- Regular Employee left side menu -->
  
  
   <!-- System Manager left side menu -->
  <sec:authorize access="hasRole('ROLE_MANAGER')">
 <div class="col-sm-3">
    <div class="sidebar-nav">
      <div class="navbar navbar-default" role="navigation">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <span class="visible-xs navbar-brand">Sidebar menu</span>
        </div>
        <div class="navbar-collapse collapse sidebar-navbar-collapse">
        <ul class="nav navbar-nav">
                     <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Profile <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="#">View Profile</a></li>
                <li class="divider"></li>
                 <li><a href="#">Update Profile</a></li>
               
              </ul>
            </li>
              <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Requests <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="${pageContext.request.contextPath}/viewExternalTransactions">View External Transactions</a></li>
                <li class="divider"></li>
                 <li><a href="${pageContext.request.contextPath}/viewInternalRequests">View Internal Requests</a></li>
                  <li><a href="${pageContext.request.contextPath}/raiseRequest">New Request</a></li>
               
              </ul>
            </li>


							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown">Accounts<b class="caret"></b></a>
								<ul class="dropdown-menu">
									<li><a href="#">View Accounts</a></li>
									<li class="divider"></li>
									<li><a href="#">Add Accounts</a></li>
									<li class="divider"></li>
									<li><a href="#">Modify Accounts</a></li>
									<li class="divider"></li>
                                    <li><a href="#">Delete Accounts</a></li>
									
								</ul></li>


							<li><a href="#">Notifications</a></li>
 
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>
  </div>
  </sec:authorize>
    <!-- System Manager left side menu -->
    
      <!-- System Adminstrator left side menu -->
 <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
 <div class="col-sm-3">
    <div class="sidebar-nav">
      <div class="navbar navbar-default" role="navigation">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <span class="visible-xs navbar-brand">Sidebar menu</span>
        </div>
        <div class="navbar-collapse collapse sidebar-navbar-collapse">
        <ul class="nav navbar-nav">
                     <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Profile <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="#">View Profile</a></li>
                <li class="divider"></li>
                 <li><a href="#">Update Profile</a></li>
               
              </ul>
            </li>
              <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Requests <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="${pageContext.request.contextPath}/viewRequests">View Requests</a></li>
                <li class="divider"></li>
                 <li><a href="#">New Request</a></li>
               
              </ul>
            </li>

							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown">Accounts<b class="caret"></b></a>
								<ul class="dropdown-menu">
									<li><a href="#">View Accounts</a></li>
									<li class="divider"></li>
									<li><a href="#">Add Accounts</a></li>
									<li class="divider"></li>
									<li><a href="#">Modify Accounts</a></li>
									<li class="divider"></li>
                                    <li><a href="#">Delete Accounts</a></li>
									
								</ul></li>
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown">System Log File <b class="caret"></b></a>
								<!-- <ul class="dropdown-menu"> -->
									<li><a href="${pageContext.request.contextPath}/admin/viewLogFile">View File</a></li>
									<!-- <li class="divider"></li>
									<li><a href="#">Modify</a></li>

								</ul></li> -->


							<li><a href="#">Notifications</a></li>
 
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>
  </div>
  </sec:authorize>
    <!-- System Adminstrator left side menu -->
</body>
</html>