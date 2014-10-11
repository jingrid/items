<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" isErrorPage="true" %>
<%@ page import="com.opensymphony.xwork2.ActionContext" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<title>Error Page</title>
	<script language="javascript">
		function showDetail()
		{
			document.getElementById('detail_error_msg').style.display = "block";
		}
	</script>
</head>

<body>

<div id="content">
	<%
		//Exception from JSP didn't log yet ,should log it here.
		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
	%>

	<h3>System Runtime Error: <br><!--=exception.getMessage()%-->
	</h3>
	<br>

	<button onclick="history.back();">Back</button>
	<br>

	<p><a href="#" onclick="showDetail();">Administrator click here to get the detail.</a></p>

	<div id="detail_error_msg" style="display:block">
        <%
				ActionContext ac = ActionContext.getContext();
				String stackTrace = null;
				if(ac.getValueStack().findValue("exceptionStack") != null) {            
				    stackTrace =  ac.getValueStack().findValue("exceptionStack").toString();
				} else {
				    stackTrace = "Stack trace not found!";
				}
        %>
        <%=stackTrace%>
        
		<pre><%//exception.printStackTrace(new java.io.PrintWriter(out));%></pre>
	</div>
</div>
</body>
</html>