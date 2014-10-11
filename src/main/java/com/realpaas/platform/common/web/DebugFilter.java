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
import javax.servlet.http.HttpServletResponseWrapper;
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
 * 	Jul 21, 2011	henry leu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henry leu
 * @see		javax.servlet.Filter
 * @see		javax.servlet.http.HttpServletRequestWrapper
 */
public class DebugFilter extends GenericFilterBean implements Filter {
    
    @Override
    public void destroy() {}
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        TrackingServletRequestWrapper requestWrapper = new TrackingServletRequestWrapper( (HttpServletRequest)request );
        TrackingServletResponseWrapper responseWrapper = new TrackingServletResponseWrapper( (HttpServletResponse)response );
        chain.doFilter(requestWrapper, responseWrapper);
    }
    
}

class TrackingServletRequestWrapper extends HttpServletRequestWrapper{

    public TrackingServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public HttpSession getSession() {
        printStackTraceElements();
        return super.getSession();
    }

    @Override
    public HttpSession getSession(boolean create) {
        printStackTraceElements();
        return super.getSession(create);
    }

    private void printStackTraceElements(){
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for(int i = 0; i < stackTraceElements.length; i++) {
            System.out.println( stackTraceElements[i].toString() );
        }
    }
}

class TrackingServletResponseWrapper extends HttpServletResponseWrapper{

    public TrackingServletResponseWrapper(HttpServletResponse response) {
        super( response );
    }

    @Override
    public void addCookie(Cookie cookie) {
        printStackTraceElements();
        super.addCookie( cookie );
    }

    @Override
    public void addHeader(String name, String value) {
        printStackTraceElements();
        super.addHeader( name, value );
    }
 
    private void printStackTraceElements(){
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for(int i = 0; i < stackTraceElements.length; i++) {
            System.out.println( stackTraceElements[i].toString() );
        }
    }
    
}
