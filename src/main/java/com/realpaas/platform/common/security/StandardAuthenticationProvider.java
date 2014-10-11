/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.security;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

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
public class StandardAuthenticationProvider extends DaoAuthenticationProvider {
    
//    public static final String DEFAULT_REQUIRED_USER_ROLE = "ROLE_USER";
//    
//    private UserInformationService userInformationService;
//    
//    private String requiredUserRole = DEFAULT_REQUIRED_USER_ROLE;
    
    private AuthenticationAdapter authenticationAdapter;
    
    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails userDetails) {
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
*/
        return authenticationAdapter.createAuthentication( userDetails );
    }

    @Override
    protected void doAfterPropertiesSet() throws Exception {
        super.doAfterPropertiesSet();
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

    public void setAuthenticationAdapter(AuthenticationAdapter authenticationAdapter) {
        this.authenticationAdapter = authenticationAdapter;
    }
    
}
