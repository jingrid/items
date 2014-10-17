/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.common.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;

import com.jingge.platform.common.web.SchemePortMapper;
import com.jingge.platform.common.web.SimpleSchemePortMapper;

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
 * 	Jan 9, 2013	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class SchemeSwitchedRedirectStrategy implements RedirectStrategy {
    
    protected final Log logger = LogFactory.getLog(getClass());

    private boolean contextRelative;
    
    private final SchemePortMapper standardSchemePortMapper = new SimpleSchemePortMapper();
    
    private SchemePortMapper schemePortMapper = standardSchemePortMapper;
    
    private String scheme;
    
    private boolean forceToUseScheme = true;

    /**
     * Redirects the response to the supplied URL.
     * <p>
     * If <tt>contextRelative</tt> is set, the redirect value will be the value after the request context path. Note
     * that this will result in the loss of protocol information (HTTP or HTTPS), so will cause problems if a
     * redirect is being performed to change to HTTPS, for example.
     */
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
        String redirectUrl = calculateRedirectUrl(request, request.getContextPath(), url);
        redirectUrl = response.encodeRedirectURL(redirectUrl);

        if (logger.isDebugEnabled()) {
            logger.debug("Redirecting to '" + redirectUrl + "'");
        }

        response.sendRedirect(redirectUrl);
    }

    private String changeScheme(HttpServletRequest request, String url){
        String redirectUrl = url;

        if( !isForceToUseScheme() ){
            return redirectUrl;
        }
        
        if( getScheme().equals( request.getScheme() ) ){
            return redirectUrl;
        }

        Integer standardPort = standardSchemePortMapper.getPort( getScheme() );
        Integer redirectPort = schemePortMapper.getPort( getScheme() );
        
        if( redirectPort != null && standardPort != null ) {
            boolean includePort = redirectPort.intValue() != standardPort.intValue();
            redirectUrl = getScheme() + "://" + request.getServerName() + ((includePort) ? (":" + redirectPort) : "") + redirectUrl;
        }
        return redirectUrl;
    }
    
    private String calculateRedirectUrl(HttpServletRequest request, String contextPath, String url) {
        if (!UrlUtils.isAbsoluteUrl(url)) {
            if (contextRelative) {
                return url;
            } else {
                return changeScheme( request, contextPath + url );
            }
        }

        // Full URL, including http(s)://

        if (!contextRelative) {
            return url;
        }

        // Calculate the relative URL from the fully qualified URL, minus the scheme and base context.
        url = url.substring(url.indexOf("://") + 3); // strip off scheme
        url = url.substring(url.indexOf(contextPath) + contextPath.length());

        if (url.length() > 1 && url.charAt(0) == '/') {
            url = url.substring(1);
        }

        return url;
    }

    /**
     * If <tt>true</tt>, causes any redirection URLs to be calculated minus the protocol
     * and context path (defaults to <tt>false</tt>).
     */
    public void setContextRelative(boolean useRelativeContext) {
        this.contextRelative = useRelativeContext;
    }
    
    public void setSchemePortMapper(SchemePortMapper schemePortMapper) {
        this.schemePortMapper = schemePortMapper;
    }

    private String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    private boolean isForceToUseScheme() {
        return forceToUseScheme;
    }

    public void setForceToUseScheme(boolean forceToUseScheme) {
        this.forceToUseScheme = forceToUseScheme;
    }
}
