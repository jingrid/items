/* 
 * Copyright section: TODO
 */

/* 
 * RUI: General RUI behaviors and conventions binding
 */
(function($){
    $.fn.rui = function( hint ){
        return this.each(function(){
            var el = $(this);
            if( el.data( 'inlineHint' ).blank || el.hasClass( 'r-hint-inline' )){
                el.val( '' );
            }
            el.removeClass( 'r-hint-inline' );
        });
    };
    $.enableButtonLinks = function(){
        $('body').on('click.button[href][target].btn', '[href][target].btn', function ( e ) {
            var oThis = $(this), href = oThis.attr('href'), target = oThis.attr('target');
            e.preventDefault();
            if( target=='_self' ){
                window.location.href = href;
            }
            else{
                window.location.href = href;
                //TODO: 动态target
            }
        });
    };

    $.enableForms = function(){
        $('.input.required > input').attr('required', 'required');
    };
})(jQuery);


$(document).ready(function(){
    $.enableButtonLinks();
    $.enableForms();
});

(function($){
    $.pwdstrprompt = 'Password Strength';
    $.pwdstrmap = ['Blank','Too short', 'Weak', 'Medium', 'Strong', 'Very Strong'];
    $.pwdstrscore = [0, 20, 40, 60, 80, 100];
    $.pwdstrcolor = ['r-pwd-blank','r-pwd-weak', 'r-pwd-weak', 'r-pwd-medium', 'r-pwd-strong', 'r-pwd-verystrong'];
    $.pwdstrcheck = function( pwd ){
        /*
           0: blank
           1: too short: length<5
           2: weak : only includes letter or number
           3: medium : includes letter and number without upper or lower letter
           4: strong : includes letter (upper and lower) and number without special letter
           5: verystrong : includes letter (upper and lower), number and special letter
        */
        
        if( pwd == ''){ //password is blank
            return 0;
        }
        else if( pwd.length < 6 ) { //password length is less than 6 characters
            return 1;
        }
        
        var strength = 1;
        var includesLowerLetter = pwd.match(/[a-z]+/);
        var includesUpperLetter = pwd.match(/[A-Z]+/);
        var includesNumber = pwd.match(/[0-9]+/);
        var includesSpecialChar = pwd.match(/[^a-zA-Z0-9]/);
        
        if( includesLowerLetter ) strength++;
        if( includesUpperLetter ) strength++;
        if( includesNumber ) strength++;
        if( includesSpecialChar ) strength++;
        return strength;
    };

    $.validateUsername = function( value ){
        return /^[A-Za-z]+[A-Za-z0-9_\-]*$/i.test(value);
    };

    $.validateMobilePhone = function( value ){
        return /^((13[0-9])|(15[^4,\D])|(18[0,5-9]))\d{8}$/i.test(value);
    };

    $.validateEmail = function( value ){
        return /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i.test(value);
    };

    $.fn.pwdstr = function( passwordStrengthId ){
        return this.each(function(){
            var el = $(this);
            var strengthStripFrameDiv = $( '<div></div>' );
            strengthStripFrameDiv.addClass( 'r-pwd-strength-strip' );
            strengthStripFrameDiv.insertAfter( el );

            var strengthStripDiv = $( '<div>&nbsp;</div>' );
            strengthStripDiv.addClass( 'r-pwd-strength-strip' );
            strengthStripDiv.appendTo( strengthStripFrameDiv );
            
            el.keyup(function(){
                var strength = $.pwdstrcheck( this.value );
                //$( '#'+hintId ).html( $.pwdstrprompt + ' - ' + $.pwdstrmap[strength] );
                $( '#'+passwordStrengthId ).val( ''+$.pwdstrscore[strength] );
                var lastClass = strengthStripDiv.data( 'lastClass' );
                if( lastClass ){
                    strengthStripDiv.removeClass( lastClass );
                }
                strengthStripDiv.addClass( $.pwdstrcolor[strength] ).data( 'lastClass', $.pwdstrcolor[strength] );
                strengthStripDiv.css( 'width', ''+$.pwdstrscore[strength]+'%' );
            });
        });
    };
    
    $.fn.unselectable = function( hint ){
        return this.each(function(){
            $(this).on('selectstart dragstart', function(e){ e.preventDefault(); return false; });
        });
    };
    
    $.fn.inlineHint = function( hint ){
        return this.each(function(){
            var el = $(this);
            var blank = this.value=='';
            var type = el.attr('type');
            
            //if browser is Internet Explorer, cancel password inline hint
            if( type=='password' && $.browser.msie ){
                return;
            }
            
            var addInlineHint = function(){
                var el = $(this);
                el.val( el.data( 'inlineHint' ).hint ).addClass( 'r-hint-inline' );
            };
            var removeInlineHint = function(){
                var el = $(this);
                if( el.hasClass( 'r-hint-inline' ) && el.data( 'inlineHint' ).blank ){
                    el.val( '' );
                }
                el.removeClass( 'r-hint-inline' );
            };
            var togglePwdType = function(){
                var el = $(this);
                var type = el.attr('type');
                if( type=='password' ){
                    if( $(this).data('inlineHint').blank ){
                        $(this)[0].type = 'text';
                    }
                }
            };
            
            el.data( 'inlineHint', { 'blank': blank, 'hint': hint } );
            togglePwdType.call( this );
            if( blank ){
                addInlineHint.call( this );
            }
            
            if( type=='password' ){
                el.focusin(function(){
                    $(this)[0].type = type; 
                })
                .focusout(function(){
                    if( $(this).data('inlineHint').blank ){
                        $(this)[0].type = 'text';
                    }
                });
            }
            
            el.keyup(function(){
                var el = $(this);
                var blank = el.val()=='';
                el.data( 'inlineHint' ).blank = blank;
            })
            .focusin(function(){
                removeInlineHint.call( this );
            })
            .blur(function(e){
                var el = $(this);
                var blank = el.val()=='';
                el.data( 'inlineHint' ).blank = blank;   
                if( blank ){
                    addInlineHint.call( this );
                    e.stopImmediatePropagation();
                }
            });
        });
    };
    
    $.fn.clearInlineHint = function( hint ){
        return this.each(function(){
            var el = $(this);
            if( el.data( 'inlineHint' ).blank || el.hasClass( 'r-hint-inline' )){
                el.val( '' );
            }
            el.removeClass( 'r-hint-inline' );
        });
    };

    $.fn.setInlineHint = function( hint ){
        return this.each(function(){
            var el = $(this);
            var blank = el.val()=='blankpassword';
            if( el.data( 'inlineHint' ).blank ){
                //el.data( 'inlineHint' ).blank = true;
                el.val( el.data( 'inlineHint' ).hint ).addClass( 'r-hint-inline' );            
            }
        });
    };

    $.fn.syncMD5 = function( toSelector ){
        return this.each(function(){
            var el = $(this);
            var sourceOld = el.data( 'source' );
            var sourceNew = el.val();
            if( sourceNew=='' || sourceOld==sourceNew ){
                return;
            }
            el.data( 'source', sourceNew );
            var digest = $().crypt({method: "md5", source: sourceNew});
            $( toSelector ).val( digest );
        });
    };
    
    $.fn.syncSHA1 = function( toSelector ){
        return this.each(function(){
            var el = $(this);
            var sourceOld = el.data( 'source' );
            var sourceNew = el.val();
            if( sourceNew=='' || sourceOld==sourceNew ){
                return;
            }
            el.data( 'source', sourceNew );
            var digest = $().crypt({method: "sha1", source: sourceNew});
            $( toSelector ).val( digest );
        });
    };
    
})(jQuery);

