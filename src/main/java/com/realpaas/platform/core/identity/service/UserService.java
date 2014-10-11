/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.identity.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.realpaas.platform.core.identity.dataobject.DefaultUserAccess;
import com.realpaas.platform.core.identity.dataobject.User;
import com.realpaas.platform.core.universal.service.AbstractProgramPartyRoleService;

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
 * 	2012-7-29	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
@Transactional
public interface UserService extends AbstractProgramPartyRoleService<User>{
    
    /**
     * Check if the given username exists.
     * @param username
     * @return true if the username exists
     */
    public boolean checkUsernameExistence(String username);
    
    public static class SignwithInfo extends SignupInfo{

        private static final long serialVersionUID = 9000430840228837060L;

        private String action;

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }
    
    public static class SignwithResult{
        public SignwithResultType type;
        public User user;
        public DefaultUserAccess currentUserAccess;
    }
    
    public enum SignwithResultType{
        Successful,
        ExistedUsername,
        WeekPassword,
        SystemError;
    }
    
    /**
     * Signup a user with username, password, email and displayName(if username is an email, no need of email input)
     * @param signwithInfo
     * @return
     */
    public SignwithResult signwith(SignwithInfo signwithInfo);
    
    public static class SignupInfo extends SigninInfo{

        private static final long serialVersionUID = -2830442869068169675L;
        
        private byte passwordStrength = 0; //SHA1 digest of password
        
        private String email;
        
        private String mobilePhone;
        
        private String displayName;
        
        public byte getPasswordStrength() {
            return passwordStrength;
        }

        public void setPasswordStrength(byte passwordStrength) {
            this.passwordStrength = passwordStrength;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

    }
    
    public static class SignupResult{
        public SignupResultType type;
        public User user;
        public DefaultUserAccess currentUserAccess;
    }
    
    public enum SignupResultType{
        Successful,
        BlankUsername,
        BlankPassword,
        BlankEmail,
        IllegalUsername,
        IllegalPassword,
        IllegalEmail,
        ExistedUsername,
        WeekPassword,
        SystemError;
    }
    
    /**
     * Signup a user with username, password and email (if username is an email, no need of email input)
     * @param user
     * @param currentUserAccess
     * @return
     */
    public SignupResult signup(SignupInfo userInfo);

    public static class SigninInfo implements Serializable{

        private static final long serialVersionUID = -7648726048293672806L;
        
        public SigninInfo() {
            super();
        }

        public SigninInfo(String username, String password) {
            super();
            this.username = username;
            this.password = password;
        }

        private String username;
        
        private String password; //SHA1 digest of password
        
//        private String targetUrl;

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

//        public String getTargetUrl() {
//            return targetUrl;
//        }
//
//        public void setTargetUrl(String targetUrl) {
//            this.targetUrl = targetUrl;
//        }
    }
    
    public static class SigninResult{
        public SigninResultType type;
        public User user;
        public DefaultUserAccess currentUserAccess;
    }
    
    public enum SigninResultType{
        Successful,
        BlankUsername,
        BlankPassword,
        IllegalUsername,
        IllegalPassword,
        WrongUsername,
        WrongPassword,
        SystemError;
    }
    
    /**
     * Login/Signin with username and password as default signin type.
     * @param username can be username, email, mobile-phone no.
     * @param password the SHA1 digest of original password string
     * @return
     */
    public SigninResult signin(SigninInfo signinInfo); 
    
    public static class ResetPasswordInfo implements Serializable{

        private static final long serialVersionUID = -8831178108385367981L;

        public ResetPasswordInfo() {
            super();
        }

        public ResetPasswordInfo(String username, String password, byte passwordStrength) {
            super();
            this.username = username;
            this.password = password;
            this.passwordStrength = passwordStrength;
        }

        private String username;
        
        private String password; //SHA1 digest of password

        private byte passwordStrength;
        
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

        public byte getPasswordStrength() {
            return passwordStrength;
        }

        public void setPasswordStrength(byte passwordStrength) {
            this.passwordStrength = passwordStrength;
        }
    }

    public static class ResetPasswordResult{
        public ResetPasswordResultType type;
    }

    public enum ResetPasswordResultType{
        Successful,
        NoUsername,
        TheSamePassword,
        SystemError;
    }

    /**
     * Reset usernae's password no matter what the old password is
     * @param username
     * @param password
     * @param passwordStrength
     * @return
     */
    public ResetPasswordResult resetPassword(ResetPasswordInfo resetPasswordInfo);
    
    public static class ChangePasswordInfo implements Serializable{

        private static final long serialVersionUID = 3555413450646130887L;

        public ChangePasswordInfo() {
            super();
        }

        public ChangePasswordInfo(String username, String oldPassword, String newPassword, byte newPasswordStrength) {
            super();
            this.username = username;
            this.oldPassword = oldPassword;
            this.newPassword = newPassword;
            this.newPasswordStrength = newPasswordStrength;
        }

        private String username;
        
        private String oldPassword; //SHA1 digest of old password

        private String newPassword; //SHA1 digest of new password

        private byte newPasswordStrength;
        
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getOldPassword() {
            return oldPassword;
        }

        public void setOldPassword(String oldPassword) {
            this.oldPassword = oldPassword;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }

        public byte getNewPasswordStrength() {
            return newPasswordStrength;
        }

        public void setNewPasswordStrength(byte newPasswordStrength) {
            this.newPasswordStrength = newPasswordStrength;
        }
    }
    
    public static class ChangePasswordResult{
        public ChangePasswordResultType type;
    }

    public enum ChangePasswordResultType{
        Successful,
        NoUsername,
        TheSamePassword,
        WrongOldPassword,
        SystemError;
    }
    
    /**
     * Change password with user's old password and new password.
     * @param username
     * @param oldPassword
     * @param password
     * @param passwordStrength
     * @return
     */
    public ChangePasswordResult changePassword(ChangePasswordInfo changePasswordInfo);

    /**
     * Check if username and email match before resetting password.
     * @param username
     * @return
     */
    public boolean checkResetPassword(String username);
    
    /**
     * List all the signin usernames of the given users
     * @param userIds the given user's id list
     * @return a list of usernames of the default user access 
     */
    public List<String> listUsernames(List<String> userIds);

    /**
     * Find all users simply
     * @return
     */
    public List<User> findUsers();
    
}
