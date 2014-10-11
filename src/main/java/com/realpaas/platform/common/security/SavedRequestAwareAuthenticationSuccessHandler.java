/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

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
 * 	Jun 13, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class SavedRequestAwareAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
    private RequestCache requestCache = new HttpSessionRequestCache();
    
    public SavedRequestAwareAuthenticationSuccessHandler(){
        super();
    }
    
    @Override
    public void onAuthenticationSuccess( final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication ) throws ServletException, IOException{
        final SavedRequest savedRequest = requestCache.getRequest( request, response );
        
        if( savedRequest == null ){
            super.onAuthenticationSuccess( request, response, authentication );
            
            return;
        }
        final String targetUrlParameter = getTargetUrlParameter();
        if( isAlwaysUseDefaultTargetUrl() || ( targetUrlParameter != null && StringUtils.hasText( request.getParameter( targetUrlParameter ) ) ) ){
            requestCache.removeRequest( request, response );
            super.onAuthenticationSuccess( request, response, authentication );
            
            return;
        }
        
        clearAuthenticationAttributes( request );
        
        // Use the DefaultSavedRequest URL
        // final String targetUrl = savedRequest.getRedirectUrl();
        // logger.debug( "Redirecting to DefaultSavedRequest Url: " + targetUrl );
        // getRedirectStrategy().sendRedirect( request, response, targetUrl );
    }
    
    public void setRequestCache( final RequestCache requestCacheToSet ){
        requestCache = requestCacheToSet;
    }
    
}
