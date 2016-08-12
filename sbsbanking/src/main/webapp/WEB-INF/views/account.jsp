<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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
    <script src="${pageContext.request.contextPath}/resources/js/welcome.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/respond.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/login.js"></script>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/gt_favicon.png">

    <link rel="stylesheet" media="screen" href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">

    <!-- Custom styles for our template -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" media="screen">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">

    <script type="text/javascript">
        $(document).ready(function () {
            $("#btnStatement").click(function () {
                alert($("#btnStatement").val());
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
            <sec:authorize access="hasRole('ROLE_CUSTOMER')">
                <div class="row">
                    <jsp:include page="leftSideBar.jsp"></jsp:include>
                    <div class="col-sm-9">

                        <c:if test="${not empty accounts}">
                            <form:form id="accountFrom" name="accountFrom" commandName="form"
                                       class="form-horizontal"
                                       action="${pageContext.request.contextPath}/getStatement"
                                       style="width: 100%" method="get">

                                <c:forEach var="account" items="${accounts}">
                                    <table class="table">


                                        <thead>
                                        <tr>
                                            <th>select</th>
                                            <th>Account</th>
                                            <th>Account Type</th>
                                            <th>Balance</th>
                                        </tr>
                                        </thead>

                                        <tbody>
                                        <tr class="success">
                                            <td><form:radiobutton path="map['accountNum']"
                                                                  value="${account.accountNumber}"/></td>
                                            <td>${account.accountNumber}</td>
                                                <%-- <td><b> <!-- <a id="account" name="account" href="javascript:;" onclick="$(this).closest('form').submit()"> -->
                                                        <fmt:formatNumber
                                                            value="${account.accountNumber}" />
                                                </b></td> --%>
                                            <td>${account.accountType}</td>
                                                <%-- <td>${account.balance}</td> --%>
                                            <td><fmt:formatNumber value="${account.balance}"
                                                                  type="currency"/></td>
                                        </tr>


                                        </tbody>


                                        <tr>
                                            <td>


                                            </td>
                                        </tr>
                                    </table>
                                </c:forEach>
                                <div class="row">
                                    <div class="col-lg-8">

                                        <div class="row" id="getDiv"
                                             style="text-align: left; font-size: small">
                                            <input id="btnStatement" name="btnStatement" type="submit"
                                                   value="Get Statement" class="btn btn-primary"/>
                                        </div>

                                    </div>

                                </div>
                            </form:form>
                        </c:if>


                        <c:if test="${empty accounts}">
                            <label class="help-inline">There is some problem fetching your account!</label>
                        </c:if>


                    </div>
                </div>
            </sec:authorize>
        </article>
    </div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>