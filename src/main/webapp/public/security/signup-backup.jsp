<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@include file="../common/common.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><s:message code="app.name" /></title>
    <link rel="shortcut icon" href="../favicon.ico">
    <link rel="stylesheet" type="text/css" href="../resources/jquery/theme/cupertino/jquery-ui-1.8.custom.css">
    <link rel="stylesheet" type="text/css" href="../resources/css/Rui.css">
	<style type="text/css">

	</style>
    <script type="text/javascript" src="../resources/jquery/script/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="../resources/jquery/script/jquery-ui-1.8.17.custom.min.js"></script>
    <script type="text/javascript" src="../resources/jquery/script/jquery.crypt.js"></script>
    <script type="text/javascript" src="../resources/jquery/script/jquery.validate.js"></script>
    <script type="text/javascript" src="../resources/jquery/script/messages_<real:locale/>.js"></script>
    <script type="text/javascript" src="../resources/app/Rui.js"></script>
    <script type="text/javascript">
        
        Rui.rs['app.username.null'] = '<s:message code="app.username.null" />';
        Rui.rs['app.username.hint'] = '<s:message code="app.username.hint" />';
        Rui.rs['app.username.illegal'] = '<s:message code="app.username.illegal" />';
        Rui.rs['app.password.null'] = '<s:message code="app.password.null" />';
        Rui.rs['app.password.hint'] = '<s:message code="app.password.hint" />';
        Rui.rs['app.passwordconfirm.hint'] = '<s:message code="app.passwordconfirm.hint" />';
        Rui.rs['app.email.null'] = '<s:message code="app.email.null" />';
        Rui.rs['app.email.illegal'] = '<s:message code="app.email.illegal" />';
        Rui.rs['app.email.hint'] = '<s:message code="app.email.hint" />';
        Rui.rs['app.nickname.hint'] = '<s:message code="app.nickname.hint" />';
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
    
        $(document).ready(function(){
            Rui.page.init();
            $.pwdstrprompt = Rui.rs['app.pwdstr'];
            $.pwdstrmap[0] = Rui.rs['app.pwdstr.blank'];
            $.pwdstrmap[1] = Rui.rs['app.pwdstr.tooshort'];
            $.pwdstrmap[2] = Rui.rs['app.pwdstr.weak'];
            $.pwdstrmap[3] = Rui.rs['app.pwdstr.medium'];
            $.pwdstrmap[4] = Rui.rs['app.pwdstr.strong'];
            $.pwdstrmap[5] = Rui.rs['app.pwdstr.verystrong'];
            
            $('#signupDiv').tabs({});
            $('button').button();
            $("#signupDiv").css('width', 600).css('height', 320).position({
                    my: 'center center',
                    at: 'center center',
                    of: document.body
            }).css('visibility', 'visible');
            
            var checkUsernameExistence = function( el, callback ){
                var value = el.val() || '';
                var history = el.data('history');
                var working = el.data('working');

                value = $.trim( value );
                if(!history){
                    history = {};
                    el.data('history', history);
                }
                
                //check if the el is using inline hint, if yes, no need to check
                var inlineHint = el.data( 'inlineHint' );
                if( inlineHint && inlineHint.blank ){
                    return false;
                }
                
                //check if the el is blank, if yes, no need to check
                if( value==='' ){
                    return false;//have no need to check
                }
                else if( value!=='' && history[value] ){
                    callback( history[value].available );
                    return true;
                }
                
                if( working ){
                    return false;//have no need to check
                }
                else{
                    el.data('working', true);
                }
                var username = value;
                $.getJSON('../pp/check-username', { 'username': username }, function(response, status, xhr){
                    if( response.success ){
                        el.data('history')[value] = { 'available': response.result.available };
	                    callback( response.result.available );
                    }
                    else{
                        //TODO: handle exception
                        alert( response.message );
                    }
                    el.data('working', false);
                });
                return true;
            }

            var changeUsernameHint = function( available ){
                if( available===undefined ){
                    $("#usernameHint").html('');
                }
                else if( available===true ){
                    $( "#usernameHint" ).html( Rui.rs['app.username.non-existent'] ).addClass( 'r-username-ok' );
                    $( "#usernameError" ).html( '' );
                    $( "#username" ).removeClass( 'r-input-invalid' ).addClass( 'r-input-valid' );
                }
                else{
                    $( "#usernameError" ).html( Rui.rs['app.username.existent'] ).removeClass( 'r-username-ok' );
                    $( "#username" ).removeClass( 'r-input-valid' ).addClass( 'r-input-invalid' );
                }
            };
            
            $("#username")
            .inlineHint( Rui.rs['app.username.hint'] )
            .keyup(function(){
                changeUsernameHint();
            })
            .blur(function(){
                var el = $(this);
                var emailEl = $("#email");
                var isEmail = $.validateEmail( el.val() );
                if( isEmail && emailEl.data( 'inlineHint' ).blank ){
                    emailEl.data( 'inlineHint' ).blank = false;
                    emailEl.clearInlineHint().val( el.val() );
                }
                if( $(this).hasClass( 'r-input-valid' ) ){
				    checkUsernameExistence( $(this), changeUsernameHint );
                }
            });
            
            $("#passwordFirst")
            .inlineHint( Rui.rs['app.password.hint'] )
            .pwdstr( 'passwordStrength' );
            
            $("#passwordSecond")
            .inlineHint( Rui.rs['app.passwordconfirm.hint'] );

            $("#email")
            .inlineHint( Rui.rs['app.email.hint'] );
            
            jQuery.validator.addMethod(
                "username", 
                function(value, element) { 
	                var isEmail = $.validateEmail( value );
	                var isMobilePhone = $.validateMobilePhone( value );
	                return this.optional(element) || (isEmail || isMobilePhone); 
				}, 
				Rui.rs['app.username.illegal']
			);

            $("#signupForm").validate({
                rules: {
                    username: {
                        required: true,
                        username: true,
                        minlength: 4,
                        maxlength: 50
                    },
                    passwordFirst: {
                        required: true,
                        minlength: 4,
                        maxlength: 50
                    },
                    passwordSecond: {
                        required: true,
                        equalTo: '#passwordFirst'
                    },
                    email: {
                        required: true,
                        email: true,
                        minlength: 5,
                        maxlength: 50
                    },
                    agreeAgreement: {
                        required: true
                    }
                },
                messages: {
                    username: {
                        username: Rui.rs['app.username.illegal'],
                    }
                },
                submitHandler: function(form) {
                    $(form).find( 'input:password' ).val( '' );
                    form.submit();
                },
                
                highlight: function(element, errorClass, validClass){
                    $(element).removeClass( 'r-input-valid' ).addClass( 'r-input-invalid' );
                },
                unhighlight: function(element, errorClass, validClass){
                    $(element).removeClass( 'r-input-invalid' ).addClass( 'r-input-valid' );
                },
                errorPlacement: function(error, element) {
                    if ( element.is(":radio") ){
                        error.appendTo( element.parent().next().next() );
                    }
                    else if ( element.is(":checkbox") ){
                        error.appendTo( element.parent().parent().parent().find( '.r-message-validate' ) );
                    }
                    else{
                        error.appendTo( element.parent().parent().find( '.r-message-validate' ) );
                    }
                }
            });

            var goToSignup = function(){
                $("#passwordFirst").syncSHA1( '#password' );

                $("#username").clearInlineHint();
                $("#passwordFirst").clearInlineHint();
                $("#passwordSecond").clearInlineHint();
                $("#email").clearInlineHint();
                $("#displayName").clearInlineHint();
                
                var valResult = $("#signupForm").valid();
                if( valResult ){
                    $("#signupForm").submit();
                }
                else{
	                $("#username").setInlineHint();
	                $("#passwordFirst").setInlineHint();
	                $("#passwordSecond").setInlineHint();
	                $("#email").setInlineHint();
	                $("#displayName").setInlineHint();
                }
            };
            
            $("#signupSubmit").click(function(e){
                goToSignup();
            });

            $("#tempSignupSubmit")
            .click(function(e){
                $("#tempSignupForm").submit();
                alert('');
            });
            
            $('#agreementLink').click(function(){
                $('#signupDiv').tabs('select', 1);
            });
            
            $('#downloadAgreement').click(function(){
                window.open('agreement.txt', '_blank');
            });
            
            Rui.quickAccess();
            Rui.focus('username');
	    });
    </script>
