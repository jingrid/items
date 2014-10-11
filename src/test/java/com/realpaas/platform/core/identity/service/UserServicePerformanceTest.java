/**
 * Copyright (c) 2011, RealPaaS Technologies Ltd. All rights reserved.
 */
package com.realpaas.platform.core.identity.service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.testng.annotations.Test;

import com.realpaas.commons.utils.TestUtil;
import com.realpaas.platform.core.identity.service.UserService.SigninInfo;
import com.realpaas.platform.core.identity.service.UserService.SigninResult;
import com.realpaas.platform.core.identity.service.UserService.SigninResultType;
import com.realpaas.platform.core.identity.service.UserService.SignupInfo;
import com.realpaas.platform.core.identity.service.UserService.SignupResult;
import com.realpaas.platform.core.identity.service.UserService.SignupResultType;
import com.realpaas.platform.framework.ioc.BeanContextLoader;
import com.realpaas.platform.test.AbstractMetricsTask;
import com.realpaas.platform.test.AbstractTest;
import com.realpaas.platform.test.ConcurrentMetricsTest;
import com.realpaas.platform.test.MetricsTaskFactory;

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
public class UserServicePerformanceTest extends AbstractTest {
    
    private UserService userService;

    @Override
    public void setUp() {
        userService = (UserService)BeanContextLoader.i().getBean( "userService" );
        assertNotNull( userService );
    }

    @Override
    public void tearDown() {}

    public UserService getUserService() {
        return userService;
    }

    //    @Test(groups = { "all", "platform", "sit", "service" })
    public void signinPrecondition() {
        signupNumberOfUsers( 100000 );
    }
    
    @Test(groups = { "all", "platform", "sit", "service" })
    public void signin() {
        String[] signupResult = signupAUser();
        final String username = signupResult[0];
        final String password = signupResult[1];
        final SigninInfo signinInfo = new SigninInfo( username, password );

        final int threadCount = 50;
        final long actionCount = 50;
        final AtomicInteger failCounter = new AtomicInteger(0);
        
        class LoginTask extends AbstractMetricsTask{
            @Override
            protected void execute() {
                SigninResult result = null;
                for(int valueIndex = 0; valueIndex < actionCount; valueIndex++) {
                    try {
                        result = getUserService().signin( signinInfo );
                        if( !SigninResultType.Successful.equals( result.type  ) ){
                            failCounter.incrementAndGet();
                        }
                    }
                    catch (Exception e) {
                        failCounter.incrementAndGet();
                        //e.printStackTrace();
                    }
                }
            }

            @Override
            protected void processResult() {
                //System.out.println( "It took " + took + " milliseconds to run the task" );
            }
        }
        
        class LoginTaskFactory implements MetricsTaskFactory{

            @Override
            public AbstractMetricsTask newMetricsTask() {
                return new LoginTask();
            }

            @Override
            public AbstractMetricsTask newMetricsTask(AtomicLong total) {
                AbstractMetricsTask task = new LoginTask();
                task.setTotal( total ); 
                return task; 
            }
        }
        
        ConcurrentMetricsTest cmt = new ConcurrentMetricsTest("LoginTask", threadCount, new LoginTaskFactory());
        cmt.runAndWait( 10000 );
        long allTaskTook = cmt.getTotalTook().get();
        long eachLoginTook = allTaskTook/actionCount/threadCount;
        System.out.println( "Each login took average time: " + eachLoginTook );
    }
    
    @Test(groups = { "all", "platform", "sit", "service" })
    public void checkUsernameExistence() {
        String[] signupResult = signupAUser();
        final String username = signupResult[0];

        final int threadCount = 50;
        final long actionCount = 50;
        final AtomicInteger failCounter = new AtomicInteger(0);
        
        class CheckUsernameExistenceTask extends AbstractMetricsTask{
            @Override
            protected void execute() {
                for(int valueIndex = 0; valueIndex < actionCount; valueIndex++) {
                    try {
                        boolean existed = getUserService().checkUsernameExistence( username );
                        assert existed;
                    }
                    catch (Exception e) {
                        failCounter.incrementAndGet();
                        //e.printStackTrace();
                    }
                }
            }

            @Override
            protected void processResult() {
                //System.out.println( "It took " + took + " milliseconds to run the task" );
            }
        }
        
        class CheckUsernameExistenceTaskFactory implements MetricsTaskFactory{

            @Override
            public AbstractMetricsTask newMetricsTask() {
                return new CheckUsernameExistenceTask();
            }

            @Override
            public AbstractMetricsTask newMetricsTask(AtomicLong total) {
                AbstractMetricsTask task = new CheckUsernameExistenceTask();
                task.setTotal( total ); 
                return task; 
            }
        }
        
        ConcurrentMetricsTest cmt = new ConcurrentMetricsTest("CheckUsernameExistenceTask", threadCount, new CheckUsernameExistenceTaskFactory());
        cmt.runAndWait( 10000 );
        long allTaskTook = cmt.getTotalTook().get();
        long eachCheckUsernameExistenceTook = allTaskTook/actionCount/threadCount;
        System.out.println( "Each CheckUsernameExistence took average time: " + eachCheckUsernameExistenceTook );
    }
        
    private void signupNumberOfUsers(int number){
        for(int i = 0; i < number; i++) {
            signupAUser();
        }
    }
    
    private String[] signupAUser() {
        String username = null;
        String password = null;;
        String email = null;
        SignupResult result = null;
        SignupInfo userInfo = new SignupInfo();
        
        username = TestUtil.uniqueText( "username{0}" );
        password = username;
        email = "realpaas_dev@163.com";
        userInfo.setUsername( username );
        userInfo.setPassword( password );
        userInfo.setEmail( email );
        
        result = userService.signup( userInfo );
        assertEquals( result.type, SignupResultType.Successful );
        return new String[]{username, password, email};
    }
}
