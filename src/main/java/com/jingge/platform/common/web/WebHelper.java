/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.common.web;

import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.core.context.SecurityContextHolder;

import com.jingge.platform.common.security.PlatformAuthenticationToken;

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
 * 	Jan 8, 2013	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public final class WebHelper {

    private WebHelper(){}
    
    public static final Charset charsetUTF8;
    
    public static final String UTF8 = "UTF-8";

    public static final String BLANK = "";
    
    public static final String PARAM_TARGET_URL = "_targetUrl";
    
    static{
        charsetUTF8 = Charset.forName( UTF8 );
    } 
    
    
    public static boolean notBlank(String text){
        return text!=null && !BLANK.equals( text.trim() );
    }
    
    /**
     * Encode a URL string to a code which can be transfered as URL query parameter 
     * @param originalUrl the original URL which is to be encoded.
     * @return the encoded string
     */
    public static String encodeURL(String originalUrl){
        return Base64.encodeBase64URLSafeString( originalUrl.getBytes( charsetUTF8 ) );
    }
    
    /**
     * Decode a encoded string back to original URL string 
     * @param encodedUrl the encoded string which had been encoded from a URL string
     * @return the decoded URL string
     */
    public static String decodeURL(String encodedUrl){
        byte[] bytes = Base64.decodeBase64( encodedUrl );
        return new String( bytes, charsetUTF8 );
    }

    public static String getOriginalUrl(HttpServletRequest request){
        String queryString = request.getQueryString();
        String originalUrl = request.getRequestURL().append( (queryString == null) ? "" : ("?" + queryString) ).toString();
        return originalUrl;
    }
    
    public static String getOriginalUri(HttpServletRequest request){
        String queryString = request.getQueryString();
        String originalUri = request.getRequestURI() + ((queryString == null) ? "" : ("?" + queryString));
        return originalUri;
    }
    
    /**
     * Append a key value pair to a URL as a query parameter
     * @param originalUrl the original URL
     * @param paramName URL parameter name
     * @param paramValue URL parameter value
     * @return the new URL with appended parameter
     */
    public static String appendParam(String originalUrl, String paramName, String paramValue){
        return appendParam( originalUrl, paramName, paramValue, false );
    }

    /**
     * Append a key value pair to a URL as a query parameter
     * @param originalUrl the original URL
     * @param paramName URL parameter name
     * @param paramValue URL parameter value
     * @param checkParam indicates to check if {@code originalUrl} includes parameters
     * @return the new URL with appended parameter
     */
    public static String appendParam(String originalUrl, String paramName, String paramValue, boolean checkParam){
        if( checkParam ){
            int index = originalUrl.indexOf( '?' );
            if( index==-1 ){
                return originalUrl + "?" + paramName + "=" + paramValue;
            }
            else{
                return originalUrl + "&" + paramName + "=" + paramValue;
            }
        }
        else{
            return originalUrl + "?" + paramName + "=" + paramValue;
        }
    }
    
    /**
     * Append {@code targetUrl} to {@code originalUrl}.
     * @param originalUrl the URL will be requested soon for current task 
     * @param targetUrl the real target URL which will be requested after the current task is finished
     * @return the new URL with appended targetUrl parameter
     */
    public static String appendTargetUrl(String originalUrl, String targetUrl) {
        return appendParam( originalUrl, PARAM_TARGET_URL, targetUrl );
    }
    
    /**
     * Append {@code targetUrl} to {@code originalUrl}.
     * @param originalUrl the URL will be requested soon for current task 
     * @param targetUrl the real target URL which will be requested after the current task is finished
     * @param checkParam indicates to check if {@code originalUrl} includes parameters
     * @return the new URL with appended targetUrl parameter
     */
    public static String appendTargetUrl(String originalUrl, String targetUrl, boolean checkParam) {
        return appendParam( originalUrl, PARAM_TARGET_URL, targetUrl, checkParam );
    }
    
    /**
     * Set targetUrl to request or session
     * @param request
     * @param targetUrl
     * @return
     */
    public static void setTargetUrl(HttpServletRequest request, String targetUrl){
        HttpSession session = request.getSession( false );
        if( session!=null ){
            session.setAttribute( PARAM_TARGET_URL, encodeURL( targetUrl ) );
        }
        else{
            request.setAttribute( PARAM_TARGET_URL, encodeURL( targetUrl ) );
        }
    }
    
    /**
     * Get targetUrl from coming URL
     * @param request
     * @return
     */
    public static String getTargetUrl(HttpServletRequest request){
        String targetUrl = (String)request.getParameter( PARAM_TARGET_URL );
        if( WebHelper.notBlank( targetUrl ) ){
            return WebHelper.decodeURL( targetUrl );
        }
        else{
            targetUrl = null;
        }

        targetUrl = (String)request.getAttribute( PARAM_TARGET_URL );
        if( WebHelper.notBlank( targetUrl ) ){
            return WebHelper.decodeURL( targetUrl );
        }
        else{
            targetUrl = null;
        }

        HttpSession session = request.getSession( false );
        if( session!=null ){
            targetUrl = (String)session.getAttribute( PARAM_TARGET_URL );
            if( WebHelper.notBlank( targetUrl ) ){
                return WebHelper.decodeURL( targetUrl );
            }
            else{
                targetUrl = null;
            }
        }

        return targetUrl;
    }
    
    /**
     * Get URL-encoded targetUrl from coming URL
     * @param request
     * @return
     */
    public static String getEncodedTargetUrl(HttpServletRequest request){
        String targetUrl = (String)request.getParameter( PARAM_TARGET_URL );
        if( WebHelper.notBlank( targetUrl ) ){
            return targetUrl;
        }
        else{
            targetUrl = null;
        }

        targetUrl = (String)request.getAttribute( PARAM_TARGET_URL );
        if( WebHelper.notBlank( targetUrl ) ){
            return targetUrl;
        }
        else{
            targetUrl = null;
        }

        HttpSession session = request.getSession( false );
        if( session!=null ){
            targetUrl = (String)session.getAttribute( PARAM_TARGET_URL );
            if( WebHelper.notBlank( targetUrl ) ){
                return targetUrl;
            }
            else{
                targetUrl = null;
            }
        }

        return targetUrl;
    }
    
    /**
     * Check if the request contains targetUrl from coming URL, whatever in request query parameters or in session
     * @param request
     * @return
     */
    public static boolean hasTargetUrl(HttpServletRequest request){
        boolean has = false;

        //Check request
        has = WebHelper.notBlank( request.getParameter( PARAM_TARGET_URL ) );
        if( has ) return has;
        
        has = WebHelper.notBlank( (String)request.getAttribute( PARAM_TARGET_URL ) );
        if( has ) return has;
        
        //Check session
        HttpSession session = request.getSession( false );
        has = session!=null && WebHelper.notBlank( (String)session.getAttribute( PARAM_TARGET_URL ) );
        
        return has;
    }
    
    /**
     * TODO: replace it with Encrypt/Decrypt Framework component
     * @param username
     * @param password
     * @return
     */
    public static String encodeAutoSigninToken(String username, String password){
        return username+","+password;
    }
    
    public static String[] decodeAutoSigninToken(String token){
        return token.split( "," );
    }
    
    public static boolean hasSignedIn(){
        PlatformAuthenticationToken auth = (PlatformAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        return auth!=null;
    }
}
