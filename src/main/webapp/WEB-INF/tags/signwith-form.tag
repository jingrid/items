<%@tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="rui" tagdir="/WEB-INF/tags"%>

<div class="row-fluid" >
    <div class="row-fluid" >
        <input type="hidden" id="initAction" value="${action}">
	    <c:if test="${action!='warnAndSignin'}">
		<ul class="nav nav-tabs">
			<li class="${action=='signupAndBind' ?'active':''}"><a href="#signupTab" data-toggle="tab" class="signupAndBind">创建并关联新账户</a></li>
			<li class="${action=='signinAndBind'?'active':''}"><a href="#signinTab" data-toggle="tab" class="signinAndBind">关联已有账户</a></li>
		</ul>
	    </c:if>
	</div>
    <div class="row-fluid" >
    <div class="span6 hpad">
	    <div class="tab-content">
	    <div class="tab-pane ${action=='signupAndBind' ?'active':''}" id="signupTab">
        <form:form modelAttribute="signwithInfo" id="signupAndBindForm" action="../security/signwith" method="post" target="_self">
        <c:if test="${signwithPage}"><rui:targeturl/></c:if><c:if test="${!signwithPage}"><rui:targeturl fetch="true"/></c:if>
        <input type="hidden" name="providerId" value="${providerId}">
        <input type="hidden" name="providerUserId" value="${providerUserId}">
        <input type="hidden" id="action" name="action" value="signupAndBind">

		    <div class="row-fluid r-form-row">
		        <div class="offset0 span12 r-message-validate">
                    <form:errors path="username" />
                    <form:errors />
		        </div>
		    </div>
		    <div class="row-fluid r-form-row">
		        <div class="span4 r-prompt">
		            <div><s:message code="app.username" /></div>
		        </div>
		        <div class="span6 r-value r-signup">
		            <input type="text" id="usernameSignup" name="username" value="${username}" placeholder="<s:message code="app.username.hint" />" tabindex="10" >
		        </div>
		    </div>
		
		    <div class="row-fluid r-form-row">
		        <div class="span4 r-prompt">
		            <div><s:message code="app.password" /></div>
		        </div>
		        <div class="span6 r-value r-signup">
		            <input type="password" id="passwordFirst" name="passwordFirst" placeholder="<s:message code="app.password.hint" />" tabindex="20">
		        </div>
		    </div>
		    
		    <div class="row-fluid r-form-row hide">
		        <div class="span4 r-prompt">
		            <div><s:message code="app.passwordconfirm" /></div>
		        </div>
		        <div class="span6 r-value r-signup">
		                <input type="password" id="passwordSecond" name="passwordSecond" tabindex="30">
		                <input type="hidden" name="password" id="passwordSignup" >
		                <input type="hidden" name="passwordStrength" id="passwordStrength" >
		        </div>
		    </div>
		
		    <div class="row-fluid r-form-row hide">
		        <div class="span4 r-prompt">
		            <div><s:message code="app.email" /></div>
		        </div>
		        <div class="span6 r-value r-signup">
		                <input type="email" id="email" name="email" value="${email}" tabindex="40">
		        </div>
		    </div>
		    
		    <div class="row-fluid r-form-row">
		        <div class="span4 r-prompt">
		            <div><s:message code="app.displayname" /></div>
		        </div>
		        <div class="span6 r-value r-signup">
		            <input type="text" id="displayName" name="displayName" value="${displayName}" tabindex="50" disabled="disabled">
		        </div>
		    </div>
		    
		    <div class="row-fluid r-form-row">
		        <div class="span4 r-prompt">
		        </div>
		        <div class="span8 r-value r-signup">
		            <label class="checkbox">
		                <input type="checkbox" name="agreeAgreement" id="agreeAgreement" value="true" checked="${agreeAgreement}" tabindex="50">&nbsp;
		                <s:message code="app.agreement.agree" />
		                <a href="javascript:" id="agreementLink"><s:message code="app.agreement" /></a>
		            </label>
		        </div>
		    </div>
            <div class="row-fluid r-form-row">
                <div class="span4 r-prompt">
                </div>
                <div class="span6 r-value r-signup">
                    <button type="button" id="signupAndBindSubmit" tabindex="60" class="btn btn-primary"><s:message code="app.signup" /></button>
                </div>
            </div>
        </form:form>
	    </div>
        <div class="tab-pane ${action=='signinAndBind' || action=='warnAndSignin' ?'active':''}" id="signinTab">
        <form:form modelAttribute="signwithInfo" id="signinAndBindForm" action="../security/signwith" method="post" target="_self">
        <c:if test="${signwithPage}"><rui:targeturl/></c:if><c:if test="${!signwithPage}"><rui:targeturl fetch="true"/></c:if>
        <input type="hidden" name="providerId" value="${providerId}">
        <input type="hidden" name="providerUserId" value="${providerUserId}">

        <c:if test="${action!='signupAndBind'}">
        <input type="hidden" id="action" name="action" value="${action}">
        </c:if>
        <c:if test="${action=='signupAndBind'}">
        <input type="hidden" id="action" name="action" value="signinAndBind">
        </c:if>

            <div class="row-fluid r-form-row">
                <div class="offset0 span12 r-message-validate">
                    <form:errors path="username" />
                    <form:errors path="password" />
                    <form:errors />
                </div>
            </div>
            
            <div class="row-fluid r-form-row">
                <div class="span4 r-prompt">
                    <div><s:message code="app.username" /></div>
                </div>
                <div class="span6 r-value r-signin">
                    <c:if test="${action!='warnAndSignin'}">
                    <input type="text" id="usernameSignin" name="username" value="${username}" placeholder="<s:message code="app.username.hint" />" tabindex="10">
                    </c:if>
                    <c:if test="${action=='warnAndSignin'}">
                    <select id="usernameSignin" name="username" placeholder="<s:message code="app.username.hint" />" tabindex="10">
                        <c:forEach var="item" items="${usernameList}">
                           <c:if test="${item==username}">
                           <option value="${item}" selected>${item}</option>
                           </c:if>
                           
                           <c:if test="${item!=username}">
                           <option value="${item}">${item}</option>
                           </c:if>
                        </c:forEach>
                    </select>
                    </c:if>
                </div>
            </div>
        
            <div class="row-fluid r-form-row">
                <div class="span4 r-prompt">
                    <div><s:message code="app.password" /></div>
                </div>
                <div class="span6 r-value r-signin">
                    <input type="password" id="passwordText" name="passwordText" placeholder="<s:message code="app.password.hint" />" tabindex="20">
                    <input type="hidden" id="passwordSignin" name="password">
                </div>
            </div>
            
            <div class="row-fluid r-form-row">
                <div class="span4 r-prompt">
                </div>
                <div class="span3 r-value r-signin">
                    <button type="button" id="signinAndBindSubmit" tabindex="40" class="btn btn-primary"><s:message code="app.signin" /></button>
                </div>
                <div class="span6 r-value r-signin">
                    <label class="checkbox">
                        <input type="checkbox" name="autoSignin" id="autoSignin" value="true" tabindex="30">&nbsp;
                        <s:message code="app.autosignin.statement" />
                    </label>
                </div>
            </div>
        </form:form>
        </div>
	    
	    </div>
    </div>
    
    <div class="offset-051 span6 "><!-- 第三方登录或注册 -->
    
	    <div class="${hasSocialInfo?'show':'hide'}" id="profilePanel">
	        <div class="vpad" ></div>
	        <div class="row-fluid prompt1" >
	            <div class="offset2 span1"><img src="../../common/resources/image/sina-weibo.png" class="r-cystal-button"/></div>
	            <div class="span9">使用${providerId}账号</div>
	        </div>
	        <div class="vpad" ></div>
	        <div class="row-fluid r-profile" >
	            <div class="offset2 span3"><img src="${socialImageUrl}" /></div>
	            <div class="span7"><span>${socialDisplayName}</span></div>
	        </div>
	        <div class="vpad" ></div>
	        <div class="row-fluid prompt1"  style="text-align: center;">
	            <div class="span11"><a href="javascript: showProviderPanel();">使用其它第三方账号登录或注册</a></div>
	        </div>
	        <div class="vpad" ></div>
	    </div>
	    
	    <div class="${!hasSocialInfo?'show':'hide'}" id="providerPanel">
	        <form id="bindForm" action="" method="post" target="_self"></form>    
		    <div class="vpad" ></div>
            <c:if test="${not empty error}">
                <div class="">${error}</div>
            </c:if>
		    <div class="row-fluid r-form-row">
		        <div class="offset2 span3 r-prompt">
		            <button href="../security/signwith-weibo" class="r-cystal-button" title="使新浪微博账号登录">
		            <img src="../../common/resources/image/sina-weibo.png"/>
		            </button>
		        </div>
		        <div class="offset2 span3 r-prompt">
		            <button href="../security/signwith-renren" class="r-cystal-button" title="使新人人网账号登录">
		            <img src="../../common/resources/image/renren.png"/>
		            </button>
		        </div>
		    </div>
		    <div class="row-fluid r-form-row">
		        <div class="offset2 span3 r-prompt">
		            <button href="../security/signwith-tencent" class="r-cystal-button" title="使新腾讯博账号登录">
		            <img src="../../common/resources/image/tencent-weibo.png"/>
		            </button>
		        </div>
		        <div class="offset2 span3 r-prompt">
		            <button href="../security/signwith-tencent" class="r-cystal-button" title="使新QQ空间账号登录">
		            <img src="../../common/resources/image/tencent-qzone.png"/>
		            </button>
		        </div>
		    </div>
            <div class="row-fluid r-form-row">
                <div class="offset2 span3 r-prompt">
                    <button href="../security/signwith-baidu" class="r-cystal-button">
                    <img src="../../common/resources/image/baidu.png"/>
                    </button>
                </div>
                <div class="offset2 span3 r-prompt">
                    <button href="../security/signwith-360" class="r-cystal-button">
                    <img src="../../common/resources/image/360.png"/>
                    </button>
                </div>
            </div>
		    <div class="row-fluid r-form-row">
		        <div class="offset2 span3 r-prompt">
		            <button href="../security/signwith-linkedin" class="r-cystal-button">
		            <img src="../../common/resources/image/linkedin.png"/>
		            </button>
		        </div>
		        <div class="offset2 span3 r-prompt">
		            <button href="../security/signwith-salesforce" class="r-cystal-button">
		            <img src="../../common/resources/image/salesforce.png"/>
		            </button>
		        </div>
		    </div>
<!-- 
		    <div class="row-fluid r-form-row">
		        <div class="offset2 span3 r-prompt">
		            <button href="../security/signwith-twitter" class="r-cystal-button">
		            <img src="../../common/resources/image/twitter.png"/>
		            </button>
		        </div>
		        <div class="offset2 span3 r-prompt">
		            <button href="../security/signwith-facebook" class="r-cystal-button">
		            <img src="../../common/resources/image/facebook.png"/>
		            </button>
		        </div>
		    </div>
 -->		    
		    <div class="row-fluid prompt1" style="text-align: center;">
		        <div class="span11"><a href="javascript: showProfilePanel();">使用${providerId}账号登录或注册</a></div>
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