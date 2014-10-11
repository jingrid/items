<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@include file="../common/common.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><s:text name="app.name" /></title>
    <link rel="shortcut icon" href="../favicon.ico">
    <link rel="stylesheet" type="text/css" href="../resources/jquery/theme/cupertino/jquery-ui-1.8.custom.css">
    <link rel="stylesheet" type="text/css" href="../resources/css/Rui.css">
    <script type="text/javascript" src="../resources/jquery/script/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="../resources/jquery/script/jquery-ui-1.8.17.custom.min.js"></script>
    <script type="text/javascript" src="../resources/app/Rui.js"></script>
    <script type="text/javascript">
        
        $(document).ready(function(){
            Rui.page.init();
	    });
    </script>
</head>
<body>
<table width=100% height=100% class="r-table-layout">
<tr>
    <td>
		<jsp:include page="../portal/head.jsp" />
    </td>
</tr>
<tr>
    <td style="text-align:center;vertical-align:middle;">

<div id="activateDiv" style="width:600px;height:320px;">
    <div>
        恭喜${displayName},您已成功完成第一步：账户注册！还差最后一步：账户激活。
    </div>
    <div>
        请进入您的邮箱${username}进行账户激活。
    </div>
</div>

    </td>
</tr>
<tr>
    <td></td>
</tr>
<tr>
    <td>
        <%@include file="../portal/foot.jsp" %>
    </td>
</tr>
</table>
</body>
</html>