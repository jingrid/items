/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.identity.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.realpaas.platform.core.identity.dao.DefaultUserAccessDao;
import com.realpaas.platform.core.identity.dao.DefaultUserAccessDaoRedis;
import com.realpaas.platform.core.identity.dao.UserDao;
import com.realpaas.platform.core.identity.dataobject.DefaultUserAccess;
import com.realpaas.platform.core.identity.dataobject.User;
import com.realpaas.platform.core.identity.dataobject.UserAccess;
import com.realpaas.platform.core.identity.service.UserService;
import com.realpaas.platform.core.program.dao.ProgramPlatformDao;
import com.realpaas.platform.core.program.dataobject.ProgramPlatform;
import com.realpaas.platform.core.universal.service.impl.AbstractProgramPartyRoleServiceImpl;
import com.realpaas.platform.ssos.core.dataobject.id.GenericIdentifier;
import com.realpaas.platform.ssos.core.dataobject.id.Identifier;
import com.realpaas.platform.ssos.ext.dataobject.enumtype.LifeFlagFilter;

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
@Component( "userService" )
public class UserServiceImpl extends AbstractProgramPartyRoleServiceImpl<User, UserDao> implements UserService{
    
    @Autowired( required=true )
    private DefaultUserAccessDao defaultUserAccessDao;
    
    @Autowired( required=true )
    private DefaultUserAccessDaoRedis defaultUserAccessDaoRedis;
    
    @Autowired( required=true )
    private ProgramPlatformDao programPlatformDao;
    
    
    @Value( "#{sysProps['system.useRedis']}" )
    private boolean useRedis = true;
    
    private ProgramPlatform programPlatform;
    
    @Transactional( readOnly=true )
    @Override
    public boolean checkUsernameExistence(String username) {
        boolean existed = true;
        
        if( useRedis ){
            String passwordDigestStored = defaultUserAccessDaoRedis.getPassword( username );
            existed = passwordDigestStored!=null;
        }
        else{
            DefaultUserAccess defaultUserAccessExisted = defaultUserAccessDao.loadByUsername( username );
            existed = defaultUserAccessExisted!=null;
        }
        
        return existed;
    }

    @Override
    public SignwithResult signwith(SignwithInfo signupInfo) {
        SignwithResult result = new SignwithResult();
        User user = new User();
        DefaultUserAccess defaultUserAccess = new DefaultUserAccess();
        
        user.setName( signupInfo.getDisplayName() );
        user.setEmail( signupInfo.getEmail() );
        defaultUserAccess.setUsername( signupInfo.getUsername() );
        defaultUserAccess.setPassword( signupInfo.getPassword() );
        defaultUserAccess.setPasswordStrength( signupInfo.getPasswordStrength() );

        try{
            String passwordDigestStored = null;
            String passwordDigest = null;
            if( useRedis ){
                /*
                 * Check if username exists
                 */
                passwordDigestStored = defaultUserAccessDaoRedis.getPassword( defaultUserAccess.getUsername() );
                if( passwordDigestStored != null ){
                    result.type = SignwithResultType.ExistedUsername;
                    return result;
                }
            }
            else{
                /*
                 * Check if username exists
                 */
                DefaultUserAccess defaultUserAccessExisted = defaultUserAccessDao.loadByUsername( defaultUserAccess.getUsername() );
                if( defaultUserAccessExisted != null ) {
                    result.type = SignwithResultType.ExistedUsername;
                    return result;
                }
            }
            
            //Create the User
            user.setProgramUnitId( getUserProgramUnit().getId() );
            getDao().create( user );
            
            // Create the UserLogin
            String passwordOriginal = defaultUserAccess.getPassword();
            passwordDigest = UserAccessHelper.generatePasswordDigest( passwordOriginal ); //Only store password's digest.
            defaultUserAccess.setPassword( passwordDigest );
            defaultUserAccess.setUserId( user.getId() );
            defaultUserAccessDao.create( defaultUserAccess );
            if( useRedis ){
                defaultUserAccessDaoRedis.setPassword( defaultUserAccess.getUsername(), passwordDigest );
                defaultUserAccessDaoRedis.setUserAccessId( defaultUserAccess.getUsername(), defaultUserAccess.getId().toString() );
            }
            
            result.type = SignwithResultType.Successful;
            result.user = user;
            result.currentUserAccess = defaultUserAccess;
        }
        catch(Exception e ){
            logger.error( "Fail to sign up", e );
            throw new RuntimeException( e );
        }
        return result;
    }

