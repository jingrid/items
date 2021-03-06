/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jingge.platform.common.security.StandardUserAuthenticationInformation;
import com.jingge.platform.common.security.UserAuthenticationInformation;
import com.jingge.platform.common.security.UserAuthenticationService;
import com.jingge.platform.common.security.UserInformation;
import com.jingge.platform.core.identity.dataobject.DefaultUserAccess;
import com.jingge.platform.core.identity.dataobject.User;
import com.jingge.platform.core.identity.service.DefaultUserAccessService;
import com.jingge.platform.core.identity.service.UserService;
import com.realpaas.platform.ssos.core.dataobject.id.GenericIdentifier;

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
@Component( "userAuthenticationService" )
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    @Autowired
    private UserService userService;
    
    @Autowired
    private DefaultUserAccessService userAccessService;

    @Override
    public UserInformation loadUserInformationByUsername(String username) {
        DefaultUserAccess userAccess = userAccessService.loadByUsername( username );
        if( userAccess==null ){
            return null;
        }
        StandardUserAuthenticationInformation userAuthenticationInformation = new StandardUserAuthenticationInformation();
        userAuthenticationInformation.setId( userAccess.getId().toString() );
        userAuthenticationInformation.setUsername( userAccess.getUsername() );
        userAuthenticationInformation.setPassword( userAccess.getPassword() );

        User user = userService.loadById( userAccess.getUserId() );
        if(user==null){
            return null;
        }
        UserInformation userInfo = new UserInformation();
        userInfo.setId( user.getId().toString() );
        userInfo.setDisplayName( user.getName() );
        userInfo.setEmail( user.getEmail() );
        userInfo.setCurrentAuthenticationInformation( userAuthenticationInformation );
        
        return userInfo;
    }

    @Override
    public UserInformation loadUserInformationById(String userId) {
        User user = userService.loadById( new GenericIdentifier( Long.valueOf( userId ) ) );
        if(user==null){
            return null;
        }
        UserInformation userInfo = new UserInformation();
        userInfo.setId( user.getId().toString() );
        userInfo.setDisplayName( user.getName() );
        userInfo.setEmail( user.getEmail() );
        return userInfo;
    }

    @Override
    public UserAuthenticationInformation loadUserAuthInfoByUsername(String username) {
        DefaultUserAccess userAccess = userAccessService.loadByUsername( username );
        if( userAccess==null ){
            return null;
        }
        StandardUserAuthenticationInformation userAuthenticationInformation = new StandardUserAuthenticationInformation();
        userAuthenticationInformation.setId( userAccess.getId().toString() );
        userAuthenticationInformation.setUsername( userAccess.getUsername() );
        userAuthenticationInformation.setPassword( userAccess.getPassword() );
        return userAuthenticationInformation;
    }

}
