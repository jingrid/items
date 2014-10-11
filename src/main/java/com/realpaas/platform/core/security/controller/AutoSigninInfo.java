/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.security.controller;

import java.io.Serializable;

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
 * 	Sep 7, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class AutoSigninInfo implements Serializable{
    
    private static final long serialVersionUID = 4395955736398246548L;
    
    public static final String AUTO_SIGNIN_FLAG_KEY = "autoSigninFlag";
    
    public static final String AUTO_SIGNIN_TOKEN_KEY = "autoSigninToken";
    
    public AutoSigninInfo(){}
    
    private boolean autoSigninFlag = false;
    
    private boolean tokenValid = false;

    private boolean tokenCracked = true;

    private boolean tokenExpired = true;

    private String username;
    
    private String password;
    
    public boolean isAutoSigninFlag() {
        return autoSigninFlag;
    }

    public void setAutoSigninFlag(boolean autoSigninFlag) {
        this.autoSigninFlag = autoSigninFlag;
    }

    public boolean isTokenValid() {
        return tokenValid;
    }

    public void setTokenValid(boolean tokenValid) {
        this.tokenValid = tokenValid;
    }

    public boolean isTokenCracked() {
        return tokenCracked;
    }

    public void setTokenCracked(boolean tokenCracked) {
        this.tokenCracked = tokenCracked;
    }

    public boolean isTokenExpired() {
        return tokenExpired;
    }

    public void setTokenExpired(boolean tokenExpired) {
        this.tokenExpired = tokenExpired;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isTokenUsable(){
        return tokenValid && !tokenCracked;
    }
}
