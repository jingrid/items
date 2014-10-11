/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
 * 	Jan 6, 2013	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class StandardAuthenticationProcessingFilter extends UsernamePasswordAuthenticationFilter {

    public static final String DEFAULT_FILTER_PROCESSES_URL = "/signin";
    
    public static final String DEFAULT_USERNAME_KEY = "username";
    
    public static final String DEFAULT_PASSWORD_KEY = "password";
    
    private boolean postOnly = true;

    public StandardAuthenticationProcessingFilter() {
        super();
        setUsernameParameter( DEFAULT_USERNAME_KEY );
        setPasswordParameter( DEFAULT_PASSWORD_KEY );
    }

    
    
    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        if( isPostOnly() && !request.getMethod().equals( "POST" ) ){
            return false;
        }
        return super.requiresAuthentication( request, response );
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        /*
         * Prepare authentication information
         */
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        username = username == null ? "" : username;
        password = password == null ? "" : password;

        //Build authentication request (which is only used to provide authentication information, not the authentication result)
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        
        //setDetails(request, authRequest); //TODO: figure out if it is necessary
        
        //Authenticate request, then build and return the authentication token (which is a type of PlatformAuthenticationToken)
        Authentication authResult = this.getAuthenticationManager().authenticate( authRequest );

        return authResult;
    }

    /**
     * Defines whether only HTTP POST requests will be allowed by this filter.
     * If set to true, and an authentication request is received which is not a POST request, this filter will 
     * skip itself and go directly to next filter.
     * <p>
     * Defaults to <tt>true</tt> but may be overridden by subclasses.
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    private boolean isPostOnly() {
        return postOnly;
    }
}
