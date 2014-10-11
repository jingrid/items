/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.web;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

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
 * 	Sep 6, 2012	henry leu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henry leu
 * @see		javax.servlet.Filter
 */
public class PlatformFilter implements Filter, InitializingBean{
    
    private final Log logger = LogFactory.getLog( this.getClass() );
    
    private List<String> publicUriPatterns;
    
    private String authenticationUri;
    
    private String contextPath;
    
    private boolean isRootContextPath = false;
    
    public void setPublicUriPatterns(List<String> publicUriPatterns) {
        this.publicUriPatterns = publicUriPatterns;
    }

    public void setAuthenticationUri(String authenticationUri) {
        this.authenticationUri = authenticationUri;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        
        if( !isPublicResource( req ) && !WebHelper.hasSignedIn() ){
            String originalUrl = WebHelper.getOriginalUrl( req );
            String encodedUrl = WebHelper.encodeURL( originalUrl );
            String wholeAuthenticationUri = addContextPath( req, authenticationUri );
            String redirectUrl = WebHelper.appendTargetUrl( wholeAuthenticationUri, encodedUrl );
            resp.sendRedirect( redirectUrl );
        }
        else{
            chain.doFilter(request, response);
        }
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    
    @Override
    public void destroy() {}

    @Override
    public void afterPropertiesSet() throws Exception {
        
    }

    /**
     * TODO: use better synchronization strategy
     * @param request
     * @return
     */
    private synchronized String getContextPath(HttpServletRequest request) {
        if( contextPath==null ){
            contextPath = request.getContextPath();
            if( "/".equals( contextPath ) ){
                isRootContextPath = true;
            }
        }
        return contextPath;
    }

    private String addContextPath(HttpServletRequest request, String uriWithoutContextPath){
        String ctxPath = getContextPath( request );
        if( isRootContextPath ){
            return uriWithoutContextPath;
        }
        else{
            return ctxPath + uriWithoutContextPath;
        }
    }
    
    private boolean isPublicResource(HttpServletRequest request) {
        // TODO Auto-generated method stub
        String requestURI = request.getRequestURI();
        String requestURL = request.getRequestURL().toString();
        String contextPath = request.getContextPath();
        
        logger.debug( contextPath + "\t\t" + requestURI + "\t\t" + requestURL );
        
        boolean isPublic = false;
        for( Iterator<String> iter = publicUriPatterns.iterator(); iter.hasNext(); ){
            String publicUri = iter.next();
            String patthPrefix = contextPath + publicUri;
            isPublic = requestURI.equals( patthPrefix ) || requestURI.startsWith( patthPrefix );
            if( isPublic ){
                break;
            }
        }
        
        if( !isPublic ){
            if( requestURI.equals( contextPath + "/" ) ){
                isPublic = true;
            }
        }
        return isPublic;
    }
}
