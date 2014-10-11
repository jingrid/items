<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@include file="../../common/jsp/taglibs.jsp" %>

<header>
<div class="navbar navbar-inverse navbar-fixed-top"><div class="navbar-inner"><div class="container">

    <!-- .btn-navbar is used as the toggle for collapsed navbar content -->
    <a class="btn btn-navbar" data-toggle="collapse" data-target=".head-collapse">
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </a>
    <a class="brand" href="">
        <div style="color: #ffffff;font-weight: bold;"><s:message code="app.name" /></div>
    </a>
    
    <div class="nav-collapse head-collapse">
        <ul class="nav">
            <li class="dropdown">
                <!-- 
                <button class="btn btn-warning btn-small">北京</button>
                <a href="#" class="dropdown-toggle" style="color: white; font-weight: 15px; font-weight: bold;" ><b class="caret"></b></a>
                 -->
            </li>
            <li class="${headMenuActive=='home'?'active':''}">
                <a href="../../public/portal/main" class="r-link"><s:message code="app.home" /></a>
            </li>
            
            <c:if test="${signinStatus}">
            <li class="${headMenuActive=='site'?'active':''}">
                <a href="../../public/portal/main" class="r-link"><s:message code="app.myhome" /></a>
            </li>
            <li class="${headMenuActive=='bss'?'active':''}">
                <a href="../../bss/portal/main" class="r-link">运营与支持</a>
            </li>
            <li class="${headMenuActive=='oss'?'active':''}">
                <a href="../../oss/portal/main" class="r-link">平台管理</a>
            </li>
            </c:if>
        </ul>
        <ul class="nav pull-right">
            <c:if test="${signinStatus}">
            <li>
                <a href="#" class="r-link-small"><i class="icon-heart icon-white"></i> <s:message code="app.myfavorites" /></a>
            </li>
            <li>
                <a href="#" class="r-link-small"><i class="icon-envelope icon-white"></i> <s:message code="app.messages" /> <span class="badge r-badge-small">2</span></a>
            </li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle r-link-small"><i class="icon-asterisk icon-white"></i> <s:message code="app.settings" /> <b class="caret"></b></a>
            </li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle r-link-small" data-toggle="dropdown"><i class="icon-user icon-white"></i> ${user.displayName} <b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="../../site/social/connect"><s:message code="app.viewprofile" /></a>
                    </li>
                    <li>
                        <a href="../../open/security/change-pwd"><s:message code="app.changepwd" /></a>
                    </li>
                    <li>
                        <a href="../../open/security/signout"><s:message code="app.signout" /></a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="../../open/security/signup"><s:message code="app.signup" /></a>
                    </li>
                    <li>
                        <a href="../../open/security/getback-pwd"><s:message code="app.getbackpwd" /></a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="../../open/security/signout?exit=true"><s:message code="app.exit" /></a>
                    </li>
                </ul>
            </li>
            </c:if>
            
            <c:if test="${!signinStatus}">
                <c:if test="${!signupPage}">
            <li>
                <!-- 
                 -->
                <button class="btn btn-primary btn-small signup-button" ><s:message code="app.signup" /></button>
                <button class="btn btn-danger btn-small1 tenant-signup-button" href="../pp/tenant-apply" target="_self" >申请试用</button>
            </li>
                </c:if>

                <c:if test="${!signinPage}">
            <li>
                <button class="btn btn-success btn-small1 signin-button" ><s:message code="app.signin" /></button>
            </li>
                </c:if>
                
            </c:if>
        </ul>
    </div><!--/.nav-collapse -->

</div></div></div>
</header>

<c:if test="${!signinPage}">
<c:if test="${!signinStatus}">
<rui:signin-modal/>
</c:if>
</c:if>

<c:if test="${!signupPage}">
<c:if test="${!signinStatus}">
<rui:signup-modal/>
</c:if>
</c:if>

<!-- body section begin -->
<section><div class="container">
<div class="container-fluid nopadding">
