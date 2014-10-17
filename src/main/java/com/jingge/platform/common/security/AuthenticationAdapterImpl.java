/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

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

@Component( "authenticationAdapter" )
public class AuthenticationAdapterImpl implements AuthenticationAdapter{

    @Autowired
    private UserInformationLoader userInformationLoader;
    
    @Autowired
    private AuthorityManager authorityManager;
    
    @Override
    public Authentication createAuthentication(UserDetails userDetails) {
        if( !(userDetails instanceof StandardUserDetails) ){
            throw new AuthenticationServiceException( "UserDetails object should be a instance of " + StandardUserDetails.class.getName() );
        }
        StandardUserDetails standardUserDetails = (StandardUserDetails)userDetails;
        String userId = standardUserDetails.getUserId();
        
        UserInformation userInformation = null;
        try{
            userInformation = userInformationLoader.loadUserInformationById( userId );
        }
        catch(Exception e){
            throw new AuthenticationServiceException( "Fail to load UserInformation object [id=\"" + userId + "\"]" );
        }
        
        if( userInformation == null){
            throw new AuthenticationServiceException( "UserInformation object [id=\"" + userId + "\"] not found" );
        }
        
        userInformation.setCurrentAuthenticationInformation( standardUserDetails.getUserAuthenticationInformation() );
        PlatformAuthenticationToken.Builder builder = PlatformAuthenticationToken.Builder.newInstance();
        PlatformAuthenticationToken token = builder.withUserInformation( userInformation )
            .addAuthority( authorityManager.getDefaultGrantedAuthority() )
            .withAuthorities( standardUserDetails.getAuthorities() )//TODO: load it by authorityManager
            .build();

        //token.setDetails(authenticationDetailsSource.buildDetails(request)); //TODO: 
        return token;
    }

    @Override
    public Authentication createAuthentication(String userId) {
        UserInformation userInformation = null;
        try{
            userInformation = userInformationLoader.loadUserInformationById( userId );
        }
        catch(Exception e){
            throw new AuthenticationServiceException( "Fail to load UserInformation object [id=\"" + userId + "\"]" );
        }
        
        if( userInformation == null){
            throw new AuthenticationServiceException( "UserInformation object [id=\"" + userId + "\"] not found" );
        }
        
        PlatformAuthenticationToken.Builder builder = PlatformAuthenticationToken.Builder.newInstance();
        PlatformAuthenticationToken token = builder.withUserInformation( userInformation )
            .addAuthority( authorityManager.getDefaultGrantedAuthority() )
            //.withUserInformation( userInformation ) //TODO: Add loaded authorities here
            .build();
        return token;
    }
    
}
