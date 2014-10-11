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
    <script type="text/javascript" src="../resources/jquery/script/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="../resources/jquery/script/jquery-ui-1.8.17.custom.min.js"></script>
    <script type="text/javascript" src="../resources/jquery/script/jquery.crypt.js"></script>
    <script type="text/javascript" src="../resources/jquery/script/jquery.validate.js"></script>
    <script type="text/javascript" src="../resources/jquery/script/messages_<rui:locale/>.js"></script>
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
            
/*
            .dialog({
                autoOpen: false,
                closeText: 'Close',
                title: 'Reset Password',
                position: 'center',
                maxWidth: 800,
                maxHeight: 600,
                minWidth: 660,
                minHeight: 400,
                open: function(){
                }
            })

*/
            $("#formDiv")
            .dialog('open')
            .css('width', 600).css('height', 320).position({
                    my: 'center center',
                    at: 'center center',
                    of: document.body
            })
            .css('visibility', 'visible');

            $('button').button();
            
            $("#passwordFirst")
            .inlineHint( Rui.rs['app.password.hint'] )
            .pwdstr( 'passwordStrength' );
            
            $("#passwordSecond")
            .inlineHint( Rui.rs['app.passwordconfirm.hint'] );

            $("#form1").validate({
                rules: {
                    passwordFirst: {
                        required: true,
                        minlength: 4,
                        maxlength: 50
                    },
                    passwordSecond: {
                        required: true,
                        equalTo: '#passwordFirst'
                    }
                },
                message: {
                    username: {
                        required: Rui.rs['app.username.null'],
                        minlength: '字符最少不能低于2个'
                    },
                    email: {
                        required: Rui.rs['app.email.null'],
                        email: Rui.rs['app.email.illegal']
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
                    error.appendTo( element.parent().parent().find( '.r-message-validate' ) );
                }
            });
            
            var goToSubmit = function(){
                $("#passwordFirst").syncSHA1( '#password' );
                $("#passwordFirst").clearInlineHint();
                $("#passwordSecond").clearInlineHint();
                
                var valResult = $("#form1").valid();
                if( valResult ){
                    $("#form1").submit();
                }
                else{
                    $("#passwordFirst").setInlineHint();
                    $("#passwordSecond").setInlineHint();
                }
            };
            
            $("#submit1").click(function(e){
                goToSubmit();
            });
            
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
        <div id="formDiv">
        <form:form modelAttribute="resetPasswordInfo" id="form1" action="reset-pwd" method="post" target="_self">
        <table width=100% height=100% class="r-signup-content" >
        <tr style="height:30px;">
            <td colspan=3 >
                <span class="r-message-validate">
	                <form:errors path="username" />
	                <form:errors />
                </span>
            </td>
        </tr>
        <tr style="height:30px;">
            <td class="r-signup-prompt"><s:message code="app.username" /></td>
            <td class="r-signup-input">
	            <label>${username}</label>
	            <input type="hidden" name="username" id="username" value="${username}">
            </td>
            <td class="r-content-cell">
                <span class="r-message-validate"></span><br>
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
        <tr style="height:0px;">
            <td colspan=3 ></td>
        </tr>
        <tr style="height:30px;">
            <td colspan=3 style="text-align: center;"><button type="button" id="submit1"><s:message code="app.resetpwd" /></button></td>
        </tr>
        </table>
        </form:form>
        </div>
    </td>
</tr>
<tr>
    <td>
        <%@include file="../portal/foot.jsp" %>
    </td>
</tr>
</table>
</body>
</html>