</head>
<body>
<table width=100% height=100% class="r-table-layout">
<tr>
    <td>
		<jsp:include page="../portal/head.jsp" />
    </td>
</tr>
<tr>
    <td style="height:100%;text-align:center;vertical-align:middle;">

<div id="signupDiv" style="visibility:hidden;width:600px;height:350px;">
    <ul> 
      <li><a href="#signupTab" class="r-web-link"><s:message code="app.registration" /></a></li>
      <li><a href="#tempSignupTab" class="r-web-link"><s:message code="app.registration.temp" /></a></li>
      <li><a href="#agreementTab" class="r-web-link"><s:message code="app.agreement" /></a></li> 
    </ul>
    <div id="signupTab">
        <form:form modelAttribute="signupInfo" id="signupForm" action="signup" method="post" target="_self">
        <input type="hidden" name="targetUrl" value="${targetUrl}">
        <table width=100% height=100% class="r-signup-content" >
        <tr style="height:30px;">
            <td class="r-signup-prompt"></td>
            <td colspan=2>
                <span class="r-message-validate">
                    <form:errors />
                </span>
            </td>
        </tr>
        <tr style="height:30px;">
            <td class="r-signup-prompt"><s:message code="app.username" /></td>
            <td class="r-signup-input"><input type="text" id="username" name="username" value="${username}" class="r-input" ></td>
            <td class="r-content-cell">
                <span id="usernameError" class="r-message-validate"><form:errors path="username" /></span><br>
                <span id="usernameHint" class="r-message-block"></span>
            </td>
        </tr>
        <tr style="height:30px;">
            <td class="r-signup-prompt"><s:message code="app.password" /></td>
            <td class="r-signup-input" style="height:50px;"><input type="password" id="passwordFirst" name="passwordFirst" class="r-input" ></td>
            <td class="r-content-cell">
                <span class="r-message-validate"><form:errors path="password" /></span><br>
                <span id="passwordHint" class="r-pwd-strength-hint"></span>
            </td>
        </tr>
        <tr style="height:30px;">
            <td class="r-signup-prompt"><s:message code="app.passwordconfirm" /></td>
            <td class="r-signup-input">
	            <input type="password" id="passwordSecond" name="passwordSecond" class="r-input" >
                <input type="hidden" name="password" id="password" >
                <input type="hidden" name="passwordStrength" id="passwordStrength" >
            </td>
            <td class="r-content-cell">
                <span class="r-message-validate"></span>
            </td>
        </tr>
        <tr style="height:30px;">
            <td class="r-signup-prompt"><s:message code="app.email" /></td>
            <td class="r-signup-input">
                <input type="email" id="email" name="email" value="${email}" class="r-input">
            </td>
            <td class="r-content-cell">
                <span class="r-message-validate"><form:errors path="email" /></span>
            </td>
        </tr>
