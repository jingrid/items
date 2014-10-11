/**
 * Copyright (c) 2011, RealPaaS Technologies Ltd. All rights reserved.
 */
package com.realpaas.platform.core.security.service;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.realpaas.commons.utils.TestUtil;
import com.realpaas.platform.core.identity.service.UserService;
import com.realpaas.platform.core.identity.service.UserService.ChangePasswordInfo;
import com.realpaas.platform.core.identity.service.UserService.ChangePasswordResult;
import com.realpaas.platform.core.identity.service.UserService.ChangePasswordResultType;
import com.realpaas.platform.core.identity.service.UserService.ResetPasswordInfo;
import com.realpaas.platform.core.identity.service.UserService.ResetPasswordResult;
import com.realpaas.platform.core.identity.service.UserService.ResetPasswordResultType;
import com.realpaas.platform.core.identity.service.UserService.SigninInfo;
import com.realpaas.platform.core.identity.service.UserService.SigninResult;
import com.realpaas.platform.core.identity.service.UserService.SigninResultType;
import com.realpaas.platform.core.identity.service.UserService.SignupInfo;
import com.realpaas.platform.core.identity.service.UserService.SignupResult;
import com.realpaas.platform.core.identity.service.UserService.SignupResultType;
import com.realpaas.platform.framework.ioc.BeanContextLoader;
import com.realpaas.platform.test.AbstractTest;

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
 * 	Jul 30, 2012	henry leu	Create the class
 * </dd>
 *
 * </dl>
 * @author	henry leu
 */
public class SecurityServiceTest extends AbstractTest {
    
    private UserService userService;

    @Override
    public void setUp() {
        userService = (UserService)BeanContextLoader.i().getBean( "userService" );
        assertNotNull( userService );
    }

    @Override
    public void tearDown() {}

    @Test(groups = { "all", "platform", "sit", "service" })
    public void checkUsernameExistence() {
        boolean existed = true;
        String[] signupResult = signupAUser();
        String existedUsername = signupResult[0];
        String newUsername = TestUtil.uniqueText( "username{0}" );
        
        existed = userService.checkUsernameExistence( existedUsername );
        assertTrue( existed );
        
        existed = userService.checkUsernameExistence( newUsername );
        assertFalse( existed );
    }
    
    
    
    @Test(groups = { "all", "platform", "sit", "service" })
    public void signup() {
        String username = TestUtil.uniqueText( "username{0}" );
        String password = username;
        String email = "realpaas_dev@163.com";
        SignupResult result = null;
        
        SignupInfo userInfo = new SignupInfo();
        userInfo.setEmail( email );
        userInfo.setUsername( username );
        userInfo.setPassword( password );
        
        result = userService.signup( userInfo );
        assertEquals( result.type, SignupResultType.Successful );
        
    }

    public void signupWithIllegalUsername() {
        String username = null;
        String password = null;;
        String email = null;
        SignupResult result = null;
        
        username = "";
        password = "Abc123";
        email = "realpaas_dev@163.com";
        SignupInfo userInfo = new SignupInfo();
        userInfo.setEmail( email );
        userInfo.setUsername( username );
        userInfo.setPassword( password );
        
        result = userService.signup( userInfo );
        assertEquals( result.type, SignupResultType.BlankUsername );
        
        username = "123abc";
        password = "Abc123";
        email = "realpaas_dev@163.com";
        userInfo.setEmail( email );
        userInfo.setUsername( username );
        userInfo.setPassword( password );
        
        result = userService.signup( userInfo );
        assertEquals( result.type, SignupResultType.IllegalUsername );
        
    }

    public void signupWithIllegalPassword() {
        String username = null;
        String password = null;;
        String email = null;
        SignupResult result = null;
        
        username = TestUtil.uniqueText( "username{0}" );
        password = " ";
        email = "realpaas_dev@163.com";
        SignupInfo userInfo = new SignupInfo();
        userInfo.setEmail( email );
        userInfo.setUsername( username );
        userInfo.setPassword( password );
        
        result = userService.signup( userInfo );
        assertEquals( result.type, SignupResultType.BlankPassword );
        
        username = TestUtil.uniqueText( "username{0}" );
        password = "Abc123 ";
        email = "realpaas_dev@163.com";
        
        userInfo.setEmail( email );
        userInfo.setUsername( username );
        userInfo.setPassword( password );
        
        result = userService.signup( userInfo );
        assertEquals( result.type, SignupResultType.IllegalPassword );
    }
    
