/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.web;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
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
 * 	Aug 12, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public final class PlatformLocaleResolver extends AbstractPlatformLocaleResolver{

    public static final String PLATFORM_LOCALE_ATTRIBUTE_NAME = PlatformLocaleResolver.class.getName() + ".LOCALE";

    private final Log logger = LogFactory.getLog( getClass() );
    
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        Locale locale = null;
        boolean hasSession = hasSession( request );
        
        if( hasSession ){
            locale = resolveSessionLocale( request );
        }
        else{
            locale = resolveCookieLocale( request );
        }
        
        if (locale == null) {
            locale = determineDefaultLocale(request);
        }
        
        return locale;
    }
    
    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        boolean hasSession = hasSession( request );
        
        if( hasSession ){
            WebUtils.setSessionAttribute(request, PLATFORM_LOCALE_ATTRIBUTE_NAME, locale);
        }
        else{
            if (locale != null) {
                // Set request attribute and add cookie.
                request.setAttribute( PLATFORM_LOCALE_ATTRIBUTE_NAME, locale );
                addCookie( response, locale.toString() );
            }
            else {
                // Set request attribute to fallback locale and remove cookie.
                request.setAttribute( PLATFORM_LOCALE_ATTRIBUTE_NAME, determineDefaultLocale( request ) );
                removeCookie(response);
            }
        }
    }
    
    /**
     * Determine the default locale for the given request,
     * Called if no locale session attribute has been found.
     * <p>The default implementation returns the specified default locale,
     * if any, else falls back to the request's accept-header locale.
     * @param request the request to resolve the locale for
     * @return the default locale (never <code>null</code>)
     * @see #setDefaultLocale
     * @see javax.servlet.http.HttpServletRequest#getLocale()
     */
    private Locale determineDefaultLocale(HttpServletRequest request) {
        Locale defaultLocale = getDefaultLocale();
        if (defaultLocale == null) {
            defaultLocale = request.getLocale();
        }
        return defaultLocale;
    }
    
    private Locale resolveSessionLocale(HttpServletRequest request){
        Locale locale = (Locale) WebUtils.getSessionAttribute( request, PLATFORM_LOCALE_ATTRIBUTE_NAME );
        return locale;
    }
    
    private Locale resolveCookieLocale(HttpServletRequest request){
        Locale locale = (Locale) request.getAttribute( PLATFORM_LOCALE_ATTRIBUTE_NAME );
        if (locale != null) {
            return locale;
        }

        // Retrieve and parse cookie value.
        Cookie cookie = WebUtils.getCookie( request, getCookieName() );
        if ( cookie != null ) {
            locale = StringUtils.parseLocaleString(cookie.getValue());
            if (logger.isDebugEnabled()) {
                logger.debug("Parsed cookie value [" + cookie.getValue() + "] into locale '" + locale + "'");
            }
            if (locale != null) {
                request.setAttribute( PLATFORM_LOCALE_ATTRIBUTE_NAME, locale );
                return locale;
            }
        }
        
        return locale;
    }
    
    private boolean hasSession(HttpServletRequest request){
        HttpSession session = request.getSession( false );
        return session!=null;
    }

}
