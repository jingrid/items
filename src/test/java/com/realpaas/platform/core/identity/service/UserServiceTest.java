/**
 * Copyright (c) 2011, RealPaaS Technologies Ltd. All rights reserved.
 */
package com.realpaas.platform.core.identity.service;

import java.util.List;

import org.testng.annotations.Test;

import com.realpaas.commons.utils.TestUtil;
import com.realpaas.platform.core.identity.dataobject.User;
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
public class UserServiceTest extends AbstractTest {
    
    private UserService userService;

    @Override
    public void setUp() {
        userService = (UserService)BeanContextLoader.i().getBean( "userService" );
        assertNotNull( userService );
    }

    @Override
    public void tearDown() {}

    @Test(groups = { "all", "platform", "sit", "service" })
    public void findUsers() {
        signupAUser();
        List<User> allUsers = userService.findUsers();
        assertTrue( allUsers.size() > 1);
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
}