    @Override
    public SignupResult signup(SignupInfo userInfo) {
        SignupResult result = new SignupResult();
        User user = new User();
        DefaultUserAccess defaultUserAccess = new DefaultUserAccess();
        user.setName( userInfo.getUsername() );
        user.setEmail( userInfo.getEmail() );
        defaultUserAccess.setUsername( userInfo.getUsername() );
        defaultUserAccess.setPassword( userInfo.getPassword() );
        defaultUserAccess.setPasswordStrength( userInfo.getPasswordStrength() );
        
        try{
/*
            if( !UserAccessHelper.checkUsernameNullability( defaultUserAccess.getUsername() ) ) {
                result.type = SignupResultType.BlankUsername;
                return result;
            }
            
            if( !UserAccessHelper.checkPasswordNullability( defaultUserAccess.getPassword() ) ) {
                result.type =  SignupResultType.BlankPassword;
                return result;
            }
            
            if( !UserAccessHelper.checkEmailNullability( user.getEmail() ) ) {
                result.type =  SignupResultType.BlankEmail;
                return result;
            }
            
            if( !UserAccessHelper.checkUsernameValidity( defaultUserAccess.getUsername() ) ) {
                result.type =  SignupResultType.IllegalUsername;
                return result;
            }
            
            if( !UserAccessHelper.checkPasswordValidity( defaultUserAccess.getPassword() ) ) {
                result.type =  SignupResultType.IllegalPassword;
                return result;
            }
            
            if( !UserAccessHelper.checkEmailValidity( user.getEmail() ) ) {
                result.type =  SignupResultType.IllegalEmail;
                return result;
            } 
*/
            String passwordDigestStored = null;
            String passwordDigest = null;
            if( useRedis ){
                /*
                 * Check if username exists
                 */
                passwordDigestStored = defaultUserAccessDaoRedis.getPassword( defaultUserAccess.getUsername() );
                if( passwordDigestStored != null ){
                    result.type = SignupResultType.ExistedUsername;
                    return result;
                }
            }
            else{
                /*
                 * Check if username exists
                 */
                DefaultUserAccess defaultUserAccessExisted = defaultUserAccessDao.loadByUsername( defaultUserAccess.getUsername() );
                if( defaultUserAccessExisted != null ) {
                    result.type = SignupResultType.ExistedUsername;
                    return result;
                }
            }
            
            //Create the User
            user.setName( defaultUserAccess.getUsername() );
            user.setProgramUnitId( getUserProgramUnit().getId() );
            getDao().create( user );
            
            // Create the UserLogin
            String passwordOriginal = defaultUserAccess.getPassword();
            passwordDigest = UserAccessHelper.generatePasswordDigest( passwordOriginal ); //Only store password's digest.
            defaultUserAccess.setPassword( passwordDigest );
            defaultUserAccess.setUserId( user.getId() );
            defaultUserAccessDao.create( defaultUserAccess );
            if( useRedis ){
                defaultUserAccessDaoRedis.setPassword( defaultUserAccess.getUsername(), passwordDigest );
                defaultUserAccessDaoRedis.setUserAccessId( defaultUserAccess.getUsername(), defaultUserAccess.getId().toString() );
            }
            
            result.type = SignupResultType.Successful;
            result.user = user;
            result.currentUserAccess = defaultUserAccess;
        }
        catch(Exception e ){
            logger.error( "Fail to sign up", e );
            throw new RuntimeException( e );
        }
        return result;
    }

    @Transactional
    @Override
    public SigninResult signin(SigninInfo signinInfo) {
        SigninResult result = new SigninResult();
        User user = null;
        DefaultUserAccess defaultUserAccess = null;
        String username = signinInfo.getUsername();
        String password = signinInfo.getPassword();
        
        try{
            String passwordDigestStored = null;
            String passwordDigest = null;
            if( useRedis ){
                /*
                 * Check if username exists
                 */
                passwordDigestStored = defaultUserAccessDaoRedis.getPassword( username );
                if( passwordDigestStored==null ){
                    result.type = SigninResultType.WrongUsername;
                    return result;
                }
                
                /*
                 * Check if the input password is correct to stored password
                 * This is the second time password SHA1 computation. 
                 * The first time is before login submit
                 */
                passwordDigest = UserAccessHelper.generatePasswordDigest( password );
                if( !passwordDigest.equals( passwordDigestStored ) ) {
                    result.type = SigninResultType.WrongPassword;
                    return result;
                }
                
                String userAccessId = defaultUserAccessDaoRedis.getUserAccessId( username );
                if( userAccessId==null ){
                    result.type = SigninResultType.SystemError;
                    return result;
                }
                
                defaultUserAccess = defaultUserAccessDao.loadById( new GenericIdentifier( Long.parseLong( userAccessId )  ) );
                if( defaultUserAccess==null ){
                    result.type = SigninResultType.SystemError;
                    return result;
                }
                
                user = defaultUserAccess.getUser();
            }
            else{
                defaultUserAccess = defaultUserAccessDao.loadByUsername( username );
                if( defaultUserAccess==null ){
                    result.type = SigninResultType.WrongUsername;
                    return result;
                }
                
                passwordDigestStored = defaultUserAccess.getPassword();
                passwordDigest = UserAccessHelper.generatePasswordDigest( password );
                if( !passwordDigest.equals( passwordDigestStored ) ) {
                    result.type = SigninResultType.WrongPassword;
                    return result;
                }
                
                user = defaultUserAccess.getUser();
            }
            
            recordSigninActivity( defaultUserAccess, user ); //Record each login info
            
            result.type = SigninResultType.Successful;
            result.user = user;
            result.currentUserAccess = defaultUserAccess;
        }
        catch(Exception e ){
            logger.error( "Fail to sign in", e );
            throw new RuntimeException( e );
        }
        return result;
    }
    
