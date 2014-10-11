<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<title>Error Page</title>
</head>

<body>

<div id="content">
	<div id="detail_error_msg" >
		<pre><%exception.printStackTrace(new java.io.PrintWriter(out));%></pre>
	</div>
</div>
</body>
</html>