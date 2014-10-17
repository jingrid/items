/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.identity.service;

import org.testng.annotations.Test;

import com.jingge.platform.core.identity.service.impl.UserAccessHelper;
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
 * 	Aug 8, 2012	henry leu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henry leu Email/MSN: hongli_leu@126.com
 */
public class UserAccessHelperTest extends AbstractTest{

    @Override
    public void setUp() throws Exception {

    }

    @Override
    public void tearDown() throws Exception {

    }
    
    @Test(groups = { "all", "platform", "service", "ut" })
    public void checkUsernameValidity(){
        assertTrue( UserAccessHelper.checkUsernameValidity( "tom" ) );
        assertTrue( UserAccessHelper.checkUsernameValidity( "tom2" ) );
        assertTrue( UserAccessHelper.checkUsernameValidity( "Tom2" ) );
        assertFalse( UserAccessHelper.checkUsernameValidity( " tom" ) );
        assertFalse( UserAccessHelper.checkUsernameValidity( "2tom" ) );
    }
    
    @Test(groups = { "all", "platform", "service", "ut" })
    public void checkPasswordValidity(){
        assertTrue( UserAccessHelper.checkPasswordValidity( "Tom2" ) );
//        assertTrue( UserAccessHelper.checkPasswordValidity( "T-_-_om2-_" ) );
        assertFalse( UserAccessHelper.checkPasswordValidity( " tom" ) );
        assertFalse( UserAccessHelper.checkPasswordValidity( "2tom*" ) );
    }
    
}
