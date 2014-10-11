/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.identity.service.impl;

import org.apache.commons.codec.digest.DigestUtils;

import com.realpaas.platform.framework.mail.MailHelper;

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
 * 	2012-7-30	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public final class UserAccessHelper {
    
    public static final String BLANK_STRING = "";

    private UserAccessHelper() {}
    
    public static boolean isBlank( String value ){
        return value==null ? true : BLANK_STRING.equals( value.trim() );
    }
    
    public static boolean checkUsernameNullability(String username) {
        return !isBlank( username );
    }

    public static boolean checkPasswordNullability(String password) {
        return !isBlank( password );
    }
    
    public static boolean checkEmailNullability(String email) {
        return !isBlank( email );
    }
    
    public static boolean checkUsernameValidity( String username ){
        final String PATTERN = "\\b(^[A-Za-z]+[A-Za-z0-9]*$)\\b";
        return username.matches( PATTERN );
    }
    
    /**
     * TODO: allow more special chars: ~!@#$%^&*()
     * Check password validity: password can contain upper and lower chars, numbers and special chars( _ - ). 
     * Other chars is prohibited.
     * @param password
     * @return true means valid, false means illegal password
     */
    public static boolean checkPasswordValidity( String password ){
        final String PATTERN = "\\b(^[A-Za-z0-9]+$)\\b";
        return password.matches( PATTERN );
    }
    
    public static boolean checkEmailValidity(String email) {
        return MailHelper.validateEmailFormat( email );
    }
    
    public static boolean checkMobilePhoneValidity(String mobilePhone) {
        final String PATTERN = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        return mobilePhone.matches( PATTERN );
    }
    
    public static String generatePasswordDigest( String password ){
        return DigestUtils.shaHex( password );
    }
    
}
