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
    <script type="text/javascript" src="../resources/jquery/script/jquery.cookie.js"></script>
    <script type="text/javascript" src="../resources/app/Rui.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            Rui.page.init();
            $('#signinDiv').tabs({});
            $('button').button();
            $("#signinDiv").css('width', 600).css('height', 300).position({
                    my: 'center center',
                    at: 'center center',
                    of: document.body
            }).css('visibility', 'visible');

            //Init autoSignin checkbox state
            var autoSignin = $.cookie('autoSigninFlag');
            if( autoSignin=='false' ){
                $("#autoSignin").attr( 'checked', false );
            }
            else{//check it if autoSignin is set to true, or check it by default if it is not set
	            $("#autoSignin").attr( 'checked', true );
            }
            
            var goToSignin = function(){
                $("#passwordText").syncSHA1( '#password' );
                var autoSigninFlag = $("#autoSignin:checkbox:checked").length > 0 ? 'true' : 'false';
                $.cookie( 'autoSigninFlag', autoSigninFlag, Rui.cookieOptions ); //Save autoSigninFlag state to cookie
                $("#signinForm").submit();
            };
            
            $("#signinSubmit").click(function(e){
                goToSignin();
            });
            
            $("#signinTab").keydown(function(e){
                var key = e.which;
                if (key == 13) {
                    goToSignin();
                }
            });
            
            //$("#passwordText").focus();
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
    <real:signin/>
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