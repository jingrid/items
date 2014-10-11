/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.identity.service.impl;

import com.realpaas.platform.common.security.UserAuthenticationInformation;
import com.realpaas.platform.common.security.UserInformation;
import com.realpaas.platform.core.identity.dataobject.DefaultUserAccess;
import com.realpaas.platform.core.identity.dataobject.User;

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
 * 	Sep 26, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public final class UserHelper {

    private UserHelper() {}
    
    public static UserInformation buildUserDetail(User user, DefaultUserAccess userAccess){
        UserInformation userDetail = new UserInformation();
        UserAuthenticationInformation userAuthenDetail = new UserAuthenticationInformation();
        userAuthenDetail.setId( userAccess.getId().toString() );
        userAuthenDetail.setType( userAccess.getObjectTypeTreeCode() );
        userAuthenDetail.setUsername( userAccess.getUsername() );
        userAuthenDetail.setPassword( userAccess.getPassword() );
        
        userDetail.setId( user.getId().toString() );
        userDetail.setCurrentAuthenticationInformation( userAuthenDetail );
        userDetail.setDisplayName( userAccess.getUsername() );
        userDetail.setEmail( user.getEmail() );
        return userDetail;
    }
    
    public static UserInformation buildUserDetail(User user, String connectinId, String providerId, String displayName){
        UserInformation userDetail = new UserInformation();
        UserAuthenticationInformation userAuthenDetail = new UserAuthenticationInformation();
        userAuthenDetail.setId( connectinId );
        userAuthenDetail.setType( providerId );
        
        userDetail.setId( user.getId().toString() );
        userDetail.setCurrentAuthenticationInformation( userAuthenDetail );
        userDetail.setDisplayName( displayName );
        userDetail.setEmail( user.getEmail() );
        return userDetail;
    }

}