    public void signupWithIllegalEmail() {
        String username = null;
        String password = null;;
        String email = null;
        SignupResult result = null;
        
        username = TestUtil.uniqueText( "username{0}" );
        password = username;
        email = "";
        SignupInfo userInfo = new SignupInfo();
        userInfo.setEmail( email );
        userInfo.setUsername( username );
        userInfo.setPassword( password );
        
        result = userService.signup( userInfo );
        assertEquals( result.type, SignupResultType.BlankEmail );
        
        username = TestUtil.uniqueText( "username{0}" );
        password = username;
        email = "realpaas_dev163.com";
        
        userInfo.setEmail( email );
        userInfo.setUsername( username );
        userInfo.setPassword( password );
        
        result = userService.signup( userInfo );
        assertEquals( result.type, SignupResultType.IllegalEmail );
    }
    
    @Test(groups = { "all", "platform", "sit", "service" }, expectedExceptions = { RuntimeException.class })
    public void signupWithException() {
        String username = null;
        String password = null;;
        String email = null;
        SignupResult result = null;
        
        username = TestUtil.uniqueText( "username{0}" ) + "asdfsafddsafdsafasfasffffffffffffffffffsadfsafsadfdsfsafasfsafsafsafdffffffffffffffsadfsafsadfdsfsafasfsafsafsafdffffffffffffffsadfsafsadfdsfsafasfsafsafsafdsafsdfsfsdfsdfasdf";
        password = username;
        email = "realpaas_dev@163.com";
        SignupInfo userInfo = new SignupInfo();
        userInfo.setEmail( email );
        userInfo.setUsername( username );
        userInfo.setPassword( password );
        
        result = userService.signup( userInfo );
        assertEquals( result.type, SignupResultType.SystemError );
    }
    
    @Test(groups = { "all", "platform", "sit", "service" })
    public void signin() {
        String[] signupResult = signupAUser();
        String username = signupResult[0];
        String password = signupResult[1];
        SigninResult result = null;
        SigninInfo signinInfo = new SigninInfo( username, password );
        
        result = userService.signin( signinInfo );
        assertEquals( result.type, SigninResultType.Successful );
    }
    
    @Test(groups = { "all", "platform", "sit", "service" })
    public void checkResetPassword() {
        String[] signupResult = signupAUser();
        String username = signupResult[0];
        boolean match = false;
        match = userService.checkResetPassword( username );
        assertTrue( match );
        
        match = userService.checkResetPassword( "asdf" );
        assertFalse( match );
    }
    
    @Test(groups = { "all", "platform", "sit", "service" })
    public void resetPassword() {
        String[] signupResult = signupAUser();
        String username = signupResult[0];
        String password = signupResult[1];
        String newPassword = "Abcd1234";
        byte passwordStrength = 90;
        ResetPasswordResult resetPasswordResult = null;
        SigninResult signinResult = null;
        ResetPasswordInfo resetPasswordInfo = null;
        
        String noUsername = TestUtil.uniqueText( "username{0}" );
        resetPasswordInfo = new ResetPasswordInfo( noUsername, password, passwordStrength );
        resetPasswordResult = userService.resetPassword( resetPasswordInfo );
        assertEquals( resetPasswordResult.type, ResetPasswordResultType.NoUsername );
        
        resetPasswordInfo = new ResetPasswordInfo( username, password, passwordStrength );
        resetPasswordResult = userService.resetPassword( resetPasswordInfo );
        assertEquals( resetPasswordResult.type, ResetPasswordResultType.TheSamePassword );
        
        resetPasswordInfo = new ResetPasswordInfo( username, newPassword, passwordStrength );
        resetPasswordResult = userService.resetPassword( resetPasswordInfo );
        assertEquals( resetPasswordResult.type, ResetPasswordResultType.Successful );
        
        SigninInfo signinInfo = new SigninInfo( username, newPassword );
        signinResult = userService.signin( signinInfo );
        assertEquals( signinResult.type, SigninResultType.Successful );
    }
    
