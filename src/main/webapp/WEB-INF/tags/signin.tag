<%@tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<div id="signinDiv" style="visibility:visible;">
    <ul>
      <li><a href="#signinTab" class="r-web-link"><s:message code="app.signin" /></a></li> 
    </ul>
    <div id="signinTab">
        <form:form modelAttribute="signinInfo" id="signinForm" action="../../open/security/signin" method="post" target="_self">
        <input type="hidden" name="targetUrl" value="${targetUrl}">
        <table width=100% height=100% class="r-signin-content">
        <tr style="height:30px;">
            <td class="r-signup-prompt"></td>
            <td colspan=2>
                <span class="r-message-validate">
                    <form:errors />
                </span>
            </td>
        </tr>
        <tr>
            <td class="r-signin-prompt"><s:message code="app.username" /></td>
            <td class="r-signin-input"><input type="text" id="username" name="username" value="${username}" class="r-input" tabindex="10"></td>
            <td class="r-content-cell">
                <span class="r-message-validate"><form:errors path="username" /></span><br>
                <!-- 
                <a href="signup" tabindex="100"><s:message code="app.signup" /></a>
                 -->
            </td>
        </tr>
        <tr>
            <td class="r-signin-prompt"><s:message code="app.password" /></td>
            <td class="r-signin-input">
                <input type="password" id="passwordText" class="r-input" tabindex="20">
                <input type="hidden" id="password" name="password">
            </td>
            <td class="r-content-cell">
                <span class="r-message-validate"><form:errors path="password" /></span><br>
                <!-- 
                <a href="getback-pwd" tabindex="110"><s:message code="app.getbackpwd" />
                 -->
            </a></td>
        </tr>
        <tr>
            <td class="r-signin-prompt"><s:message code="app.autosignin" /></td>
            <td colspan=2 class="r-signin-prompt">
                <span style="height:30px; line-height:30px; vertical-align: middle; margin: 3px; float:left;"><input type="checkbox" name="autoSignin" id="autoSignin" value="true" class="r-checkbox" tabindex="30"></span>
                <span style="height:30px; line-height:30px; vertical-align: middle; padding-left:2px;"><s:message code="app.autosignin.statement" /></span>
            </td>
        </tr>
        <tr>
            <td colspan=3 >&nbsp;</td>
        </tr>
        <tr>
            <td colspan=3 style="text-align: center;"><button type="button" id="signinSubmit" tabindex="40"><s:message code="app.signin" /></button></td>
        </tr>
        </table>
        </form:form>
    </div>   
</div>