/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.common.web;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;

/**
 * <p>
 * 
 * <dl>
 * <dt><b>Examples:</b></dt>
 * <p>
 * <pre>
 * 
 * </pre>
 * 
 * <p><dt><b>Immutability:</b></dt> 
 * <dd>
 * 	<b>IMMUTABLE</b> and <b>MUTABLE</b>
 * </dd>
 * 
 * <p><dt><b>Thread Safety:</b></dt> 
 * <dd>
 * 	<b>NOT-THREAD-SAFE</b> and <b>NOT-APPLICABLE</b> (for it will never be used on multi-thread occasion.)
 * </dd>
 * 
 * <p><dt><b>Serialization:</b></dt>
 * <dd>
 * 	<b>NOT-SERIALIIZABLE</b> and <b>NOT-APPLICABLE</b> (for it have no need to be serializable.)
 * </dd>
 * 
 * <p><dt><b>Design Patterns:</b></dt>
 * <dd>
 * 	
 * </dd>
 * 
 * <p><dt><b>Change History:</b></dt>
 * <dd>
 * 	Date		Author		Action
 * </dd>
 * <dd>
 * 	Aug 11, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public final class LocaleHelper {
    
    private LocaleHelper(){}
    
    public static Locale getSessionLocale(HttpServletRequest request){
        Locale locale = (Locale) WebUtils.getSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
        return locale;
    }
    
    public static String getSessionLocaleCode(HttpServletRequest request){
        Locale locale = getSessionLocale( request );
        String localeCode = null;
        if( locale!=null ){
            localeCode = locale.toString();
        }
        else{
            localeCode = "";
        }
        return localeCode;
    }
    
    public static Locale getCookieLocale(HttpServletRequest request, String localeCookieName){
        String localeCode = getCookieLocaleCode( request, localeCookieName );
        Locale locale = StringUtils.parseLocaleString( localeCode );
        return locale;
    }
    
    public static String getCookieLocaleCode(HttpServletRequest request, String localeCookieName){
        Cookie cookie = WebUtils.getCookie(request, localeCookieName);
        String localeCode = null;

        if( cookie!=null ){
            localeCode = cookie.getValue();
            if( localeCode==null || localeCode.trim().equals( "" ) ){
                localeCode = "";
            }
        }
        else{
            localeCode = "";
        }
        return localeCode;
    }

    public static String getLocaleCode(HttpServletRequest request){
        Locale locale = getLocale( request );
        String localeCode = null;
        if( locale!=null ){
            localeCode = locale.toString();
        }
        else{
            localeCode = "";
        }
        return localeCode;
    }
    
    public static Locale getLocale(HttpServletRequest request ){
        Locale locale = (Locale)request.getAttribute( PlatformLocaleResolver.PLATFORM_LOCALE_ATTRIBUTE_NAME );
        
        if( locale!=null ){
            return locale;
        }
        
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        if (localeResolver != null) {
            return localeResolver.resolveLocale(request);
        }
        else {
            return request.getLocale();
        }        
    }
}