    @Test(groups = { "all", "platform", "sit", "service" })
    public void changePassword() {
        String[] signupResult = signupAUser();
        String username = signupResult[0];
        String password = signupResult[1];
        String oldPassword = password;
        String newPassword = "Abcd1234";
        byte newPasswordStrength = 90;
        ChangePasswordResult changePasswordResult = null;
        SigninResult signinResult = null;
        ChangePasswordInfo changePasswordInfo = null;
        
        String noUsername = TestUtil.uniqueText( "username{0}" );
        changePasswordInfo = new ChangePasswordInfo( noUsername, oldPassword, newPassword, newPasswordStrength );
        changePasswordResult = userService.changePassword( changePasswordInfo );
        assertEquals( changePasswordResult.type, ChangePasswordResultType.NoUsername );
        
        changePasswordInfo = new ChangePasswordInfo( username, newPassword, newPassword, newPasswordStrength );
        changePasswordResult = userService.changePassword( changePasswordInfo );
        assertEquals( changePasswordResult.type, ChangePasswordResultType.WrongOldPassword );
        
        changePasswordInfo = new ChangePasswordInfo( username, oldPassword, oldPassword, newPasswordStrength );
        changePasswordResult = userService.changePassword( changePasswordInfo );
        assertEquals( changePasswordResult.type, ChangePasswordResultType.TheSamePassword );
        
        changePasswordInfo = new ChangePasswordInfo( username, oldPassword, newPassword, newPasswordStrength );
        changePasswordResult = userService.changePassword( changePasswordInfo );
        assertEquals( changePasswordResult.type, ChangePasswordResultType.Successful );
        
        SigninInfo signinInfo = new SigninInfo( username, newPassword );
        signinResult = userService.signin( signinInfo );
        assertEquals( signinResult.type, SigninResultType.Successful );
    }
    
    @Test(groups = { "all", "platform", "sit", "service" })
    public void listUsernames() {
        SignupResult signupResult = null;
        List<String> userIds = new ArrayList<String>();
        List<String> expectedUsernames = new ArrayList<String>();
        
        signupResult = signupAUserWithResult();
        userIds.add( signupResult.user.getId().toString() );
        expectedUsernames.add( signupResult.currentUserAccess.getUsername() );
        
        signupResult = signupAUserWithResult();
        userIds.add( signupResult.user.getId().toString() );
        expectedUsernames.add( signupResult.currentUserAccess.getUsername() );

        List<String> usernames = userService.listUsernames( userIds );
        assertEquals( usernames.size(), 2 );
        
        assertEquals( usernames, expectedUsernames );
    }
    
    private String[] signupAUser() {
        String username = null;
        String password = null;;
        String email = null;
        SignupResult result = null;
        
        username = TestUtil.uniqueText( "username{0}" );
        password = username;
        email = "realpaas_dev@163.com";
        SignupInfo userInfo = new SignupInfo();
        userInfo.setEmail( email );
        userInfo.setUsername( username );
        userInfo.setPassword( password );
        
        result = userService.signup( userInfo );
        assertEquals( result.type, SignupResultType.Successful );
        return new String[]{username, password, email};
    }
    
    private SignupResult signupAUserWithResult() {
        String username = null;
        String password = null;;
        String email = null;
        SignupResult result = null;
        
        username = TestUtil.uniqueText( "username{0}" );
        password = username;
        email = "realpaas_dev@163.com";
        SignupInfo userInfo = new SignupInfo();
        userInfo.setEmail( email );
        userInfo.setUsername( username );
        userInfo.setPassword( password );
        
        result = userService.signup( userInfo );
        assertEquals( result.type, SignupResultType.Successful );
        return result;
    }
}