if(!window.Rui){
    window.Rui = {};
    window.Rui.page = {};
    window.Rui.rs = {};
}

Rui.cookieOptions = {
    expires: 365,
    path: '/',
    domain: '.realsaas.com',
    secure: true
};
    
Rui.post = function(url, data){
    $.post( url, data ,function( result ){
        alert( result );
    });
};

Rui.get = function(url, data){
    $.get( url, data ,function( result ){
        alert( result );
    });
};

Rui.removeCookies = function(){
    var cookies = document.cookie.split(";");
    var now = new Date();
    now.setTime(now.getTime() - 1);
    var agoGMT = now.toGMTString();
    var cookieSting = "";
    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i];
        var eqPos = cookie.indexOf("=");
        var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
        var path = "";
        var domain = "";
        
        if( name=='JSESSIONID' ){
            path = ";Path=/realpaas-linker-web";
        }
        else{
            path = ";Path=/";
        }
        
        if( name=='JSESSIONID' ){
            domain = ";Domain=";
        }
        else{
            domain = ";Domain=.realsaas.com";
        }
        
        cookieSting = name + "=;Expires="+agoGMT + path + domain;
        document.cookie = cookieSting;
    }
};

Rui.focus = function(id){
    $('#'+id).focus();
};

Rui.quickAccess = function(){
    var quickAccess = $('#quickAccess.r-quick-access');
    var debugPopupId = 'debug';
    if( quickAccess.length<=0 ){
        quickAccess = $('<div id="quickAccess" class="r-quick-access"></div>')
        quickAccess.appendTo(document.body);
    }
    var windowHeight = $( document ).height()-100;
    quickAccess.css( { 'left': 0 , 'top': windowHeight } );
    
    quickAccess.draggable();
    quickAccess.click(function(){
          $('#'+debugPopupId).dialog('open');
        });
    Rui.popup( debugPopupId, '../../open/portal/debug' );
};

