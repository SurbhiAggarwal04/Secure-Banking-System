<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            alert();
            $.getJSON("${pageContext.request.contextPath}/resources/json/questions.json", function (obj) {
                $.each(obj.questions, function (key, value) {
                    alert(value.name);
                    $("#sq").append("<option>" + value.name + "</option>");
                });
            });
        });
    </script>

    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Forgot Your password</title>

    <meta charset="utf-8">
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

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="/js/html5shiv.js"></script>
    <script src="/js/respond.min.js"></script>
    <![endif]-->

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
        <li class="active">about</li>
    </ol>

    <div class="row">

        <!-- Article main content -->
        <article class="col-xs-12 maincontent">
            <header
                    class="page-header">
                <h1 class="page-title">Bank Of Tempe</h1>
            </header>
        </article>
    </div>
</div>

<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>