<!-- 
        <tr>
            <td class="r-signin-prompt"><s:message code="app.nickname" /></td>
            <td class="r-signin-input"><input type=text id="displayName" name="displayName" class="r-input"></td>
            <td>
                <span class="r-message-block"></span>
            </td>
        </tr>
 -->        
        <tr style="height:30px;">
            <td class="r-signup-prompt"></td>
            <td colspan=1 class="r-signup-prompt">
                <span style="height:30px; line-height:30px; vertical-align: middle; margin: 3px; float:left;"><input type="checkbox" name="agreeAgreement" id="agreeAgreement" value="true" checked="${agreeAgreement}" class="r-checkbox"></span>
                <span style="height:30px; line-height:30px; vertical-align: middle; padding-left:2px;">
	                <s:message code="app.agreement.agree" />
	                <a href="javascript:" id="agreementLink"><s:message code="app.agreement" /></a>
                </span>
            </td>
            <td>
                <span class="r-message-validate"></span>
            </td>
        </tr>
        <tr style="height:0px;">
            <td colspan=3 ></td>
        </tr>
        <tr style="height:30px;">
            <td colspan=3 style="text-align: center;"><button type="button" id="signupSubmit"><s:message code="app.signup" /></button></td>
        </tr>
        </table>
        </form:form>
    </div>
    <div id="tempSignupTab">
        <form id="tempSignupForm" action="tempSignup" method="post" target="_self">
        <table width=100% height=100% class="r-signin-content">
        <tr class="r-message-block">
            <td colspan=3 style="height:30px;" >
                <form:errors path="lastName"  />
            </td>
        </tr>
        <tr>
            <td class="r-signin-prompt"><s:message code="app.nickname" /></td>
            <td class="r-signin-input"><input type=text name="displayName1" value="${caName}" class="r-input"></td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td class="r-signin-prompt"></td>
            <td colspan=2 class="r-signin-prompt">
                <span style="height:30px; line-height:30px; vertical-align: middle; margin: 3px; float:left;"><input type="checkbox" name="autoSignin" id="autoSignin" value="true" class="r-checkbox"></span>
                <span style="height:30px; line-height:30px; vertical-align: middle; padding-left:2px;"><s:message code="app.autosignin.statement" /></span>
            </td>
        </tr>
        <tr>
            <td colspan=3 >&nbsp;</td>
        </tr>
        <tr>
            <td colspan=3 >&nbsp;</td>
        </tr>
        <tr>
            <td colspan=3 style="text-align: center;"><button type="button" id="tempSignupSubmit"><s:message code="app.signin"/></button></td>
        </tr>
        </table>
        </form>
    </div>
    <div id="agreementTab">
        <iframe src="<s:message code="app.agreement.uri" />" scrolling="auto" class="r-html" style="width:100%;height:220px;" ></iframe>
        <a href="<s:message code="app.agreement.uri" />" class="r-link" target="_blank"><s:message code="app.agreement.download" /></a>
    </div>
</div>

    </td>
</tr>
<tr>
    <td></td>
</tr>
<tr>
    <td>
        <%@include file="../portal/foot.jsp" %>
    </td>
</tr>
</table>
</body>
</html>