Rui.popup = function(id, url){
    var win = $('#' + id + '.r-window');
    var iframe = $('#' + id + 'iframe.r-iframe');
    var titleHeight = 30;
    var widthOffset = 30;
    var defaultOption = {
        width: '800px',
        height: '700px',
        left: 0,
        top: 0
    };
    
    if( iframe.length<=0 ){
        iframe = $('<iframe id="' + id + '" class="r-iframe"></iframe>');
        iframe.appendTo( document.body );
        var newObject = $.extend({}, defaultOption);
        iframe.css( newObject );
    }
    
    iframe.attr('src', url);
    iframe.dialog({
            autoOpen: false,
            closeText: 'Close',
            title: 'Debug Window',
            position: 'center',
            maxWidth: 800,
            maxHeight: 700,
            minWidth: 660,
            minHeight: 400,
            open: function(){
                var o = iframe.parent();
                var width = o.width()-widthOffset;
                var height = o.height()-titleHeight;
                width = parseInt( width );
                height = parseInt( height );
                iframe.css({'width': width,'height' :o.height,'left': 0,'top': 0});
            }
        });
        
        iframe.parent().resize(function(){
            var o = $(this);
            var width = o.width()-widthOffset;
            var height = o.height()-titleHeight-30;
            width = parseInt( width );
            height = parseInt( height );
            iframe.css({'width': width, 'height': height});
//                .css({'left': 0, 'top': 0});
        });
};

Rui.msgPanel = function(){
    var msgPanelId = "msgPanelId";
    var msgPanel = $('<div id="' + msgPanelId + '" class="r-panel-msg"><ul></ul></div>');
    msgPanel.appendTo( document.body );
    msgPanel.draggable();
    
    var pageWidth = $( window ).width();
    var pageHeight = $( window ).height();
    var left = (pageWidth - $(this).width())/2;
    var top = (pageHeight - $(this).height())/2;
    alert(left);
    msgPanel
    .css({'left': left, 'top': top});
};
Rui.log = function( msg ){
    $( '<li>' +msg+ '</li>' ).appendTo( '#msgPanelId' );
};

