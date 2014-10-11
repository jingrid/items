<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@include file="../portal/common.jsp" %>

<!DOCTYPE html>
<html lang="en" ng-app="jobs">
<head>
    <meta charset="utf-8">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title><s:message code="app.title" /></title>
    <link rel="shortcut icon" href="../favicon.ico">
    <link rel="stylesheet" href="../../common/resources/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="../../common/resources/bootstrap/css/bootstrap-responsive.css">
    <link rel="stylesheet" href="../../common/resources/rui/css/rui.css">
    <style type="text/css">

    </style>

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <script src="../../common/resources/jquery/script/jquery-1.7.1.js"></script>
    <script src="../../common/resources/bootstrap/js/bootstrap.js"></script>
    <script src="../../common/resources/jquery/script/jquery-ui-1.8.17.custom.min.js"></script>
    <script src="../../common/resources/jquery/script/jquery.crypt.js"></script>
    <script src="../../common/resources/jquery/script/jquery.cookie.js"></script>
    <script src="../../common/resources/rui/js/rui.js"></script>
    <script src="../../common/resources/angular/angular.js"></script>
    <script src="../../common/resources/angular/angular-resource.js"></script>
    <script src="../resources/scheduling/js/jobs-app.js"></script>
    <script src="../resources/scheduling/js/jobs-controllers.js"></script>
    <script src="../resources/scheduling/js/jobs-services.js"></script>
<!-- 
	<script src="./js/app-jobs.js"></script>
	<script src="./js/controllers-jobs.js"></script>
 -->
    <script>
        $(document).ready(function(){
            Rui.page.init();
        });
    </script>
</head>
<body>
<div>
<c:set var="headMenuActive" value="myHome" />
<%@include file="../portal/head.jsp" %>

<div class="row">
    <div class="span3">
        <%@include file="./navigator.jsp" %>
    </div>
    <div ng-view class="span9 nomargin">
    </div>
<%@include file="../portal/foot.jsp" %>
</div><!-- top container -->

</body>
</html>