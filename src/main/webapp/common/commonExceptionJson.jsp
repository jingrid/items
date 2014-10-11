<%@page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
<%
        ActionContext ac = ActionContext.getContext();
        Exception exception = null;
        Object exceptionStack = null;
        String message = "";
        String stackTrace = null;
        
        if(ac.getValueStack().findValue("exception") != null) {
            exception = (Exception)ac.getValueStack().findValue("exception");
            Exception cause = (Exception)exception.getCause();
            if( cause!=null ){
                exception = cause;
            }
            message = exception.getMessage();
            message = StringEscapeUtils.escapeJavaScript( message );
        } else {
            message = "Unknown system internal error!";
        }
        if(ac.getValueStack().findValue("exceptionStack") != null) {
            exceptionStack =  ac.getValueStack().findValue("exceptionStack");
            stackTrace = exceptionStack.toString();
        } else {
            stackTrace = "Stack trace not found!";
        }
%>
{success: 'false', message: 'common: <%=message%>'}