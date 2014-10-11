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
        Rui.rs['app.email.null'] = '<s:message code="app.email.null" />';
        Rui.rs['app.email.illegal'] = '<s:message code="app.email.illegal" />';
        Rui.rs['app.email.hint'] = '<s:message code="app.email.hint" />';
        Rui.rs['app.username.existent'] = '<s:message code="app.username.existent" />';
        Rui.rs['app.username.non-existent'] = '<s:message code="app.username.non-existent" />';
    
        $(document).ready(function(){
            Rui.page.init();
            
            $("#formDiv")
            .css('width', 600).css('height', 320).position({
                    my: 'center center',
                    at: 'center center',
                    of: document.body
            })
            .css('visibility', 'visible');

            $('button').button();
            
            $("#username")
            .inlineHint( Rui.rs['app.username.hint'] );

            jQuery.validator.addMethod(
                "username", 
                function(value, element) { 
                    var isEmail = $.validateEmail( value );
                    var isMobilePhone = $.validateMobilePhone( value );
                    return this.optional(element) || (isEmail || isMobilePhone); 
                }, 
                Rui.rs['app.username.illegal']
            );
            
            $("#form1").validate({
                rules: {
                    username: {
                        required: true,
                        username: true,
                        minlength: 4,
                        maxlength: 50
                    }
                },
                submitHandler: function(form1) {
                    form1.submit();
                },
                highlight: function(element, errorClass, validClass){
                    $(element).removeClass( 'r-input-valid' ).addClass( 'r-input-invalid' );
                },
                unhighlight: function(element, errorClass, validClass){
                    $(element).removeClass( 'r-input-invalid' ).addClass( 'r-input-valid' );
                },
                errorPlacement: function(error, element) {
                    error.appendTo( element.parent().parent().parent().find( '.r-message-validate' ) );
                }
            });
            var goToSubmit = function(){
                $("#username").clearInlineHint();
                var valResult = $("#form1").valid();
                if( valResult ){
                    $("#form1").submit();
                }
                else{
                    $("#username").setInlineHint();
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
        <form:form modelAttribute="getbackPasswordInfo" id="form1" name="form1" action="getback-pwd" method="post" target="_self">
        <table width=100% height=100% class="r-signup-content" >
        <tr style="height:30px;">
            <td colspan=3>
                <span class="r-message-validate">
	                <form:errors path="username" />
	                <form:errors />
                </span>
            </td>
        </tr>
        <tr style="height:30px;">
            <td class="r-signup-prompt"><s:message code="app.username" /></td>
            <td class="r-signup-input"><input id="username" name="username" value="${username}" class="r-input" ></td>
            <td class="r-content-cell">
                <button type="button" id="submit1"><s:message code="app.getbackpwd" /></button>
            </td>
        </tr>
        <tr style="height:0px;">
            <td colspan=3 ></td>
        </tr>
        <tr style="height:30px;">
            <td colspan=3 style="text-align: center;">
            </td>
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