/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.realpaas.platform.framework.http.CookieManager;

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
 * 	Aug 11, 2012	henry leu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henry leu Email/MSN: hongli_leu@126.com
 */
public final class CookieHelper {
    
    public static final String CTX_COOKIE_MANAGER = CookieHelper.class.getName();
    
    private CookieHelper(){}
    
    public static CookieManager getCookieManager(HttpServletRequest request, HttpServletResponse response){
        return getCookieManager( request, response, null );
    }
    
    /**
     * Get CookieManager from Struts2 ActionContext. if none, init one and put it into ActionContext.
     * @param request HttpServletRequest object
     * @param domain which domain the cookie will be stored.
     * @return CookieManager object which has been initialized and put into ActionContext.
     * @throws NullPointerException throw if the caller of the method is not Struts2 Action or Interceptor.
     */
    public static CookieManager getCookieManager(HttpServletRequest request, HttpServletResponse response, String domain) throws NullPointerException{
        CookieManager cm = getContextCookieManager( request );
        if( cm==null ){
            cm = new CookieManager( request, response, domain );
            setContextCookieManager( request, cm );
        }
        return cm;
    }
    
    private static CookieManager getContextCookieManager(HttpServletRequest request){
        return (CookieManager)request.getAttribute( CTX_COOKIE_MANAGER );
    }
    
    private static void setContextCookieManager(HttpServletRequest request, CookieManager cm){
        request.setAttribute( CTX_COOKIE_MANAGER, cm );
    }
}
