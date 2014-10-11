<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@include file="../common/common.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title><s:message code="app.title" /></title>
    <link rel="shortcut icon" href="../favicon.ico">
    <link rel="stylesheet" href="../resources/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="../resources/bootstrap/css/bootstrap-responsive.css">
    <!-- 
    <link rel="stylesheet" href="../resources/jquery/theme/cupertino/jquery-ui-1.8.custom.css">
    -->
    <link rel="stylesheet" href="../resources/rui/css/rui.css">
    <style type="text/css">

    </style>

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <script src="../resources/jquery/script/jquery-1.7.1.js"></script>
    <script src="../resources/bootstrap/js/bootstrap.js"></script>
    <script src="../resources/jquery/script/jquery-ui-1.8.17.custom.min.js"></script>
    <script src="../resources/jquery/script/jquery.crypt.js"></script>
    <script src="../resources/jquery/script/jquery.cookie.js"></script>
    <script type="text/javascript" src="../resources/jquery/script/jquery.validate.js"></script>
    <script type="text/javascript" src="../resources/jquery/script/messages_<rui:locale/>.js"></script>
    <script src="../resources/rui/js/rui.js"></script>
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
            
        $(document).ready(function(){
            Rui.page.init();
            
            $.pwdstrprompt = Rui.rs['app.pwdstr'];
            $.pwdstrmap[0] = Rui.rs['app.pwdstr.blank'];
            $.pwdstrmap[1] = Rui.rs['app.pwdstr.tooshort'];
            $.pwdstrmap[2] = Rui.rs['app.pwdstr.weak'];
            $.pwdstrmap[3] = Rui.rs['app.pwdstr.medium'];
            $.pwdstrmap[4] = Rui.rs['app.pwdstr.strong'];
            $.pwdstrmap[5] = Rui.rs['app.pwdstr.verystrong'];
            
            $("#formDiv")
            .dialog('open')
            .css('width', 600).css('height', 320).position({
                    my: 'center center',
                    at: 'center center',
                    of: document.body
            })
            .css('visibility', 'visible');

            $('button').button();
            
            $("#oldPasswordText")
            .inlineHint( Rui.rs['app.password.hint'] );

            $("#passwordFirst")
            .inlineHint( Rui.rs['app.password.hint'] )
            .pwdstr( 'newPasswordStrength' );
            
            $("#passwordSecond")
            .inlineHint( Rui.rs['app.passwordconfirm.hint'] );

            $("#form").validate({
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
                $("#oldPasswordText").syncSHA1( '#oldPassword' );
                $("#passwordFirst").syncSHA1( '#newPassword' );
                $("#oldPasswordText").clearInlineHint();
                $("#passwordFirst").clearInlineHint();
                $("#passwordSecond").clearInlineHint();
                
                var valResult = $("#form").valid();
                if( valResult ){
                    $("#form").submit();
                }
                else{
                    $("#oldPasswordText").setInlineHint();
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
<div>
<%@include file="../portal/head-common.jsp" %>

<div class="row-fluid">
<div class="offset2 span8 r-signin-page " >

    <div class="r-pad" ></div>
    <h3 class="r-split-h"><s:message code="app.signin" /></h3>
    
    <div class="row-fluid" >
    <div class="span7"><div class="container-fluid" >
        <form:form modelAttribute="changePasswordInfo" id="form" action="../security/change-pwd" method="post" target="_self">
        <table width=100% height=100% class="r-signup-content" >
        <tr style="height:30px;">
            <td colspan=3 >
                <span class="r-message-validate">
                    <form:errors path="username"/>
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
            <td class="r-signup-prompt"><s:message code="app.oldpassword" /></td>
            <td class="r-signup-input" style="height:50px;"><input type="password" id="oldPasswordText" name="oldPasswordText" class="r-input" ></td>
            <td class="r-content-cell">
                <span class="r-message-validate"><form:errors path="oldPassword" /></span><br>
                <input type="hidden" name="oldPassword" id="oldPassword" >
            </td>
        </tr>
        <tr style="height:30px;">
            <td class="r-signup-prompt"><s:message code="app.password" /></td>
            <td class="r-signup-input" style="height:50px;"><input type="password" id="passwordFirst" name="passwordFirst" class="r-input" ></td>
            <td class="r-content-cell">
                <span class="r-message-validate"><form:errors path="newPassword" /></span><br>
                <span id="passwordHint" class="r-pwd-strength-hint"></span>
            </td>
        </tr>
        <tr style="height:30px;">
            <td class="r-signup-prompt"><s:message code="app.passwordconfirm" /></td>
            <td class="r-signup-input">
                <input type="password" id="passwordSecond" name="passwordSecond" class="r-input" >
                <input type="hidden" name="newPassword" id="newPassword" >
                <input type="hidden" name="newPasswordStrength" id="newPasswordStrength" >
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
    </div></div>
    
    </div>
    
</div>
</div>
    
<%@include file="../portal/foot-common.jsp" %>
</div><!-- top container -->
</body>
</html>