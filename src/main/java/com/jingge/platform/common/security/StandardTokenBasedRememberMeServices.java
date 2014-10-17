/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.common.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

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
 * 	Jan 11, 2013	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class StandardTokenBasedRememberMeServices extends TokenBasedRememberMeServices {

//    private static final String DEFAULT_REQUIRED_USER_ROLE = "ROLE_USER";

    private static final String MD5 = "MD5";
    
    private static final String SHA1 = "SHA1";
    
    private static final String DEFAULT_SIGNATURE_ALGORITHM = SHA1;

    private String signatureAlgorithm = DEFAULT_SIGNATURE_ALGORITHM;
    
//    private UserInformationService userInformationService;
//    
//    private String requiredUserRole = DEFAULT_REQUIRED_USER_ROLE;
    
    private AuthenticationAdapter authenticationAdapter;
    
    public StandardTokenBasedRememberMeServices(String key, UserDetailsService userDetailsService) {
        super( key, userDetailsService );
    }

    @Override
    protected Authentication createSuccessfulAuthentication(HttpServletRequest request, UserDetails userDetails) {
        /*
        if( !(user instanceof StandardUserDetails) ){
            throw new AuthenticationServiceException( "UserDetails object should be a instance of " + StandardUserDetails.class.getName() );
        }
        StandardUserDetails standardUserDetails = (StandardUserDetails)user;
        String userId = standardUserDetails.getUserId();
        
        UserInformation userInformation = null;
        try{
            userInformation = getUserInformationService().loadById( userId );
        }
        catch(Exception e){
            throw new AuthenticationServiceException( "Fail to load UserInformation object [id=\"" + userId + "\"]" );
        }
        
        if( userInformation == null){
            throw new AuthenticationServiceException( "UserInformation object [id=\"" + userId + "\"] not found" );
        }
        
        userInformation.setCurrentAuthenticationInformation( standardUserDetails.getUserAuthenticationInformation() );
        PlatformAuthenticationToken.Builder builder = PlatformAuthenticationToken.Builder.newInstance();
        PlatformAuthenticationToken token = builder.addAuthority( new SimpleGrantedAuthority( getRequiredUserRole() ) )
            .withAuthorities( standardUserDetails.getAuthorities() )
            .withUserInformation( userInformation )
            .build();

        //token.setDetails(authenticationDetailsSource.buildDetails(request)); //TODO: 
         */
        
        return authenticationAdapter.createAuthentication( userDetails );
    }

    @Override
    protected String makeTokenSignature(long tokenExpiryTime, String username, String password) {
        String data = username + ":" + tokenExpiryTime + ":" + password + ":" + getKey();
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance( getSignatureAlgorithm() ); //TODO: check if need to CACHE IT
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No Remember-me Cookie Signiture algorithm available!");
        }

        return new String(Hex.encode(digest.digest(data.getBytes())));
    }

    @Override
    protected void setCookie(String[] tokens, int maxAge, HttpServletRequest request, HttpServletResponse response) {
        if( logger.isDebugEnabled() ){
            logger.debug("Creating cookie for remember-me signiture");
        }
        String cookieValue = encodeCookie( tokens );
        Cookie cookie = new Cookie( getCookieName(), cookieValue );
        cookie.setMaxAge( maxAge );
        cookie.setPath( getCookiePath( request ) );
        cookie.setSecure( true );
        response.addCookie( cookie );

        Cookie insucureCookie = (Cookie)cookie.clone();
        insucureCookie.setSecure( false );
        response.addCookie( insucureCookie );
    }

    @Override    
    protected void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
        if( logger.isDebugEnabled() ){
            logger.debug("Cancelling cookie for remember-me signiture");
        }
        Cookie cookie = new Cookie( getCookieName(), null );
        cookie.setMaxAge( 0 );
        cookie.setPath( getCookiePath( request ) );
        cookie.setSecure( true );
        response.addCookie( cookie );
        
        Cookie insucureCookie = (Cookie)cookie.clone();
        insucureCookie.setSecure( false );
        response.addCookie( insucureCookie );
    }

    private String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";
    }
    
//    private UserInformationService getUserInformationService() {
//        return userInformationService;
//    }
//
//    public void setUserInformationService(UserInformationService userInformationService) {
//        this.userInformationService = userInformationService;
//    }
//    
//    private String getRequiredUserRole() {
//        return requiredUserRole;
//    }
//
//    public void setRequiredUserRole(String requiredUserRole) {
//        this.requiredUserRole = requiredUserRole;
//    }
    
    
    private String getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public void setSignatureAlgorithm(String signatureAlgorithm) {
        if( SHA1.equals( signatureAlgorithm ) || MD5.equals( signatureAlgorithm ) ){
            this.signatureAlgorithm = signatureAlgorithm;
        }
        else{
            throw new IllegalArgumentException("signatureAlgorithm only accept MD5 or SHA1");
        }
    }

    public void setAuthenticationAdapter(AuthenticationAdapter authenticationAdapter) {
        this.authenticationAdapter = authenticationAdapter;
    }
    
}
