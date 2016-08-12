<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <style type="text/css">
        table {
            width: 300px;
        }

        tbody {
            height: 10em;
            overflow: scroll;
        }

    </style>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/leftSideBar.css">
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
            <sec:authorize access="hasRole('ROLE_CUSTOMER')">
                <div class="row">
                    <jsp:include page="leftSideBar.jsp"></jsp:include>
                    <div class="col-sm-9">
                        <table class="table">

                                <%-- 	<tr>
                                       <c:forEach begin="1" end="${noOfpages}" var="i">

                                                   <td>${i}</td>
                                                   <c:set var="recordIndex" value="${((i-1)*10)+1}"/>
                                                   <c:set var="endIndex" value="${((i)*10)+1}"/>
                                       </c:forEach>

                                   </tr>  --%>
                            <thead>
                            <tr>
                                <th style="width: 20%" align="center">Date</th>

                                <th align="center">Account</th>
                                <th></th>

                                <th style="width: 20%;" align="center">Description</th>
                                <th align="center" style="width: 20%">Type</th>

                                <th align="center">Amount</th>
                                <th align="center">Status</th>
                                <!-- <th>Balance</th> -->
                            </tr>
                            </thead>
                        </table>
                        <c:if test="${not empty transactions}">

                            <c:forEach var="transaction" items="${transactions}">

                                <table class="table">


                                    <tbody style="overflow: scroll;">

                                    <tr class="success">
                                        <td style="width: 20%">${fn:trim(transaction.tranUpdateTS)}</td>
                                        <c:choose>
                                            <c:when test="${fn:containsIgnoreCase(transaction.transactionType, 'credit')}">
                                                <td>${fn:trim(transaction.transactionFromAccountNum)}</td>
                                                <td style="width: 20%">-</td>
                                            </c:when>
                                            <c:when test="${fn:containsIgnoreCase(transaction.transactionType, 'debit')}">
                                                <td>${fn:trim(transaction.transactionFromAccountNum)}</td>
                                                <td style="width: 20%">-</td>
                                            </c:when>
                                            <c:when test="${fn:containsIgnoreCase(transaction.transactionType, 'payment')}">
                                                <td>${fn:trim(transaction.transactionFromAccountNum)}</td>
                                                <td style="width: 20%">Amount given to
                                                    Merchant:${transaction.transactionToAccountNum}</td>
                                            </c:when>
                                            <c:when
                                                    test="${fn:containsIgnoreCase(transaction.transactionType, 'transfer')}">
                                                <c:if test="${fn:length(accounts) gt 1}">
                                                    <c:choose>

                                                        <c:when test="${  fn:contains( accounts[0].accountNumber, transaction.transactionFromAccountNum ) }">

                                                            <c:choose>
                                                                <c:when test="${  fn:contains( accounts[1].accountNumber, transaction.transactionToAccountNum ) }">
                                                                    <td>${transaction.transactionFromAccountNum}</td>
                                                                    <td style="width: 20%">Transfer to the other
                                                                        personal account
                                                                    </td>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <td>${transaction.transactionFromAccountNum}</td>
                                                                    <td style="width: 20%">Amount tranferrred
                                                                        to ${transaction.transactionToAccountNum}</td>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:when>
                                                        <c:when test="${  fn:contains( accounts[1].accountNumber, transaction.transactionFromAccountNum ) }">

                                                            <c:choose>
                                                                <c:when test="${  fn:contains( accounts[0].accountNumber, transaction.transactionToAccountNum ) }">
                                                                    <td>${transaction.transactionFromAccountNum}</td>
                                                                    <td style="width: 20%">Transfer to the other
                                                                        personal account
                                                                    </td>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <td>${transaction.transactionFromAccountNum}</td>
                                                                    <td style="width: 20%">Amount tranferrred
                                                                        to ${transaction.transactionToAccountNum}</td>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:when>

                                                        <c:when test="${  fn:contains( accounts[0].accountNumber, transaction.transactionToAccountNum ) }">

                                                            <c:choose>
                                                                <c:when test="${  fn:contains( accounts[1].accountNumber, transaction.transactionFromAccountNum ) }">
                                                                    <td>${transaction.transactionFromAccountNum}</td>
                                                                    <td style="width: 20%">Transfer to the other
                                                                        personal account
                                                                    </td>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <td>${transaction.transactionToAccountNum}</td>
                                                                    <td style="width: 20%">Amount received
                                                                        from ${transaction.transactionFromAccountNum}</td>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:when>
                                                        <c:when test="${  fn:contains( accounts[1].accountNumber, transaction.transactionToAccountNum ) }">
                                                            <c:choose>
                                                                <c:when test="${  fn:contains( accounts[0].accountNumber, transaction.transactionFromAccountNum ) }">
                                                                    <td>${transaction.transactionFromAccountNum}</td>
                                                                    <td style="width: 20%">Transfer to the other
                                                                        personal account
                                                                    </td>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <td>${transaction.transactionToAccountNum}</td>
                                                                    <td style="width: 20%">Amount received
                                                                        from ${transaction.transactionFromAccountNum}</td>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:when>

                                                    </c:choose>
                                                </c:if>
                                                <c:if test="${fn:length(accounts) eq 1}">
                                                    <c:choose>

                                                        <c:when test="${  fn:contains( accounts[0].accountNumber, transaction.transactionFromAccountNum ) }">


                                                            <td>${transaction.transactionFromAccountNum}</td>
                                                            <td style="width: 20%">Amount tranferrred
                                                                to ${transaction.transactionToAccountNum}</td>


                                                        </c:when>


                                                        <c:when test="${  fn:contains( accounts[0].accountNumber, transaction.transactionToAccountNum ) }">


                                                            <td>${transaction.transactionToAccountNum}</td>
                                                            <td style="width: 20%">Amount received
                                                                from ${transaction.transactionFromAccountNum}</td>

                                                        </c:when>


                                                    </c:choose>


                                                </c:if>
                                            </c:when>

                                        </c:choose>

                                        <td style="width: 20%">${transaction.transactionType}</td>


                                        <td><fmt:formatNumber value="${transaction.amount}" type="currency"/></td>
                                        <td>${transaction.transactionState}</td>
                                    </tr>


                                    </tbody>

                                </table>
                            </c:forEach>
                            <%-- <form:form name="downloadForm" modelAttribute="${transactions}" action="${pageContext.request.contextPath}/downloadStatement">	 --%>
                            <div class="row" style="text-align: right">
                                <a href="${pageContext.request.contextPath}/downloadStatement"> <input id="btnDownload"
                                                                                                       name="btnDownload"
                                                                                                       type="button"
                                                                                                       value="Download Statement"
                                                                                                       class="btn btn-primary"/>
                                </a>
                            </div>
                            <%-- </form:form>
 --%>
                        </c:if>
                        <c:if test="${empty transactions}">
                            <label class="col-lg-4 control-label"
                                   style="margin-left: 0%; text-align: left;">${transactionFecthingError }</label>
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