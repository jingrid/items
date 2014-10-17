<%@page session="false" %>
<%@page import="com.jingge.platform.common.security.PlatformAuthenticationToken"%>
<%@page import="com.jingge.platform.common.security.UserInformation"%>
<%@page import="org.springframework.security.authentication.AnonymousAuthenticationToken"%>
<%@page import="org.springframework.security.core.Authentication"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@include file="./taglibs.jsp" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	response.setDateHeader("Expires",0); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache");

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if( authentication!=null ){
        if( authentication instanceof PlatformAuthenticationToken ){
		    PlatformAuthenticationToken token = (PlatformAuthenticationToken)authentication;
	        Object userInfo = token.getUserInformation();
	        if( userInfo!=null ){
	            request.setAttribute( "user", userInfo );
	            request.setAttribute( "signinStatus", Boolean.TRUE );
	        }
        }
        else if( authentication instanceof AnonymousAuthenticationToken ){
            AnonymousAuthenticationToken token = (AnonymousAuthenticationToken)authentication;
            UserInformation userInfo = new UserInformation();
            userInfo.setDisplayName( (String)token.getPrincipal() );
            if( userInfo!=null ){
                request.setAttribute( "user", userInfo );
                request.setAttribute( "signinStatus", Boolean.FALSE );
            }
        } 
        else{
            throw new RuntimeException("Illegal Authentication object is issued");
        }
    }
    else{
        request.setAttribute( "signinStatus", Boolean.FALSE );
    }
%>