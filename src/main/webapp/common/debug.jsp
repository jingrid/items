<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.realpaas.platform.framework.http.CookieManager"%>
<%@page import="com.realpaas.platform.common.web.CookieHelper"%>
<%@page import="com.realpaas.platform.common.web.LocaleHelper"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Locale"%>
<%@ include file="common.jsp" %>

<%
    String sessionLocale = LocaleHelper.getSessionLocaleCode( request );
    String cookieLocale = LocaleHelper.getCookieLocaleCode( request, "locale" );
    String currentLocale = LocaleHelper.getLocaleCode( request );
    CookieManager cm = CookieHelper.getCookieManager( request, response );
    HttpSession session = request.getSession( false );
    boolean hasSession = session!=null;
    Enumeration enumSessinName = null;
    if( hasSession ){
        enumSessinName = session.getAttributeNames();
    }
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><s:message code="app.name" /></title>
    <link rel="shortcut icon" href="../favicon.ico">
    <link rel="stylesheet" type="text/css" href="../resources/css/App.css">
    <style>
        table,tr,td,th {
            border: 1px solid #CCCCCC;
            border-collapse:collapse;
        }
        label {
            font-weight: bold;
        }
    </style>
    <script type="text/javascript" src="../resources/jquery/script/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="../resources/jquery/script/jquery.cookie.js"></script>
    <script type="text/javascript" src="../resources/app/App.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            var refreshPage = function(){
                window.location.href = window.location.href;
            };
        
            $('#refreshButton').click(function(){
                refreshPage();
            });
        
            $('#clearAllCookieButton').click(function(){
                App.removeCookies();
                refreshPage();
            });
            
            $('.cookieItemRemove').click(function(){
                var btn = $(this);
                var name = btn.parent().find('input:hidden.cookieName').val();
                $.removeCookie( name, App.cookieOptions );
                refreshPage();
            });
            
            $('.cookieItemUpdate').click(function(){
                var btn = $(this);
                var name = btn.parent().find('input:hidden.cookieName').val();
                var newValue = btn.parent().find('input:text.cookieText').val();
                $.cookie( name, newValue, App.cookieOptions );
                refreshPage();
            });
            
        });
    </script>
</head>
<body>
<button id="refreshButton">Refresh</button>
<table width=100% height="30px" border=0>
<tr>
    <td width="100%">
<table width="100%" style="vertical-align:top;">
<tr>
    <td colspan=2 align=center>
        Context Key Variables
    </td>
</tr>
<tr style="font-weight:bold;align-text:center;">
    <td width="200px">
        Name
    </td>
    <td>
        Value
    </td>
</tr>
<tr>
    <td><label>Session Status: </label></td><td>${ sessionStatus }</td>
</tr>
<tr>
    <td><label>Signin Status: </label></td><td>${ signinStatus }</td>
</tr>
<tr>
    <td><label>Locale: </label></td><td>${ currentLocale }</td>
</tr>
<tr>
    <td><label>Session Locale: </label></td><td>${ sessionLocale }</td>
</tr>
<tr>
    <td><label>Cookie Locale: </label></td><td>${ cookieLocale }</td>
</tr>
</table>  
    </td>
</tr>
<tr>
    <td style="vertical-align:top;">
<table width="100%" style="vertical-align:top;">
<tr>
    <td colspan=2 align=center>
        Session Detail
    </td>
</tr>
<tr style="font-weight:bold;align-text:center;">
    <td width="200px">
        Name
    </td>
    <td>
        Value
    </td>
</tr>
<%
if( hasSession ){
    for(; enumSessinName.hasMoreElements();){
        String sessionKey = (String)enumSessinName.nextElement();
        Object sessionValue = session.getAttribute( sessionKey );
%>
<tr>
    <td>
        <%=sessionKey %>
    </td>
    <td>
        <%=sessionValue.toString() %>
    </td>
</tr>
<%
    }//end for
}//end if
%>
</table>
    </td>
</tr>
<tr>
    <td style="vertical-align:top;">
<table width="100%" border=1 style="border-collapse:collapse;vertical-align:top;">
<tr>
    <td colspan=2 align=center>
        Cookie Detail @ <%=request.getServerName()%> &nbsp; <button id="clearAllCookieButton">Clear All Cookies</button>
    </td>
</tr>
<tr style="font-weight:bold;">
    <td width="200px">
        Name
    </td>
    <td>
        Value
    </td>
</tr>
<%
    for(Iterator<Cookie> cookieIter = cm.iterator(); cookieIter.hasNext();){
        Cookie cookie = cookieIter.next();
%>
<tr>
    <td>
        <%=cookie.getName()%>
    </td>
    <td>
        <%=cookie.getValue()%>
        <input type="hidden" class="cookieName"  value="<%=cookie.getName()%>">
        <br>
        <input type="text" class="cookieText" size=20 value="<%=cookie.getValue()%>">
        <button class="cookieItemUpdate">Update</button>
        <button class="cookieItemRemove">Remove</button>
    </td>
</tr>
<%
    }//end for
%>
</table>
    </td>
</tr>
</table>
</body>
</html>