    @Override
    public ResetPasswordResult resetPassword(ResetPasswordInfo resetPasswordInfo) {
        ResetPasswordResult result = new ResetPasswordResult();
        DefaultUserAccess defaultUserAccess = null;
        String username = resetPasswordInfo.getUsername();
        String password = resetPasswordInfo.getPassword();
        byte passwordStrength = resetPasswordInfo.getPasswordStrength();
        
        try{
            String oldPasswordDigest = null;
            String newPasswordDigest = null;
            if( useRedis ){
                /*
                 * Check if username exists
                 */
                oldPasswordDigest = defaultUserAccessDaoRedis.getPassword( username );
                if( oldPasswordDigest==null ){
                    result.type = ResetPasswordResultType.NoUsername;
                    return result;
                }
                
                newPasswordDigest = UserAccessHelper.generatePasswordDigest( password );
                if( oldPasswordDigest.equals( newPasswordDigest ) ) {
                    result.type = ResetPasswordResultType.TheSamePassword;
                    return result;
                }
                
                defaultUserAccessDaoRedis.setPassword( username, newPasswordDigest );
            }
            else{
                /*
                 * Check if username exists
                 */
                defaultUserAccess = defaultUserAccessDao.loadByUsername( username );
                if( defaultUserAccess==null ){
                    result.type = ResetPasswordResultType.NoUsername;
                    return result;
                }
                
                oldPasswordDigest = defaultUserAccess.getPassword();
                newPasswordDigest = UserAccessHelper.generatePasswordDigest( password );
                if( oldPasswordDigest.equals( newPasswordDigest ) ) {
                    result.type = ResetPasswordResultType.TheSamePassword;
                    return result;
                }
                
                defaultUserAccess.setPassword( newPasswordDigest );
                defaultUserAccess.setPasswordStrength( passwordStrength );
                defaultUserAccessDao.update( defaultUserAccess );
            }
            
            recordResetPasswordActivity( username, password, passwordStrength ); //Record each reset password activity
            result.type = ResetPasswordResultType.Successful;
        }
        catch(Exception e ){
            logger.error( "Fail to reset password", e );
            throw new RuntimeException( e );
        }
        return result;
    }

