<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <style type="text/css">
        @media ( min-width: 768px) {
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
    <script src="${pageContext.request.contextPath}/resources/js/welcome.js"></script>
    <script
            src="${pageContext.request.contextPath}/resources/js/respond.min.js"></script>
    <script
            src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <script
            src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/login.js"></script>
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
               style="margin-left: 0%; text-align: right; font-size: large;">Welcome
            User : ${pageContext.request.userPrincipal.name}</label>

    </c:if>
    <div class="row">

        <!-- Article main content -->
        <article class="col-xs-12 maincontent">
            <header
                    class="page-header"> <!-- <h1 class="page-title">Welcome</h1> -->
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
            <%-- <sec:authorize access="hasRole('ROLE_USER')"> --%>
            <div class="row">
                <jsp:include page="leftSideBar.jsp"></jsp:include>
                <div class="col-sm-9">

                    gyuhjhiuikjkjkj
                    <div class="row">
                        <c:if test="${empty NoSelectionError}">

                            <c:if test="${not empty accountTransactionList}">

                                <form id="statementForm" name="statementForm"
                                      class="form-horizontal"
                                      action="${pageContext.request.contextPath}/downloadStatement"
                                      style="width: 100%" method="get">

                                    <c:if test="${not empty accountNum}">
                                        <input type="text" class="form-control" id="accountNum"
                                               name="accountNum" value="${accountNum}">


                                    </c:if>
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th style="width: 20%" align="center">Date</th>

                                            <th align="center" style="width: 20%">Sender Account</th>


                                            <th style="width: 20%;" align="center">Receiver Account</th>
                                            <th align="center" style="width: 20%">Type</th>

                                            <th align="center">Amount</th>
                                            <th align="center">Status</th>
                                            <!-- <th>Balance</th> -->
                                        </tr>
                                        </thead>
                                    </table>
                                    <c:forEach var="transaction" items="${accountTransactionList}">

                                        <table class="table">


                                            <tbody style="overflow: scroll;">

                                            <tr class="success">
                                                <td style="width: 20%">${fn:trim(transaction.tranUpdateTS)}</td>

                                                <td style="width: 20%">${transaction.transactionFromAccountNum}</td>
                                                <c:if test="${empty transaction.transactionToAccountNum}">
                                                    <td style="width: 20%;">-</td>
                                                </c:if>
                                                <c:if
                                                        test="${not empty transaction.transactionToAccountNum}">
                                                    <td style="width: 20%;">${transaction.transactionToAccountNum}</td>
                                                </c:if>

                                                <td style="width: 20%">${transaction.transactionType}</td>
                                                <td><fmt:formatNumber value="${transaction.amount}"
                                                                      type="currency"/></td>
                                                <td>${transaction.transactionState}</td>

                                            </tr>
                                            </tbody>
                                        </table>
                                    </c:forEach>
                                    <div class="row" style="text-align: right">
                                            <%-- <a href="${pageContext.request.contextPath}/downloadStatement"> --%>
                                        <input id="btnDownload" name="btnDownload" type="submit"
                                               value="Download Statement" class="btn btn-primary"/>
                                        <!-- </a> -->
                                    </div>
                                </form>
                            </c:if>
                            <c:if test="${empty accountTransactionList}">
                                <label for="emptyList" class="col-lg-4 control-label"
                                       style="margin-left: 0%; text-align: left;">No
                                    Transactions to display</label>
                            </c:if>
                        </c:if>
                        <c:if test="${not empty NoSelectionError}">
                            <label class="col-lg-4 control-label"
                                   style="margin-left: 0%; text-align: left;">${NoSelectionError}</label>
                        </c:if>
                        <c:if test="${not empty NothingTodownloadError}">
                            <label class="col-lg-4 control-label"
                                   style="margin-left: 0%; text-align: left;">${NothingTodownloadError}</label>
                        </c:if>
                    </div>

                </div>

            </div>
            <%-- </sec:authorize> --%>
    </div>


    </article>
</div>

<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>