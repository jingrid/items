/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.filter.GenericFilterBean;

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
 * 	Jan 8, 2013	henry leu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henry leu
 * @see		javax.servlet.Filter
 * @see		javax.servlet.http.HttpServletRequestWrapper
 */
public final  class RetainSessionFilter extends GenericFilterBean implements Filter {
    
    @Override
    public void destroy() {}
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RetainSessionServletRequestWrapper requestWrapper = new RetainSessionServletRequestWrapper( (HttpServletRequest)request );
        requestWrapper.setResponse( (HttpServletResponse)response );
        chain.doFilter(requestWrapper, response);
    }
}

class RetainSessionServletRequestWrapper extends HttpServletRequestWrapper{

    private static final String JSESSIONID = "JSESSIONID";
    
    private HttpServletResponse response = null;
    
    public RetainSessionServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public void setResponse(HttpServletResponse response) { this.response = response;}
    
    @Override
    public HttpSession getSession() {
        HttpSession session = super.getSession();
        processSessionCookie( session );
        return session;
    }

    @Override
    public HttpSession getSession(boolean create) {
        HttpSession session = super.getSession( create );
        processSessionCookie( session );
        return session;
    }

    private void processSessionCookie(HttpSession session) {
        if( null == session || null == response ) {
            return;
        }
        
        // cookieOverWritten is used to avoid set multiple JSESSIONID Cookie in the request wrapping and session creating chain.
        Object cookieOverWritten = getAttribute( "COOKIE_OVERWRITTEN_FLAG" );
        if( null == cookieOverWritten && isSecure() && isRequestedSessionIdFromCookie() && session.isNew() ) {
            Cookie cookie = new Cookie( JSESSIONID, session.getId() );
            cookie.setMaxAge( -1 );
            String contextPath = getContextPath();
            if( (contextPath != null) && (contextPath.length() > 0) ) {
                cookie.setPath( contextPath );
            }
            else {
                cookie.setPath( "/" );
            }
            response.addCookie( cookie ); // Add a Set-Cookie response for cookie-enabled session
            setAttribute( "COOKIE_OVERWRITTEN_FLAG", "true" );//Avoid setting cookie repeatedly.
        }
    }
}