    @Override
    public ChangePasswordResult changePassword(ChangePasswordInfo changePasswordInfo) {
        ChangePasswordResult result = new ChangePasswordResult();
        DefaultUserAccess defaultUserAccess = null;
        String username = changePasswordInfo.getUsername();
        String oldPassword = changePasswordInfo.getOldPassword();
        String newPassword = changePasswordInfo.getNewPassword();
        byte newPasswordStrength = changePasswordInfo.getNewPasswordStrength();
        
        try{
            String oldPasswordDigestStored = null;
            String oldPasswordDigest = null;
            String newPasswordDigest = null;
            if( useRedis ){
                /*
                 * Check if username exists
                 */
                oldPasswordDigestStored = defaultUserAccessDaoRedis.getPassword( username );
                if( oldPasswordDigestStored==null ){
                    result.type = ChangePasswordResultType.NoUsername;
                    return result;
                }

                oldPasswordDigest = UserAccessHelper.generatePasswordDigest( oldPassword );
                if( !oldPasswordDigestStored.equals( oldPasswordDigest ) ) {
                    result.type = ChangePasswordResultType.WrongOldPassword;
                    return result;
                }
                
                newPasswordDigest = UserAccessHelper.generatePasswordDigest( newPassword );
                if( oldPasswordDigestStored.equals( newPasswordDigest ) ) {
                    result.type = ChangePasswordResultType.TheSamePassword;
                    return result;
                }
                
                defaultUserAccessDaoRedis.setPassword( username, newPasswordDigest );
            }
            else{
                /*
                 * Check if username exists
                 */
                defaultUserAccess = defaultUserAccessDao.loadByUsername( username );
                if( defaultUserAccess==null ){
                    result.type = ChangePasswordResultType.NoUsername;
                    return result;
                }
                
                oldPasswordDigestStored = defaultUserAccess.getPassword();
                oldPasswordDigest = UserAccessHelper.generatePasswordDigest( oldPassword );
                if( !oldPasswordDigestStored.equals( oldPasswordDigest ) ) {
                    result.type = ChangePasswordResultType.WrongOldPassword;
                    return result;
                }
                
                newPasswordDigest = UserAccessHelper.generatePasswordDigest( newPassword );
                if( oldPasswordDigestStored.equals( newPasswordDigest ) ) {
                    result.type = ChangePasswordResultType.TheSamePassword;
                    return result;
                }
                
                defaultUserAccess.setPassword( newPasswordDigest );
                defaultUserAccess.setPasswordStrength( newPasswordStrength );
                defaultUserAccessDao.update( defaultUserAccess );
            }
            
            recordChangePasswordActivity( username, newPassword, newPasswordStrength ); //Record each change password activity
            result.type = ChangePasswordResultType.Successful;
        }
        catch(Exception e ){
            logger.error( "Fail to reset password", e );
            throw new RuntimeException( e );
        }
        return result;
    }

    /**
     * TODO: duplicated method, replace it with checkUsernameExistence 
     */
    @Override
    public boolean checkResetPassword(String username) {
        try{
            DefaultUserAccess defaultUserAccess = null;
            if( useRedis ){
                String userAccessIdStr = defaultUserAccessDaoRedis.getUserAccessId( username );
                if( userAccessIdStr==null ){
                    return false;
                }
                Identifier<?> userAccessId = new GenericIdentifier( Long.parseLong( userAccessIdStr ) );
                
                defaultUserAccess = defaultUserAccessDao.loadById( userAccessId );
                if( defaultUserAccess==null ){
                    return false;
                }
            }
            else{
                defaultUserAccess = defaultUserAccessDao.loadByUsername( username );
                if( defaultUserAccess==null ){
                    return false;
                }
            }
        }
        catch(Exception e ){
            logger.error( "Fail to check username and email", e );
            throw new RuntimeException( e );
        }
        return true;
    }

    @Override
    public List<String> listUsernames(List<String> userIds) {
        List<String> usernames = new ArrayList<String>( userIds.size() );
//        for(String userIdStr : userIds){
//            User user = getDao().loadById( new GenericIdentifier( Long.parseLong( userIdStr ) ) );
//            Set<UserAccess> userAccesses = user.getUserAccesses();
//            for(Iterator<UserAccess> iter = userAccesses.iterator(); iter.hasNext();){
//                UserAccess userAccess = iter.next();
//                if( DefaultUserAccess.TYPE_CODE.equals( userAccess.getTypeCode() ) ){
//                    DefaultUserAccess defaultUserAccess = (DefaultUserAccess)userAccess;
//                    usernames.add( defaultUserAccess.getUsername() );
//                }
//            }
//        }
        
        List<DefaultUserAccess> defaultUserAccessList = defaultUserAccessDao.listByUserIds( userIds );
        for(DefaultUserAccess ua: defaultUserAccessList){
            usernames.add( ua.getUsername() );
        }
        return usernames;
    }
    
    @Override
    public List<User> findUsers() {
        return findByLifeFlag( LifeFlagFilter.All );
    }

    /**
     * TODO: impl
     * Record each signin info for user tracking itself and platform analysis
     * @param userAccess
     * @param user
     */
    @Async
    private void recordSigninActivity(UserAccess userAccess, User user){
        logger.info( "User " + user.getName() + " had logged in" );
    }

    /**
     * TODO: impl
     * @param username
     * @param password
     * @param passwordStrength
     */
    @Async
    private void recordResetPasswordActivity(String username, String password, byte passwordStrength){
        logger.info( "User " + username + " had reset password" );
    }

    /**
     * TODO: impl
     * @param username
     * @param password
     * @param passwordStrength
     */
    @Async
    private void recordChangePasswordActivity(String username, String password, byte passwordStrength){
        logger.info( "User " + username + " had changed password" );
    }

    private synchronized ProgramPlatform getUserProgramUnit(){
        if( programPlatform==null ){
            programPlatform = programPlatformDao.loadByCode( ProgramPlatform.DEFAULT_CODE );
        }
        return programPlatform;
    }
}
