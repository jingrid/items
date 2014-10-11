<%@tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="rui" tagdir="/WEB-INF/tags"%>

<div class="row-fluid" >

    <div class="span7 nopadding">
    <div class="container-fluid1"><form:form modelAttribute="signinInfo" id="signinForm" action="../security/signin" method="post" target="_self">
	<c:if test="${signinPage}"><rui:targeturl/></c:if><c:if test="${!signinPage}"><rui:targeturl fetch="true"/></c:if>
    <div class="row-fluid field" >
        <div class="offset3 span8">
            <h4>站内登录</h4>
        </div>
    </div>

    <div class="row-fluid field">
        <div class="offset3 span8 r-message-validate">
        </div>
    </div>
    
    <div class="row-fluid field vlarge sign">
        <div class="span3 prompt">
            <div><s:message code="app.username" /></div>
        </div>
        <div class="span9 input">
            <input type="text" id="usernameSignin" name="username" value="${username}"  tabindex="10" class="input-large" placeholder="<s:message code="app.username.hint" />">&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="../security/signup" tabindex="100"><s:message code="app.signup" /></a>
        </div>
    </div>

    <div class="row-fluid field vlarge sign">
        <div class="span3 prompt">
            <div><s:message code="app.password" /></div>
        </div>
        <div class="span9 input">
            <input type="hidden" id="passwordSignin" name="password">
            <input type="password" id="passwordText" tabindex="20" class="input-large" placeholder="<s:message code="app.password.hint" />">&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="../security/getback-pwd" tabindex="110"><s:message code="app.getbackpwd" /></a>
        </div>
    </div>
    
    <div class="row-fluid field vlarge">
        <div class="offset3 span9 control">
            <button type="button" id="signinSubmit" tabindex="40" class="btn btn-primary">立即<s:message code="app.signin" /></button>
            &nbsp;&nbsp;&nbsp;
            <label class="checkbox inline">
                <input type="checkbox" name="autoSignin" id="autoSignin" value="true" tabindex="30">&nbsp;
                <s:message code="app.autosignin.statement" />
            </label>
        </div>
    </div>
    </form:form></div>
    </div>
    
    <div class="offset-05- span5 nopadding">
    <form id="bindForm" action="" method="post" target="_self"></form>
    <div class="container-fluid1">
    <div class="row-fluid field" >
        <div class="offset3 span8">
            <h4>第三方登录</h4>
        </div>
    </div>
    <div class="vpad"></div>
    <div class="row-fluid field">
        <div class="span3">
            <button href="../security/signwith-weibo" class="r-cystal-button" title="使新浪微博账号登录">
            <img src="../../common/resources/image/sina-weibo.png"/>
            </button>
        </div>
        <div class="span3">
            <button href="../security/signwith-renren" class="r-cystal-button" title="使新人人网账号登录">
            <img src="../../common/resources/image/renren.png"/>
            </button>
        </div>
        <div class="span3">
            <button href="../security/signwith-tencent" class="r-cystal-button" title="使新腾讯博账号登录">
            <img src="../../common/resources/image/tencent-qq.png"/>
            </button>
        </div>
        <div class="span3">
            <button href="../security/signwith-baidu" class="r-cystal-button" title="使新腾讯博账号登录">
            <img src="../../common/resources/image/baidu.png"/>
            </button>
        </div>
    </div>
    <div class="vpad"></div>
    <div class="row-fluid field">
        <div class="span3">
            <button href="../security/signwith-linkedin" class="r-cystal-button">
            <img src="../../common/resources/image/linkedin.png"/>
            </button>
        </div>
        <div class="span3">
            <button href="../security/signwith-salesforce" class="r-cystal-button">
            <img src="../../common/resources/image/salesforce.png"/>
            </button>
        </div>
        <div class="span3">
            <button href="../security/signwith-twitter" class="r-cystal-button">
            <img src="../../common/resources/image/twitter.png"/>
            </button>
        </div>
        <div class="span3">
            <button href="../security/signwith-facebook" class="r-cystal-button">
            <img src="../../common/resources/image/facebook.png"/>
            </button>
        </div>
    </div>
    <div class="vpad"></div>
    </div>
    </div>
    
</div>
<script>
	Rui.rs['app.username.null'] = '<s:message code="app.username.null" />';
	Rui.rs['app.username.hint'] = '<s:message code="app.username.hint" />';
	Rui.rs['app.username.illegal'] = '<s:message code="app.username.illegal" />';
	Rui.rs['app.password.null'] = '<s:message code="app.password.null" />';
	Rui.rs['app.password.hint'] = '<s:message code="app.password.hint" />';
	Rui.rs['app.passwordconfirm.hint'] = '<s:message code="app.passwordconfirm.hint" />';
</script>