<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@include file="common.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title><s:message code="app.title" /></title>
    <link rel="shortcut icon" href="../favicon.ico">
    <link rel="stylesheet" href="../../resources/components/bootstrap/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="../../resources/components/bootstrap/bootstrap/css/bootstrap-responsive.css">
    <!--
    -->
    <link rel="stylesheet" href="../../resources/rui/css/rui.css">
    <style type="text/css">

    </style>

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <script src="../../resources/components/jquery/jquery.js"></script>
    <script src="../../resources/components/bootstrap/bootstrap/js/bootstrap.js"></script>
<!--
    <script src="../../resources/rui/js/rui.js"></script>
-->
    <script>
        $(document).ready(function(){
            Rui.page.init();
        });
    </script>
</head>
<body>
<div>
<c:set var="headMenuActive" value="home" />
<c:set var="publicPage" value="true" />
<%@include file="../portal/head.jsp" %>


<%@include file="../portal/foot.jsp" %>
</div><!-- top container -->
</body>
</html>