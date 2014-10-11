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
    <script src="../resources/rui/js/rui.js"></script>
    <script>
$(document).ready(function(){
    var initAction = $('#initAction').val();
        
    $.pwdstrprompt = Rui.rs['app.pwdstr'];
    $.pwdstrmap[0] = Rui.rs['app.pwdstr.blank'];
    $.pwdstrmap[1] = Rui.rs['app.pwdstr.tooshort'];
    $.pwdstrmap[2] = Rui.rs['app.pwdstr.weak'];
    $.pwdstrmap[3] = Rui.rs['app.pwdstr.medium'];
    $.pwdstrmap[4] = Rui.rs['app.pwdstr.strong'];
    $.pwdstrmap[5] = Rui.rs['app.pwdstr.verystrong'];
    
    //TODO:
    var changeUsernameHint = function( available ){
        if( available===true ){
            $( "#usernameHint" ).html( Rui.rs['app.username.non-existent'] ).addClass( 'r-username-ok' );
            $( "#usernameError" ).html( '' );
            $( "#usernameSignup" ).removeClass( 'r-input-invalid' ).addClass( 'r-input-valid' );
        }
        else{
            $( "#usernameError" ).html( Rui.rs['app.username.existent'] ).removeClass( 'r-username-ok' );
            $( "#usernameSignup" ).removeClass( 'r-input-valid' ).addClass( 'r-input-invalid' );
        }
    };

    var resetFieldHint = function( inputId, inputMsgId ){
        $('#' + inputId).removeClass( 'r-input-valid' ).removeClass( 'r-input-invalid' );
        $('#' + inputMsgId).html('');
    };

    var resetUsernameSignupHint = function(){
        resetFieldHint( 'usernameSignup', 'usernameSignupHint' );
    };
    
    var checkUsernameSignupValidity = function( el ){
        var valid = true;
        var isEmail = $.validateEmail( el.val() );
        valid = valid && isEmail;
        return valid;
    };

    $("#usernameSignup")
    .keyup(function(){
        resetUsernameSignupHint();
    })
    .blur(function(){
        var el = $(this);
        var isEmail = $.validateEmail( el.val() );
        var valid = checkUsernameSignupValidity( el );
        if( isEmail ){
            $("#email").val( el.val() );
        }

        if( valid ){
            Rui.checkUsernameExistence( $(this), changeUsernameHint );
        }
    });
    
    $("#passwordFirst")
    .pwdstr( 'passwordStrength' )
    .blur(function(){
        var el = $(this);
        $("#passwordSecond").val( el.val() );
    });
    
    jQuery.validator.addMethod(
        "username", 
        function(value, element) { 
            var isEmail = $.validateEmail( value );
            var isMobilePhone = $.validateMobilePhone( value );
            return this.optional(element) || (isEmail || isMobilePhone); 
        }, 
        Rui.rs['app.username.illegal']
    );
    
    $("#signupAndBindForm").validate({
        rules: {
            username: {
                required: true,
                username: true,
                minlength: 6,
                maxlength: 50
            },
            passwordFirst: {
                required: true,
                minlength: 5,
                maxlength: 50
            },
            passwordSecond: {
                required: true,
                equalTo: '#passwordFirst'
            },
            email: {
                required: true,
                email: true,
                minlength: 6,
                maxlength: 50
            },
            agreeAgreement: {
                required: true
            }
        },
        messages: {
            usernameSignup: {
                username: Rui.rs['app.username.illegal'],
            }
        },
        submitHandler: function(form) {
            form.submit();
        },
        highlight: function(element, errorClass, validClass){
            $(element).removeClass( 'input-success' ).addClass( 'input-error' );
            //element.attr( 'title', null );
        },
        unhighlight: function(element, errorClass, validClass){
            $(element).removeClass( 'input-error' ).addClass( 'input-success' );
            //element.attr( 'title', null );
        },
        errorPlacement: function(error, element) {
            if ( element.is(":radio") ){
                //error.appendTo( element.parent().next().next() );
                error.appendTo( $( '#signupAndBindForm .r-message-validate' ) );
            }
            else if ( element.is(":checkbox") ){
                error.appendTo( $( '#signupAndBindForm .r-message-validate' ) );
            }
            else{
                var msg = element.parent().prev().text() + ': ' + error.html();
                element.attr( 'title', msg );
                error.html( msg );
                error.appendTo( $( '#signupAndBindForm .r-message-validate' ) );
            }
        }
    });
    
    var goToSignupAndBind = function(){
        $("#passwordFirst").syncSHA1( '#passwordSignup' );
        var formValid = true;
        $('#signupAndBindForm div.r-value input[type="text"]').each(function(index, dom){
            if( !$(dom).valid ){ return true; }
            var elValid = $(dom).valid();
            formValid = formValid && elValid;
            return formValid;
        });
        
        if( formValid ){
            $("#signupAndBindForm").submit();
        }
    };
    
    $("#signupAndBindSubmit").click(function(e){
        goToSignupAndBind();
    });
    
    $("#usernameSignup").keydown(function(e){
        var key = e.which;
        if (key == 13) {
            $("#passwordFirst").focus();
        }
    });
    
    $("#passwordFirst").keydown(function(e){
        var key = e.which;
        if (key == 13) {
            goToSignupAndBind();
        }
    });
    

    //Init autoSignin checkbox state
    var autoSignin = $.cookie('autoSigninFlag');
    if( autoSignin=='false' ){
        $("#autoSignin").attr( 'checked', false );
    }
    else{//check it if autoSignin is set to true, or check it by default if it is not set
        $("#autoSignin").attr( 'checked', true );
    }
    
    var signinRules = {
            passwordText: {
                required: true,
                minlength: 5,
                maxlength: 50
            }
    };
    if( initAction!='warnAndSignin' ){
        signinRules.username = {
                required: true,
                username: true,
                minlength: 6,
                maxlength: 50
            };
    }
    
    $("#signinAndBindForm").validate({
        rules: signinRules,
        messages: {
            username: {
                username: Rui.rs['app.username.illegal'],
            }
        },
        submitHandler: function(form) {
            form.submit();
        },
        highlight: function(element, errorClass, validClass){
            $(element).removeClass( 'input-success' ).addClass( 'input-error' );
            $(element).attr( 'title', null );
        },
        unhighlight: function(element, errorClass, validClass){
            $(element).removeClass( 'input-error' ).addClass( 'input-success' );
            //$(element).attr( 'title', null );
        },
        errorPlacement: function(error, element) {
            if ( element.is(":radio") ){
                error.appendTo( $( '#signinAndBindForm .r-message-validate' ) );
            }
            else if ( element.is(":checkbox") ){
                error.appendTo( $( '#signinAndBindForm .r-message-validate' ) );
            }
            else{
            /*
	         */   
	            var msg = element.parent().prev().text() + ': ' + error.html();
	            element.attr( 'title', msg );
	            error.html( msg );
                error.appendTo( $( '#signinAndBindForm .r-message-validate' ) );
            }
        }
    });
    
    var goToSigninAndBind = function(){
        $("#passwordText").syncSHA1( '#passwordSignin' );
        var autoSigninFlag = $("#autoSignin:checkbox:checked").length > 0 ? 'true' : 'false';
        $.cookie( 'autoSigninFlag', autoSigninFlag, Rui.cookieOptions ); //Save autoSigninFlag state to cookie
        
        var formValid = true;
         if( $("#signinAndBindForm #action").val()!='warnAndSignin'){
            formValid = formValid && $('#usernameSignin').valid();
         }
        formValid = formValid && $('#passwordText').valid();
        
        if( formValid ){
            $("#signinAndBindForm").submit();
        }
    };
    
    $("#signinAndBindSubmit").click(function(e){
        goToSigninAndBind();
    });
    
    $("#usernameSignin").keydown(function(e){
        var key = e.which;
        if (key == 13) {
            $("#passwordText").focus();
            return false;
        }
    });
    $("#passwordText").keydown(function(e){
        var key = e.which;
        if (key == 13) {
            goToSigninAndBind();
            return false;
        }
    });
    
    if( initAction=='signupAndBind' ){
        $('#usernameSignup').focus();
    }
    else if( initAction=='signinAndBind' || initAction=='warnAndSignin' ){
        $('#usernameSignin').focus();
    }
    
    $('a.signupAndBind').click(function(){
        $('#usernameSignup').focus();
    });
    
    $('a.signinAndBind').click(function(){
        $('#usernameSignin').focus();
    });

    Rui.page.init();
});
    </script>
</head>
<body>
<div>
<c:set var="signinPage" value="true" scope="request"/>
<c:set var="signupPage" value="true" scope="request"/>
<c:set var="signwithPage" value="true" scope="request"/>
<%@include file="../portal/head-common.jsp" %>

<div class="row-fluid">
<div class="offset2 span8 signwith-page " >
    <div class="r-panel">
    <!-- 
    <div class="padding" ></div>
    <h3 class="hsplit">第三方登录</h3>
     -->
    <rui:signwith-form/>
    </div>
</div>
</div>

<%@include file="../portal/foot-common.jsp" %>
</div><!-- top container -->
</body>
</html>