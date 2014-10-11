/* 
 * Copyright section: TODO
 */

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
                if( el.data( 'inlineHint' ).blank ){
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
            if( el.data( 'inlineHint' ).blank ){
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

if(!window.App){
    window.App = {};
    window.App.page = {};
    window.App.rs = {};
}

App.cookieOptions = {
    expires: 365,
    path: '/',
    domain: '.realpaas.com',
    secure: false
};
    
App.post = function(url, data){
    $.post( url, data ,function( result ){
        alert( result );
    });
};

App.get = function(url, data){
    $.get( url, data ,function( result ){
        alert( result );
    });
};

App.removeCookies = function(){
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
            domain = ";Domain=.realpaas.com";
        }
        
        cookieSting = name + "=;Expires="+agoGMT + path + domain;
        document.cookie = cookieSting;
    }
};

App.focus = function(id){
    $('#'+id).focus();
};

App.quickAccess = function(){
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
    App.popup( debugPopupId, '../portal/debug' );
};

App.popup = function(id, url){
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

App.msgPanel = function(){
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
App.log = function( msg ){
    $( '<li>' +msg+ '</li>' ).appendTo( '#msgPanelId' );
};
App.page.init = function(){
    //setup locale selector
    $("#locale").change(function(){
        var newLocaleCode = $(this).val();
        $.get("../portal/change-locale", {locale: newLocaleCode},function( result ){
            //alert( result );
            window.location.reload(true);
        });
    });
    
    $('input, select')
    .focusin(function(){$(this).addClass('r-input-focus')})
    .focusout(function(){$(this).removeClass('r-input-focus')});
    
    App.quickAccess();
    //App.msgPanel();
    
};