Rui.checkUsernameExistence = function( el, callback ){
    var value = el.val() || '';
    var history = el.data('history');
    var working = el.data('working');

    value = $.trim( value );
    if(!history){
        history = {};
        el.data('history', history);
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
    $.getJSON('../security/check-username', { 'username': username }, function(response, status, xhr){
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
};

Rui.page.initSignin = function(){
    //Init autoSignin checkbox state
    var autoSignin = $.cookie('autoSigninFlag');
    if( autoSignin=='false' ){
        $("#autoSignin").attr( 'checked', false );
    }
    else{//check it if autoSignin is set to true, or check it by default if it is not set
        $("#autoSignin").attr( 'checked', true );
    }
    
    $("#usernameSignin").inlineHint( Rui.rs['app.username.hint'] );
    $("#passwordText").inlineHint( Rui.rs['app.password.hint'] );
    
    var goToSignin = function(){
        $("#passwordText").syncSHA1( '#passwordSignin' );
        $("#usernameSignin").clearInlineHint();
        $("#passwordText").clearInlineHint();
        
        var autoSigninFlag = $("#autoSignin:checkbox:checked").length > 0 ? 'true' : 'false';
        $.cookie( 'autoSigninFlag', autoSigninFlag, Rui.cookieOptions ); //Save autoSigninFlag state to cookie
        $("#signinForm").submit();
    };
    
    $("#signinSubmit").click(function(e){
        goToSignin();
    });
    
    $("#usernameSignin").keydown(function(e){
        var key = e.which;
        if (key == 13) {
            $("#passwordText").focus();
        }
    });
    $("#passwordText").keydown(function(e){
        var key = e.which;
        if (key == 13) {
            goToSignin();
        }
    });
    
    if( $('.signin-modal').length==0 ){
        $('#usernameSignin').focus();
    }
};
Rui.page.initSignup = function(){
    $.pwdstrprompt = Rui.rs['app.pwdstr'];
    $.pwdstrmap[0] = Rui.rs['app.pwdstr.blank'];
    $.pwdstrmap[1] = Rui.rs['app.pwdstr.tooshort'];
    $.pwdstrmap[2] = Rui.rs['app.pwdstr.weak'];
    $.pwdstrmap[3] = Rui.rs['app.pwdstr.medium'];
    $.pwdstrmap[4] = Rui.rs['app.pwdstr.strong'];
    $.pwdstrmap[5] = Rui.rs['app.pwdstr.verystrong'];
    
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
        $.getJSON('../security/check-username', { 'username': username }, function(response, status, xhr){
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
            $( "#usernameSignup" ).removeClass( 'r-input-invalid' ).addClass( 'r-input-valid' );
        }
        else{
            $( "#usernameError" ).html( Rui.rs['app.username.existent'] ).removeClass( 'r-username-ok' );
            $( "#usernameSignup" ).removeClass( 'r-input-valid' ).addClass( 'r-input-invalid' );
        }
    };
    
    $("#usernameSignup")
    .inlineHint( Rui.rs['app.username.hint'] )
    .keyup(function(){
        changeUsernameHint();
    })
    .blur(function(){
        var el = $(this);
        var emailEl = $("#email");
        var isEmail = $.validateEmail( el.val() );
/*        
        if( isEmail && emailEl.data( 'inlineHint' ).blank ){
            emailEl.data( 'inlineHint' ).blank = false;
            emailEl.clearInlineHint().val( el.val() );
        }
*/
        if( isEmail ){
            emailEl.val( el.val() );
        }

        if( $(this).hasClass( 'r-input-valid' ) ){
            checkUsernameExistence( $(this), changeUsernameHint );
        }
    });
    
    $("#passwordFirst")
    .inlineHint( Rui.rs['app.password.hint'] )
    .pwdstr( 'passwordStrength' )
    .blur(function(){
        var el = $(this);
        $("#passwordSecond").val( el.val() );
    });
    
/*    
    $("#passwordSecond")
    .inlineHint( Rui.rs['app.passwordconfirm.hint'] );
    
    $("#email")
    .inlineHint( Rui.rs['app.email.hint'] );
*/    
    
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
            usernameSignup: {
                username: Rui.rs['app.username.illegal'],
            }
        },
        submitHandler: function(form) {
            $(form).find( 'input:password' ).val( '1111' );
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
        $("#passwordFirst").syncSHA1( '#passwordSignup' );
    
        $("#usernameSignup").clearInlineHint();
        $("#passwordFirst").clearInlineHint();
        //$("#passwordSecond").clearInlineHint();
        //$("#email").clearInlineHint();
        
        var valResult = $("#signupForm").valid();
        if( valResult ){
            $("#signupForm").submit();
        }
        else{
            $("#usernameSignup").setInlineHint();
            $("#passwordFirst").setInlineHint();
            //$("#passwordSecond").setInlineHint();
            //$("#email").setInlineHint();
        }
    };
    
    $("#signupSubmit").click(function(e){
        goToSignup();
    });
    
    $('#agreementLink').click(function(){
        window.open('../resources/file/agreement_<real:locale/>.txt', '_blank');
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
            goToSignup();
        }
    });
    
    if( $('.signup-modal').length==0 ){
        $('#usernameSignup').focus();
    }
};

Rui.page.initHeadBar = function(){
    $(".signup-button").click(function(e){
        $('.signin-modal').modal('hide');
        $('.signup-modal').modal('show');
    });
    
    $(".signin-button").click(function(e){
        $('.signup-modal').modal('hide');
        $('.signin-modal').modal('show');
    });
    
    $("button[href].r-cystal-button").click(function(e){
        $("#bindForm").attr('action', $(this).attr('href') ).submit();
    });
};
Rui.page.init = function(){
    //setup locale selector
    $("#locale").change(function(){
        var newLocaleCode = $(this).val();
        $.get("../pp/change-locale", {locale: newLocaleCode},function( result ){
            //alert( result );
            window.location.reload(true);
        });
    });
    
    /*
    $(".r-signin-trigger").leanModal();
    $('.r-signin-trigger').click(function(){
        $("#signinModel").leanModal();
        //alert( 'signinModel' );
        //window.location.href="../security/signin";
    });
    */
    
     $('.r-cystal-button img').on( {
        mousedown: function(){
            $(this).addClass( 'pressed' );
        },
        mouseup: function(){
            $(this).removeClass( 'pressed' );
        },
        mouseleave: function(){
            $(this).removeClass( 'pressed' );
        }
    });
    
    /*
    $('input, select')
    .focusin(function(){$(this).addClass('r-input-focus')})
    .focusout(function(){$(this).removeClass('r-input-focus')});
    */
    Rui.page.initHeadBar();
    
    if( $('#signinForm').length>0 ){
        Rui.page.initSignin();
    }
    if( $('#signupForm').length>0 ){
        Rui.page.initSignup();
    }
    
    if( $('#signinForm').length==0 ){
        Rui.quickAccess();
    }
    
    //Rui.msgPanel();
};

function showProviderPanel(){
    $('#profilePanel').slideUp(400, function(){});
    $('#providerPanel').slideDown(400, function(){});
}
function showProfilePanel(){
    $('#profilePanel').slideDown(400, function(){});
    $('#providerPanel').slideUp(400, function(){});
}

