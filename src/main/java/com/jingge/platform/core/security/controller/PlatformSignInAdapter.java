/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import com.jingge.platform.common.security.AuthenticationAdapter;

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
 * 	Oct 26, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
@Component( "signInAdapter" )
public class PlatformSignInAdapter implements SignInAdapter {

//    @Autowired
//    private UserInformationLoader userInformationLoader;
//    
//    @Autowired
//    private AuthorityManager authorityManager;

    @Autowired
    private AuthenticationAdapter authenticationAdapter;

    @Override
    public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
/*
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
        PlatformAuthenticationToken token = builder.addAuthority( authorityManager.getDefaultGrantedAuthority() )
                //TODO: Add loaded authorities here
            .withUserInformation( userInformation )
            .build();
        SecurityContextHolder.getContext().setAuthentication( token );
*/        
        Authentication token = authenticationAdapter.createAuthentication( userId );
        SecurityContextHolder.getContext().setAuthentication( token );
        return null;
    }

}
