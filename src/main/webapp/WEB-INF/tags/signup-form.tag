<%@tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="rui" tagdir="/WEB-INF/tags"%>

<div class="row-fluid" >
    <div class="span8">
    <div class="container-fluid"><form:form modelAttribute="signupInfo" id="signupForm" action="../security/signup" method="post" target="_self">
    <c:if test="${signupPage}"><rui:targeturl/></c:if><c:if test="${!signupPage}"><rui:targeturl fetch="true"/></c:if>
    <div class="row-fluid field" >
        <div class="offset3 span8">
            <h4>内部注册</h4>
        </div>
    </div>
        
    <div class="row-fluid field">
        <div class="offset3 span8 r-message-validate"><form:errors /></div>
    </div>
    
    <div class="row-fluid field vlarge">
        <div class="span3 prompt ">
            <div><s:message code="app.username" /></div>
        </div>
        <div class="span6 input">
            <input type="text" id="usernameSignup" name="username" value="${username}" tabindex="10" >
        </div>
        <div class="span3 static">
            <div>
                <span id="usernameError" class="r-message-validate"><form:errors path="username" /></span>
                <span id="usernameHint" class="r-message-block"></span>
            </div>
        </div>
    </div>

    <div class="row-fluid field vlarge">
        <div class="span3 prompt">
            <div><s:message code="app.password" /></div>
        </div>
        <div class="span6 input">
            <input type="password" id="passwordFirst" name="passwordFirst" tabindex="20">
        </div>
        <div class="span3 static">
            <div>
                <span class="r-message-validate"><form:errors path="password" /></span>
                <span id="passwordHint" class="r-pwd-strength-hint"></span>
            </div>
        </div>
    </div>
    
    <div class="row-fluid field hide">
        <div class="span3 prompt">
            <div><s:message code="app.passwordconfirm" /></div>
        </div>
        <div class="span6 input">
                <input type="password" id="passwordSecond" name="passwordSecond" tabindex="30">
                <input type="hidden" name="password" id="passwordSignup" >
                <input type="hidden" name="passwordStrength" id="passwordStrength" >
        </div>
        <div class="span3 static">
            <div>
                <span class="r-message-validate"></span>
            </div>
        </div>
    </div>

    <div class="row-fluid field hide">
        <div class="span3 prompt">
            <div><s:message code="app.email" /></div>
        </div>
        <div class="span6 input">
                <input type="email" id="email" name="email" value="${email}" tabindex="40">
        </div>
        <div class="span3 static">
            <div>
                <span class="r-message-validate"><form:errors path="email" /></span>
            </div>
        </div>
    </div>

    <div class="row-fluid field">
        <div class="span3 prompt">
        </div>
        <div class="span8 control">
            <button type="button" id="signupSubmit" tabindex="60" class="btn btn-primary"><s:message code="app.signup" /></button>
            &nbsp;&nbsp;&nbsp;
            <label class="checkbox inline">
                <input type="checkbox" name="agreeAgreement" id="agreeAgreement" value="true" checked="${agreeAgreement}" tabindex="50">&nbsp;
                <s:message code="app.agreement.agree" />
                <a href="javascript:" id="agreementLink"><s:message code="app.agreement" /></a>
            </label>
        </div>
        <div class="static">
            <div>
                <span class="r-message-validate"></span>
            </div>
        </div>
    </div>

    </form:form></div>
    </div>
    
    <div class="offset-05 span4 nopadding">
    <form id="bindForm" action="" method="post" target="_self"></form>
    <div class="container-fluid">
    <div class="row-fluid field" >
        <div class="offset3 span8">
            <h4>第三方注册</h4>
        </div>
    </div>
    <div class="vpad"></div>
    <div class="row-fluid field">
        <div class="span3 prompt">
            <button href="../security/signwith-weibo" class="r-cystal-button" title="使新浪微博账号登录">
            <img src="../../common/resources/image/sina-weibo.png"/>
            </button>
        </div>
        <div class="offset1 span3 prompt">
            <button href="../security/signwith-renren" class="r-cystal-button" title="使新人人网账号登录">
            <img src="../../common/resources/image/renren.png"/>
            </button>
        </div>
        <div class="offset1 span3 prompt">
            <button href="../security/signwith-renren" class="r-cystal-button" title="使新腾讯博账号登录">
            <img src="../../common/resources/image/tencent-weibo.png"/>
            </button>
        </div>
    </div>
    <div class="vpad"></div>
    <div class="row-fluid field">
        <div class=" span3 prompt">
            <button href="../security/signwith-tencent" class="r-cystal-button" title="使新QQ空间账号登录">
            <img src="../../common/resources/image/tencent-qzone.png"/>
            </button>
        </div>
        <div class="offset1 span3 prompt">
            <button href="../security/signwith-linkedin" class="r-cystal-button">
            <img src="../../common/resources/image/linkedin.png"/>
            </button>
        </div>
        <div class="offset1 span3 prompt">
            <button href="../security/signwith-salesforce" class="r-cystal-button">
            <img src="../../common/resources/image/salesforce.png"/>
            </button>
        </div>
    </div>
    <div class="vpad"></div>
    <div class="row-fluid field">
        <div class=" span3 prompt">
            <button href="../security/signwith-twitter" class="r-cystal-button">
            <img src="../../common/resources/image/twitter.png"/>
            </button>
        </div>
        <div class="offset1 span3 prompt">
            <button href="../security/signwith-facebook" class="r-cystal-button">
            <img src="../../common/resources/image/facebook.png"/>
            </button>
        </div>
    </div>
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
	Rui.rs['app.email.null'] = '<s:message code="app.email.null" />';
	Rui.rs['app.email.illegal'] = '<s:message code="app.email.illegal" />';
	Rui.rs['app.email.hint'] = '<s:message code="app.email.hint" />';
	Rui.rs['app.displayname.hint'] = '<s:message code="app.displayname.hint" />';
	Rui.rs['app.username.existent'] = '<s:message code="app.username.existent" />';
	Rui.rs['app.username.non-existent'] = '<s:message code="app.username.non-existent" />';
	Rui.rs['app.passwordconfirm.same'] = '<s:message code="app.passwordconfirm.same" />';
	Rui.rs['app.passwordconfirm.diff'] = '<s:message code="app.passwordconfirm.diff" />';
	Rui.rs['app.pwdstr'] = '<s:message code="app.pwdstr" />';
	Rui.rs['app.pwdstr.blank'] = '<s:message code="app.pwdstr.blank" />';
	Rui.rs['app.pwdstr.tooshort'] = '<s:message code="app.pwdstr.tooshort" />';
	Rui.rs['app.pwdstr.weak'] = '<s:message code="app.pwdstr.weak" />';
	Rui.rs['app.pwdstr.medium'] = '<s:message code="app.pwdstr.medium" />';
	Rui.rs['app.pwdstr.strong'] = '<s:message code="app.pwdstr.strong" />';
	Rui.rs['app.pwdstr.verystrong'] = '<s:message code="app.pwdstr.verystrong" />';
</script>
<script type="text/javascript" src="../../common/resources/jquery/script/jquery.validate.js"></script>
<script type="text/javascript" src="../../common/resources/jquery/script/messages_<rui:locale/>.js"></script>
