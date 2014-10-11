/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.security.service.impl;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.realpaas.platform.common.security.StandardUserAuthenticationInformation;
import com.realpaas.platform.common.security.StandardUserDetails;
import com.realpaas.platform.core.identity.dataobject.DefaultUserAccess;
import com.realpaas.platform.core.identity.service.DefaultUserAccessService;

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
 * 	Jan 7, 2013	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
@Component( "userDetailsService" )
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private DefaultUserAccessService userAccessService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DefaultUserAccess userAccess = userAccessService.loadByUsername( username );
        if( userAccess==null ){
            return null;
        }
        Collection<? extends GrantedAuthority> authorities = Collections.emptyList(); //TODO: LOAD ROLES
        StandardUserDetails standardUserDetails = new StandardUserDetails(
                userAccess.getUserId().toString(),
                userAccess.getUsername(),
                userAccess.getPassword(),
                true,
                true,
                true,
                true,
                authorities
            );
        
        StandardUserAuthenticationInformation userAuthenticationInformation = new StandardUserAuthenticationInformation();
        userAuthenticationInformation.setId( userAccess.getId().toString() );
        userAuthenticationInformation.setUsername( userAccess.getUsername() );
        userAuthenticationInformation.setPassword( userAccess.getPassword() );
        standardUserDetails.setUserAuthenticationInformation( userAuthenticationInformation );
        return standardUserDetails;
    }
}
