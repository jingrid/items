/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.security.controller;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.realpaas.platform.core.identity.service.UserService.ResetPasswordInfo;
import com.realpaas.platform.core.identity.service.impl.UserAccessHelper;

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
 * 	Sep 5, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class ResetPasswordInfoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ResetPasswordInfo.class.isAssignableFrom( clazz );
    }

    @Override
    public void validate(Object target, Errors errors) {
        ResetPasswordInfo vo = (ResetPasswordInfo)target;
        if( !StringUtils.hasLength( vo.getUsername() ) ) {
            errors.rejectValue("username", "app.username.null");
        }
        else{
            if( !UserAccessHelper.checkEmailValidity( vo.getUsername() ) && !UserAccessHelper.checkMobilePhoneValidity( vo.getUsername() ) ) {
                errors.rejectValue("username", "app.username.illegal");
            }
        }
        
        if( !StringUtils.hasLength( vo.getPassword() ) ) {
            errors.rejectValue("password", "app.password.null");
        }
        else{
            if (!UserAccessHelper.checkPasswordValidity( vo.getPassword() )) {
                errors.rejectValue("password", "app.password.illegal");
            }
        }
    }

}
