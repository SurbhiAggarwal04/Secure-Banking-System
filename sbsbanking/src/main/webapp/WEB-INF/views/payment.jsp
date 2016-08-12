<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false" %>
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

    <meta charset="utf-8">

    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>

    <script src="${pageContext.request.contextPath}/resources/js/respond.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.js"></script>

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/gt_favicon.png">

    <link rel="stylesheet" media="screen" href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">

    <!-- Custom styles for our template -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" media="screen">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">


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
               style="margin-left: 0%; text-align: right; font-size: large;">Welcome User
            : ${pageContext.request.userPrincipal.name}</label>

    </c:if>
    <div class="row">

        <!-- Article main content -->
        <article class="col-xs-12 maincontent">
            <header class="page-header">

                <!-- <h1 class="page-title">Welcome</h1> -->
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
                </form> --%>
            </header>
            <sec:authorize access="hasRole('ROLE_USER')">
                <div class="row">
                    <jsp:include page="leftSideBar.jsp"></jsp:include>
                    <div class="col-sm-9">
                        <h1>Payment</h1>

                        <form class="form-horizontal" action="${pageContext.request.contextPath}/paymentConfirmation" method="post" id="PaymentForm">
                            From Account Number: &nbsp;&nbsp;&nbsp;<input type="text" name="fromAccount"/><br/>
                            To Account Number: &nbsp;&nbsp;&nbsp;<input type="text" name="toAccount"/><br/>
                            Amount: &nbsp;&nbsp;&nbsp;<input type="text" name="amount"/><br/>
                            Date: &nbsp;&nbsp;&nbsp;<input type="date" name="transferDate"/><br/>
                            <input type="submit" value="submit"/>
                        </form>
                        <p>${errorMessage}</p>
                    </div>
                </div>
            </sec:authorize>
        </article>
    </div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
</html>