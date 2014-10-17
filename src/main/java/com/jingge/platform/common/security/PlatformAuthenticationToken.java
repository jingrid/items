/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.common.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;


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
public class PlatformAuthenticationToken extends AbstractAuthenticationToken {
    
    PlatformAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super( authorities );
        super.setAuthenticated(true);
    }

    private static final long serialVersionUID = -8924993737709872672L;

    public void setUserInformation(UserInformation userInfo){
        this.setDetails( userInfo );
    }

    public UserInformation getUserInformation(){
        return (UserInformation)this.getDetails();
    }
    
    @Override
    public Object getCredentials() {
        return getUserInformation().getCurrentAuthenticationInformation().getPassword();
    }

    @Override
    public Object getPrincipal() {
        return getUserInformation().getCurrentAuthenticationInformation().getUsername();
    }

    /**
     * {@link UsernamePasswordAuthenticationToken#setAuthenticated(boolean)}
     */
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        getUserInformation().getCurrentAuthenticationInformation().setPassword( null );
    }
    
    public static class Builder{
        private UserInformation userInformation;
        private Collection<GrantedAuthority> authorities;

        private Builder (){}
        
        public static Builder newInstance(){
            return new Builder();
        }
        
        public PlatformAuthenticationToken build(){
            PlatformAuthenticationToken token = null;
            
            if( authorities==null ){
                this.authorities = new ArrayList<GrantedAuthority>();
            }
            token = new PlatformAuthenticationToken( this.authorities );
            this.authorities = null;
            
            if( userInformation==null ){
                throw new IllegalStateException();
            }
            token.setDetails( userInformation );
            this.userInformation = null;
            
            return token;
        }
        
        public Builder withUserInformation(UserInformation userDetail){
            this.userInformation = userDetail;
            return this;
        }
        
        public Builder withAuthorities(Collection<GrantedAuthority> authorities){
            if( this.authorities == null ){
                this.authorities = authorities;
            }
            else{
                this.authorities.addAll( authorities );
            }
            return this;
        }
        
        public Builder addAuthority(GrantedAuthority authority){
            if( this.authorities==null ){
                this.authorities = new ArrayList<GrantedAuthority>();
            }
            this.authorities.add( authority );
            return this;
        }
        
    }
}
