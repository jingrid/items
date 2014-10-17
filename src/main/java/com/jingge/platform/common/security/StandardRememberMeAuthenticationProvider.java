/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.common.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

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
public class StandardRememberMeAuthenticationProvider extends RememberMeAuthenticationProvider {

    StandardRememberMeAuthenticationProvider(String key) {
        super( key );
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (supportsPlatformToken(authentication.getClass())) {
            return authentication;
        }
        else if (supportsRememberToken(authentication.getClass())) {
            if (this.getKey().hashCode() != ((RememberMeAuthenticationToken) authentication).getKeyHash()) {
                throw new BadCredentialsException(messages.getMessage("RememberMeAuthenticationProvider.incorrectKey",
                        "The presented RememberMeAuthenticationToken does not contain the expected key"));
            }
            return authentication;
        }
        else{
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        boolean supported = false;
        supported = RememberMeAuthenticationToken.class.isAssignableFrom(authentication) || 
                PlatformAuthenticationToken.class.isAssignableFrom(authentication);
        return supported;
    }

    private boolean supportsRememberToken(Class<?> authentication) {
        return RememberMeAuthenticationToken.class.isAssignableFrom(authentication);
    }
    
    private boolean supportsPlatformToken(Class<?> authentication) {
        return PlatformAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
